package com.vignesh.a44.quizapp.Service;

import com.vignesh.a44.quizapp.Repository.UserRepo;
import com.vignesh.a44.quizapp.Schema.MailQueueMessage;
import com.vignesh.a44.quizapp.Schema.UsersSchema;
import com.vignesh.a44.quizapp.Utility.CustomUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UsersService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private static final Logger log = LoggerFactory.getLogger(UsersService.class);

    @Autowired
    JWTService jwtService;

    @Autowired
    UserRepo usersCollection;

    @Autowired
    RabbitMQSendService rabbitMQSendService;

    @Autowired
    MongoTemplate mongodb;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<?> authorizeUser (String userEmail, String userPassword) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            UsersSchema user = usersCollection.findByEmail(userEmail);
            if (user != null && user.isVerified()) {
                System.out.println("::[UsersController]>> Inside for custom /login: "+user.toString());
                System.out.println("::[UsersController]>> Pre authenticationManager.authenticate ");
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), userPassword));
                System.out.println("::[UsersController]>> Post authenticationManager.authenticate "+authentication.isAuthenticated());
                if(authentication.isAuthenticated()) {
                    String userToken = jwtService.generateToken(user.getEmail());
                    result.put("STATUS", "SUCCESS");
                    result.put("USER", user.getUsername());
                    result.put("USER_TOKEN", userToken);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
            } else {
                result.put("STATUS", "FAILED");
                result.put("MESSAGE", "This user does not exist or is not verified");
                return new ResponseEntity<>(result,HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("Exception occurred while verifying user credentials: {}", e.getMessage());
            result.put("STATUS","FAILED");
            if (e.getMessage().equalsIgnoreCase("Bad credentials")) {
                result.put("MESSAGE", "Invalid email or password");
            } else {
                result.put("MESSAGE", "Error processing request");
            }
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    public UsersSchema createUser(UsersSchema user) {
        try {
            UsersSchema existingUser = usersCollection.findByEmail(user.getEmail());
            if (existingUser != null) {
                throw new RuntimeException("Duplicate Request");
            }
            return usersCollection.save(new UsersSchema(
                            user.getUsername(),
                            user.getEmail(),
                            encoder.encode(user.getPassword()),
                            user.isAuthor(),
                            user.isAdmin()
                    )
            );
        } catch (Exception e) {
            log.error("Error while creating user: {}",e.getMessage());
            throw e;
        }
    }

    public String startVerificationProcess(String emailId) {
        UsersSchema user = usersCollection.findByEmail(emailId);
        try {
            if (user == null) {
                return "User does not exist";
            }
            if (user.getVerificationProcess() == null || user.getVerificationProcess().equals("Failed")) {
                user.setVerificationCode(CustomUtilities.generate6DigitCode());
                user.setVerificationProcess("Started");
                log.info("Verification code for user: {} is set as: {}", user.getEmail(), user.getVerificationCode());
                if (!sendSystemMessage(user.getEmail(), "Account Verification", user.getVerificationCode())) {
                    user.setVerificationProcess("Failed");
                }
                usersCollection.save(user);
                return user.getVerificationProcess();
            }
            return "Ok";
        } catch (Exception e) {
            log.error("Error while starting verification process for user: {}, with error: {}", user.getEmail(), e.getMessage());
            throw e;
        }
    }

    public Boolean sendSystemMessage(String receiverEmail, String topic, String body) {
        try {
            if (topic.equalsIgnoreCase("Account Verification")) {
                body = "Your account verification code is: "+body;
            }
            MailQueueMessage message = new MailQueueMessage(receiverEmail, topic, body);
            rabbitMQSendService.sendMessage(message.toString());
            return true;
        } catch (Exception e) {
            log.error("Error while compiling system message to mailing service: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<?> verifyUser (String userEmail) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            UsersSchema user = usersCollection.findByEmail(userEmail);
            if (user != null) {
                Boolean isUserVerificationSuccessful = updateUserVerificationStatus(user, Boolean.TRUE);
                if (isUserVerificationSuccessful) {
                    result.put("MESSAGE", "VERIFICATION_SUCCESSFUL");
                } else {
                    result.put("MESSAGE","VERIFICATION_FAILED");
                }
            } else {
                result.put("MESSAGE", "USER_NOT_FOUND");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Could not complete verification process for user with email : {} {}", userEmail, e.getMessage());
            result.put("MESSAGE", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Boolean updateUserVerificationStatus (UsersSchema user, Boolean status) {
        Query query = new Query(Criteria.where("email").is(user.getEmail()));
        Update update = new Update().set("isVerified", status);
        return mongodb.updateFirst(query, update, UsersSchema.class).wasAcknowledged();
    }

}
