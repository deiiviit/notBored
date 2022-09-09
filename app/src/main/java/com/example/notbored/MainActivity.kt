package com.example.notbored

import android.content.Intent
import android.graphics.Color

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

        validateEnableButton()
        onClickButtonStart()
    }

    fun onClickButtonStart() {
        binding.btnStart.setOnClickListener {
            val intentToCategoriesActivity = Intent(this, CategoriesActivity::class.java).also {
                it.putExtra("participants", binding.editTextNumber.text.toString().toInt())
            }
            startActivity(intentToCategoriesActivity)
        }
    }

    private fun validateEnableButton() {
        binding.btnStart.isEnabled = false
        binding.editTextNumber.addTextChangedListener {
            when {
                !validateFieldParticipants(binding.editTextNumber) -> binding.btnStart.isEnabled =
                    false
                //   Toast.makeText(this, R.string.Data_not_allowed, Toast.LENGTH_LONG).show()
                else -> binding.btnStart.isEnabled = true
            }
        }
    }

    private fun validateFieldParticipants(edtParticipants: EditText): Boolean {
        val numberOfParticipants = edtParticipants.text.toString()
        return when {
            numberOfParticipants.isNullOrEmpty()
                    || !isNumeric(numberOfParticipants)
                    || numberOfParticipants.toInt() < 0 -> false
            else -> true
        }
    }

    private fun isNumeric(toCheck: String): Boolean {
        return toCheck.all { char -> char.isDigit() }

    }
}