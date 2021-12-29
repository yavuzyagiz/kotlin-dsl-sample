# kotlin-dsl-example

  - ### Sample preferences project on android.
## How to use
```
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
```
