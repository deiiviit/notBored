package com.example.notbored

import android.content.Intent


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnStart = findViewById<Button>(R.id.btnStart)
        var edtParticipants = findViewById<EditText>(R.id.editTextNumber)

        validateEnableButton(btnStart, edtParticipants)
        onClick(edtParticipants, btnStart)

    }

    private fun onClick(edtParticipants: EditText, btnStart: Button) {
        btnStart.setOnClickListener {
            var intent = Intent(this, CategoriesActivity::class.java)
            intent.putExtra(
                "participants",
                edtParticipants.toString()
            ) //esta enviando el dato pero en string :(
            startActivity(intent)
        }
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

/*
=======
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


>>>>>>> master
}
*/
