package com.quiz.cloudservice.Utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:aws.properties")
public class CloudUtilities {
    // Properties from application.properties
    @Value("${aws.config.access_key}")
    private String access_key;

    @Value("${aws.config.secret_key}")
    private String secret_key;

    @Value("${aws.config.region}")
    private String region;

    @Value("${aws.config.bucket}")
    private String bucket;

    public String getAwsAccessKey() {
        return access_key;
    }

    public String getAwsSecurityKey() {
        return secret_key;
    }

    public String getAwsRegion() {
        return region;
    }

    public String getAwsBucketName() {
        return bucket;
    }

}

