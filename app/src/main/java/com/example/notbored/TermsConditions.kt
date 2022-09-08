package com.example.notbored

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notbored.databinding.ActivityTermsConditionsBinding

class TermsConditions : AppCompatActivity() {
    lateinit var binding: ActivityTermsConditionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsConditionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    //ibClose the activity
        binding.ibClose.setOnClickListener {
            finish()
        }

    }


}