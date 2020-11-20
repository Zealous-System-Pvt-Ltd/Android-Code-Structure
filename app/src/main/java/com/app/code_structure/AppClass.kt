package com.app.code_structure

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication

class AppClass : MultiDexApplication() {


    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this

        if (BuildConfig.DEBUG) {

        }
        try {

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {
        @JvmField
        var appInstance: AppClass? = null

        @JvmStatic
        fun getAppContext(): AppClass {
            return appInstance as AppClass
        }
    }
}