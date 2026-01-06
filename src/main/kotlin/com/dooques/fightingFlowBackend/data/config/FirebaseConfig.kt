package com.dooques.fightingFlowBackend.data.config

import com.google.auth.oauth2.GoogleCredentials
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.FileInputStream

//@Configuration
//class FirebaseConfig {
//
//    @Bean
//    fun firebaseApp(): FirebaseApp {
//
//        val serviceAccount = FileInputStream(System.getenv("SERVICE_KEY_PATH"))
//
//        val options = FirebaseOptions.builder()
//            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//            .build()
//
//        return FirebaseApp.initializeApp(options)
//    }
//}