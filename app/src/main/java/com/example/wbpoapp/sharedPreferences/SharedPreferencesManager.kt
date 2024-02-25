package com.example.wbpoapp.sharedPreferences

import android.content.Context
import com.example.wbpoapp.ui.MainActivity


const val sharedPrefName = "WBPOAppSharedPreferences"
const val firstRegistrationKey = "firstRegistration"

class SharedPreferencesManager {
    private val sharedPref = MainActivity.instance.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)


    fun setFirstRegistration(firstRegistration: Boolean) {
        sharedPref.edit().putBoolean(firstRegistrationKey, firstRegistration).apply()
    }

    fun getFirstRegistration(): Boolean {
        return sharedPref.getBoolean(firstRegistrationKey, true)
    }

    fun setFollowId(id: Int, isFollowed: Boolean) {
        sharedPref.edit().putBoolean(id.toString(), isFollowed).apply()
    }

    fun getIfIdIsFollowed(id: Int): Boolean {
        return sharedPref.getBoolean(id.toString(), false)
    }
}