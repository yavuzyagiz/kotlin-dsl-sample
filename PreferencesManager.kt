package com.example.preferencesdsl

import android.content.SharedPreferences
import kotlin.NullPointerException

interface Event
enum class Events : Event { PUT, GET }

data class Preferences(
    val sharedPreferences: SharedPreferences? = null,
    val editor: SharedPreferences.Editor? = null
)

data class Data(val event: Event, val key: String)

fun preferences(f: PreferencesBuilder.() -> Unit): Preferences {
    val builder = PreferencesBuilder()
    builder.f()
    return builder.build()
}

class PreferencesBuilder {
    private var preferences: Preferences? = null
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    fun setPreferences(sharedPreferences: SharedPreferences?) {
        this.sharedPreferences = sharedPreferences
        this.editor = this.sharedPreferences!!.edit()
    }

    infix fun Event.key(key: String): Data {
        return Data(this, key)
    }

    infix fun Data.value(data: Any?): Any {
        return when (this.event) {
            Events.PUT -> setData(this.key, data)
            else -> throw IllegalArgumentException()
        }
    }

    infix fun Data.defaultValue(data: Any?): Any? {
        return when (this.event) {
            Events.GET -> setDefaultValue(this.key, data)
            else -> throw IllegalArgumentException()
        }
    }

    private fun setDefaultValue(key: String, defaultValue: Any?): Any? {
        var data: Any? = null
        when (defaultValue) {
            is Boolean -> data = getBoolean(key, defaultValue)
            is Int -> data = getInt(key, defaultValue)
            is Long -> data = getLong(key, defaultValue)
            is Float -> data = getFloat(key, defaultValue)
            is String -> data = getString(key, defaultValue)
        }
        return data
    }

    private fun setData(key: String, data: Any?): Boolean {
        when (data) {
            is Boolean -> {
                putBoolean(
                    key to data
                )
                return true
            }
            is Int -> {
                putInt(
                    key to data
                )
                return true
            }
            is Long -> {
                putLong(
                    key to data
                )
                return true
            }
            is Float -> {
                putFloat(
                    key to data
                )
                return true
            }
            is String -> {
                putString(
                    key to data
                )
                return true
            }
            else -> return false
        }
    }

    private fun putBoolean(pair: Pair<String, Boolean>) {
        editor?.putBoolean(pair.first, pair.second)
    }

    private fun putInt(pair: Pair<String, Int>) {
        editor?.putInt(pair.first, pair.second)
    }

    private fun putLong(pair: Pair<String, Long>) {
        editor?.putLong(pair.first, pair.second)
    }

    private fun putFloat(pair: Pair<String, Float>) {
        editor?.putFloat(pair.first, pair.second)
    }

    private fun putString(pair: Pair<String, String>) {
        editor?.putString(pair.first, pair.second)
    }

    private fun getBoolean(key: String, defaultValue: Boolean = false): Boolean =
        sharedPreferences?.getBoolean(key, defaultValue)
            ?: throw NullPointerException("setPreference() method must be called.")

    private fun getInt(key: String, defaultValue: Int = 0): Int =
        sharedPreferences?.getInt(key, defaultValue)
            ?: throw NullPointerException("setPreference() method must be called.")

    private fun getLong(key: String, defaultValue: Long = 0): Long =
        sharedPreferences?.getLong(key, defaultValue)
            ?: throw NullPointerException("setPreference() method must be called.")

    private fun getFloat(key: String, defaultValue: Float = 0f): Float =
        sharedPreferences?.getFloat(key, defaultValue)
            ?: throw NullPointerException("setPreference() method must be called.")

    private fun getString(key: String, defaultValue: String = ""): String =
        sharedPreferences?.getString(key, defaultValue)
            ?: throw NullPointerException("setPreference() method must be called.")

    private fun apply() {
        editor?.apply()
    }

    fun clear(){
        editor?.clear()
    }

    fun remove(key: String){
        editor?.remove(key)
    }

    fun build(): Preferences {
        var preferences: Preferences? = null
        sharedPreferences?.let {
            editor?.let {
                apply()
                preferences = Preferences(sharedPreferences, editor)
            }
        }
        return preferences ?: throw NullPointerException("setPreference() method must be called.")
    }
}

