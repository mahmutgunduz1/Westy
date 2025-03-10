package com.mahmutgunduz.westy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Hilt için gerekli Application sınıfı
// @HiltAndroidApp annotation'ı Hilt'in dependency injection sistemini başlatır
@HiltAndroidApp
class WestyApplication : Application() 