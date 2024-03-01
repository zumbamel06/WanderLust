package com.wanderlab.wanderlustcompanion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ContentDisplay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_display)

        val position = intent.getStringExtra("position")
        val data = intent.getStringExtra("data")
        val content_text: TextView = findViewById(R.id.content_display_text_view)

        val StringArray: Array<String> = resources.getStringArray(R.array.list_of_list)
        val index_value = StringArray.indexOf(data)
        val contentArray: Array<String> = resources.getStringArray(R.array.content_of_list)
        content_text.text = contentArray[index_value]
    }
}