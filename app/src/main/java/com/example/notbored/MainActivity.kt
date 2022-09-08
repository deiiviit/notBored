package com.example.notbored

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.example.notbored.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.editTextNumber.addTextChangedListener {
            binding.btnStart.isEnabled = binding.editTextNumber.text.toString().isNotEmpty()
        }



        binding.btnStart.setOnClickListener {
            val intentToCategoriesActivity = Intent(this, CategoriesActivity::class.java).also {

                it.putExtra("participants", binding.editTextNumber.text.toString().toInt())
            }

            startActivity(intentToCategoriesActivity)
        }


        validateEnableButton(binding.btnStart, binding.editTextNumber)


    }

    private fun validateEnableButton(btnStart: Button, edtParticipants: EditText) {
        btnStart.isEnabled = false


        edtParticipants.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when {
                    !validateFieldParticipants(edtParticipants) -> btnStart.isEnabled = false
                    else -> btnStart.isEnabled = true
                }
            }
        })
    }

    private fun validateFieldParticipants(edtParticipants: EditText): Boolean {
        val numberOfParticipants = edtParticipants.text.toString()
        return when {
            numberOfParticipants.isNullOrEmpty()
                    || !isNumeric(numberOfParticipants)
                    || numberOfParticipants.toInt() <= 0 -> false
            else -> true
        }
    }

    private fun isNumeric(toCheck: String): Boolean {
        return toCheck.all { char -> char.isDigit() }

    }
}