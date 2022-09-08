package com.example.notbored

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notbored.databinding.ActivitySuggestionBinding

class SuggestionActivity : AppCompatActivity() {

    lateinit var binding: ActivitySuggestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // click btnBack and back to CategoryActivity
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}