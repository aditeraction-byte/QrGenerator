package com.example.qrgenerator

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QrGeneratorApp : Application()
// Base Application class required by Hilt to initialize the dependency container.