package com.kigo.testaar

import android.app.Application
import com.kigo.androidsdk.config.Kigo

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Kigo.Builder(this,"test_api_key_12345")
            .setLoggingEnabled(true)
            .build()
    }
}