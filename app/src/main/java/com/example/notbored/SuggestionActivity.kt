package com.example.notbored


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notbored.APIServices.APIService
import com.example.notbored.APIServices.ActivityResponse
import com.example.notbored.databinding.ActivitySuggestionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.notbored.APIServices.provideApiService

import retrofit2.Response


class SuggestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuggestionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrieves number of participants and category from intent
        val participants = intent.getIntExtra("participants", 1)
        val category = intent.getStringExtra("category")?.lowercase()

        // check if random button is clicked
        val random = intent.getBooleanExtra("random", false)
        if (random) {
            searchRandom(participants = participants)
        }



        // click btnBack and back to CategoryActivity
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun searchRandom(participants: Int) {
        //get participants from previous activity
        CoroutineScope(Dispatchers.IO).launch {


           val apiResponse : Response<ActivityResponse> = provideApiService()
               .getRandomActivity()

            val activityResponse = apiResponse.body()

            runOnUiThread {
                if (apiResponse.isSuccessful) {
                    val activity = activityResponse?.activity ?: ""
                    val type = activityResponse?.type ?: ""
                    val participants = activityResponse?.participants ?: ""
                    binding.tvTitle.text = activity
                    binding.tvCategory.text = type.replaceFirstChar { it.uppercase() }
                    binding.tvParticipantsQuantity.text = participants.toString()
                    binding.ivCategory.visibility = View.VISIBLE
                    binding.tvCategory.visibility = View.VISIBLE

                }
            }

        }
    }

//    //function to get the activity by category
//    fun searchByCategory(category: String) {
//        //get participants from main activity
//        participants = intent.getIntExtra("participants", 0)
//        CoroutineScope(Dispatchers.IO).launch {
//
//            val apiResponse: Response<ActivityResponse> = getRetrofit()
//                .create(APIService::class.java)
//                .getActivityByCategory(category)
//
//            val activityResponse = apiResponse.body()
//
//            runOnUiThread {
//                if (apiResponse.isSuccessful) {
//                    val activity = activityResponse?.activity ?: ""
//                    binding.tvTitle.text = activity
//                    binding.tvParticipantsQuantity.text = participants.toString()
//
//                }
//            }
//
//        }
//    }


    // TODO pantalla random arreglar titulo y agregar el icono

    //TODO intent - actualizar screen activity


    // TODO pantalla random arreglar titulo y agregar el icono

    //TODO intent - actualizar screen activity


}