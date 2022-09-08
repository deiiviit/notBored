package com.example.notbored


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notbored.APIServices.ActivityResponse
import com.example.notbored.APIServices.provideApiService
import com.example.notbored.databinding.ActivitySuggestionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class SuggestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuggestionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrieves number of participants and category from intent
        val participants = intent.getIntExtra("participants", 1)
        val category = intent.getStringExtra("category") ?: "random"

        if (participants > 0) {
            if (category != "random") {
                searchActivityByCategoryAndParticipants(participants, category)
            } else {
                searchRandom(participants)
            }
        } else {
            if (category != "random") {
                searchByCategory(category)
            } else {

            }
        }

        // click btnBack and back to CategoryActivity
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun searchActivityByCategoryAndParticipants(participants: Int, category: String) {

        CoroutineScope(Dispatchers.IO).launch {

            val apiResponse: Response<ActivityResponse> =
                provideApiService().getActivityByParticipantsAndType(
                    participants,
                    category.lowercase()
                )

            val activityResponse = apiResponse.body()

            runOnUiThread {
                if (apiResponse.isSuccessful) {
                    binding.tvTitle.text = activityResponse?.activity ?: ""
                    binding.tvParticipantsQuantity.text = activityResponse?.participants.toString()
                    binding.tvType.text = category
                }
            }
        }

    }


    private fun searchRandom(participants: Int) {
        //get participants from previous activity
        CoroutineScope(Dispatchers.IO).launch {

            val apiResponse: Response<ActivityResponse> = provideApiService()
                .getRandomActivity()

            val activityResponse = apiResponse.body()

            runOnUiThread {
                if (apiResponse.isSuccessful) {
                    val activity = activityResponse?.activity ?: ""
                    val type = activityResponse?.type ?: ""
                    binding.tvTitle.text = activity
                    binding.tvType.text = type.replaceFirstChar { it.uppercase() }
                    binding.tvParticipantsQuantity.text = participants.toString()

                }
            }

        }
    }

    //function to get the activity by category
    private fun searchByCategory(category: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val apiResponse: Response<ActivityResponse> = provideApiService()
                .getActivityByType(category)

            val activityResponse = apiResponse.body()

            runOnUiThread {
                if (apiResponse.isSuccessful) {
                    val activity = activityResponse?.activity ?: ""
                    binding.tvTitle.text = activity


                }
            }

        }
    }


    // TODO pantalla random arreglar titulo y agregar el icono

    //TODO intent - actualizar screen activity


}