package com.example.notbored
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        goActivityTermsConditions()
    }

    /**
     * Method that implements navigation between start activities to categoriesActivity when clicking the start buttont
     *.also it is used since you want to use the intent to send the data received in the fieldtext
     */
    fun onClickButtonStart() {
        binding.btnStart.setOnClickListener {
            val intentToCategoriesActivity = Intent(this, CategoriesActivity::class.java).also {
                it.putExtra("participants", binding.editTextNumber.text.toString().toInt())
            }
            startActivity(intentToCategoriesActivity)
        }
    }

    /**
     * Orchestrator method to disable or enable the Start button
     * The validate Field Participants method is called, which returns a boolean
     * In case of receiving a false, the changeStatusButton method is called so that it changes color
     * addTextChangedListener works as a listener of the text field
     * Detect changes since the field is empty, when it is being written and when it is finished writing
     */
    private fun validateEnableButton() {
        changeStatusButton(false)
        binding.editTextNumber.addTextChangedListener {
            when {
                !validateFieldParticipants(binding.editTextNumber) -> changeStatusButton(false)
              // Toast.makeText(this, R.string.Data_not_allowed, Toast.LENGTH_LONG).show()
                else -> changeStatusButton(true)
            }
        }
    }

    /**
     * Implemented method to validate that the entered data is allowed by the application or as required
     * It is validated if the text field is empty, or if it is not numeric or if it is a number less than 0
     * @param edtParticipants Receives the data entered by the user

     */
    private fun validateFieldParticipants(edtParticipants: EditText): Boolean {
        val numberOfParticipants = edtParticipants.text.toString()
        return when {
            numberOfParticipants.isNullOrEmpty()
                    || !isNumeric(numberOfParticipants)
                    || numberOfParticipants.toInt() < 0 -> false
            else -> true
        }
    }

    /**
     * Implemented method to change the color of the button and enable or disable the start button
     */
    private fun changeStatusButton(flagStatusButton: Boolean) {
        binding.btnStart.isEnabled = flagStatusButton
        when {
            flagStatusButton -> binding.btnStart.setBackgroundColor(Color.BLUE)
            else -> binding.btnStart.setBackgroundColor(Color.GRAY)
        }
    }


    /**
     * Implemented method to validate if the number you enter as a parameter is numeric
     * @param toCheck number you enter to validate
     * @return true if numeric or false if not
     */
    private fun isNumeric(toCheck: String): Boolean {
        return toCheck.all { char -> char.isDigit() }

    }

    /**
     * Method that allows navigation to the terms and conditions activity
     */
    private fun goActivityTermsConditions() {
        binding.tvTermsConditions.setOnClickListener() {
            val intentToTermsConditions = Intent(this, TermsConditions::class.java)
            startActivity(intentToTermsConditions)
        }
    }
}