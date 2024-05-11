package com.example.FMIbook.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfiguration {
    @Value("${firebase.storage-bucket}")
    private String bucketName;
    @Value("${firebase.config}")
    private String firebaseConfig;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        byte[] byteArray = firebaseConfig.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .setStorageBucket(bucketName)
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
