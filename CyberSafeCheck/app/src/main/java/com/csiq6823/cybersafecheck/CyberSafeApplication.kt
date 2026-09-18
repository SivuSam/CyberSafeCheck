package com.csiq6823.cybersafecheck

import android.app.Application

class CyberSafeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RiskRepository.initialize(this)
    }
}
