package com.raka.books

import android.app.Application
import com.raka.data.utils.WifiService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BooksApp: Application(){
    companion object {
        lateinit var instance:  BooksApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        setupServices()
    }

    private fun setupServices() {
        WifiService.instance.initializeWithApplicationContext(this)
    }
}