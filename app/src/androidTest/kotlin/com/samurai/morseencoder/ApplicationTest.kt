package com.samurai.morseencoder

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner


class MockTestRunner : AndroidJUnitRunner() {
    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        ClassNotFoundException::class
    )
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, Application::class.java.name, context)
    }
}