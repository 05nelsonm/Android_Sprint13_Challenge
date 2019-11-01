package com.lambdaschool.sprintchallenge13.di

import com.lambdaschool.sprintchallenge13.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (
    modules = [AppModule::class]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}