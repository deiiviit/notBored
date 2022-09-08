package com.example.notbored

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.notbored.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //binding
    private lateinit var binding: ActivityMainBinding

    private var participants: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStart.isEnabled = binding.editTextNumber.text.toString().isNotEmpty()

        binding.editTextNumber.addTextChangedListener {
            binding.btnStart.isEnabled = binding.editTextNumber.text.toString().isNotEmpty()

        }
        //update participants with the value from the edit text
        binding.editTextNumber.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                participants = binding.editTextNumber.text.toString().toInt()
            }
        }

        val intentToCategoriesActivity = Intent(this, CategoriesActivity::class.java).also {

            it.putExtra("participants", participants)
        }

        binding.btnStart.setOnClickListener {

            startActivity(intentToCategoriesActivity)
        }

        //clic tvTermsConditions to open TermsConditionsActivity
        binding.tvTermsConditions.setOnClickListener {
            val intentToTermsConditionsActivity = Intent(this, TermsConditions::class.java)
            startActivity(intentToTermsConditionsActivity)
        }







    }

}