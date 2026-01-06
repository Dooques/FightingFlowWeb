package com.dooques.fightingFlowBackend.data.service

import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.cloud.FirestoreClient
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service

//@Service
//class FirestoreService(private val firebaseApp: FirebaseApp) {
//    private lateinit var db: Firestore
//
//    @PostConstruct
//    fun init() {
//        db = FirestoreClient.getFirestore()
//    }
//
//}