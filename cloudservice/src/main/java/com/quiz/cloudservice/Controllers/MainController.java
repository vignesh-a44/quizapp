package com.quiz.cloudservice.Controllers;

import com.quiz.cloudservice.Service.AwsS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cloud")
public class MainController {

    @Autowired
    AwsS3Service s3Service;

    @GetMapping("/getAllContent")
    public List<String> getS3Files() {
        return s3Service.listBucketContent();
    }

}
