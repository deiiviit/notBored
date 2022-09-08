package com.example.notbored

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.notbored.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //binding
    private lateinit var binding: ActivityMainBinding

    private val participants: Int = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStart.isEnabled = binding.editTextNumber.text.toString().isNotEmpty()


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

        binding.editTextNumber.addTextChangedListener {
            binding.btnStart.isEnabled = binding.editTextNumber.text.toString().isNotEmpty()
        }


    }

}