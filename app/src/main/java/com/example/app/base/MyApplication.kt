package com.example.app.base

import android.app.Application
import android.content.Context
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.memoryCacheSettings
import com.google.firebase.ktx.Firebase

class MyApplication: Application(){
    object Globals{
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        Globals.appContext = applicationContext

        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings { })
        }
        Firebase.firestore.firestoreSettings = settings
    }
}