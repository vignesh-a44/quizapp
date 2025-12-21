package com.quiz.cloudservice.Service;

import com.quiz.cloudservice.Utility.CloudUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AwsS3Service {

    @Autowired
    S3Client s3Client;

    @Autowired
    CloudUtilities utilities;

    public List<String> listBucketContent() {

        String bucketName = utilities.getAwsBucketName();

        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).build();
        ListObjectsV2Response response = s3Client.listObjectsV2(request);
        return response.contents().stream().map(S3Object::key).collect(Collectors.toList());
    }

}
