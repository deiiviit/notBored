package com.example.notbored

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.notbored.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Intent to go to the CategoriesActivity
        val intentToCategoriesActivity = Intent(this, CategoriesActivity::class.java).also {
            it.putExtra("participants", binding.editTextNumber.text.toString())
        }

        // start the activity
        binding.btnStart.setOnClickListener {
            startActivity(intentToCategoriesActivity)
        }

        //clic tvTermsConditions to open TermsConditionsActivity
        binding.tvTermsConditions.setOnClickListener {
            val intentToTermsConditionsActivity = Intent(this, TermsConditions::class.java)
            startActivity(intentToTermsConditionsActivity)
        }


    }

    // function to go from


}