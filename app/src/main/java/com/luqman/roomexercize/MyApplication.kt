package com.luqman.roomexercize

import android.app.Application
import com.luqman.roomexercize.database.AppDatabase

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
    }
}