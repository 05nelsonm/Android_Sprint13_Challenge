package com.lambdaschool.sprintchallenge13

import android.app.Application
import com.lambdaschool.sprintchallenge13.di.DaggerAppComponent

class App: Application() {

    val appComponent by lazy {
        DaggerAppComponent
            .builder()
            .build()
    }
}