package com.example.preferencesdsl

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textview)
        var text:String? = null
        val preferences = preferences {
            setPreferences(getSharedPreferences("TEST", Context.MODE_PRIVATE))
            Events.PUT key "key_0" value false
            Events.PUT key "key_1" value 123
            Events.PUT key "key_2" value 123.0f
            Events.PUT key "key_3" value 12345L
            Events.PUT key "key_4" value "text"
            Events.GET key "key_1" defaultValue 0
            text = (Events.GET key "key_4" defaultValue "empty") as? String
            remove("key_0")
        }
        textView.text = "$text" //: text
    }
}