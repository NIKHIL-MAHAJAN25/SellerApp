package com.nikhil.sellerapp.Application

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import io.ktor.http.ContentType

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val settings= firestoreSettings {
            isPersistenceEnabled=true
        }
        Firebase.firestore.firestoreSettings=settings
    }
}