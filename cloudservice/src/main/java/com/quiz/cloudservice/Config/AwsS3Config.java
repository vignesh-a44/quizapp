package com.quiz.cloudservice.Config;

import com.quiz.cloudservice.Utility.CloudUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsS3Config {

   @Autowired
    CloudUtilities utilities;

    @Bean
    public S3Client s3Client() {

        String accessKey = utilities.getAwsAccessKey();
        String securityKey = utilities.getAwsSecurityKey();
        String region = utilities.getAwsRegion();

        return S3Client.builder().region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, securityKey))
                ).build();
    }

}
