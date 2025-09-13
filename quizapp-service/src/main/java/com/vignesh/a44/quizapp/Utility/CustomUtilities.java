package com.vignesh.a44.quizapp.Utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource("classpath:application.properties")
public class CustomUtilities {
    // Properties from application.properties
    @Value("${custom.security.freepassendpoints}")
    private List<String> nonRestrictedEndPoints;

    public List<String> getNonRestrictedRoutes() {
        return nonRestrictedEndPoints;
    }

}
