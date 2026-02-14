package com.example.photoprofile

import android.app.Application
import com.example.photoprofile.di.DIContainer

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DIContainer.main(this)
    }
}