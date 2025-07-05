package com.vignesh.a44.quizapp.Repository;

import com.vignesh.a44.quizapp.Schema.UsersSchema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<UsersSchema, String> {
    UsersSchema findByUsername(String username);
    UsersSchema findByEmail(String email);
    @Override
    Optional<UsersSchema> findById(String userId);
}
