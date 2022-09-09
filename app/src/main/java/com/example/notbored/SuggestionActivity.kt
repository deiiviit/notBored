package com.example.notbored


import android.os.Bundle
import android.view.View
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


        chooseApiCall(participants,category)

        binding.btnTryAnother.setOnClickListener {
            chooseApiCall(participants,category)
        }

        // click btnBack and back to CategoryActivity
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    // control flow to determine which API call to make
    private fun chooseApiCall (participants: Int, category: String){
        if (participants > 0) {
            if (category != "random") {
                searchActivityByCategoryAndParticipants(participants, category)
            } else {
                searchRandomWithParticipants(participants)
            }
        } else {
            if (category != "random") {
                searchByCategoryWithoutParticipants(category)
            } else {
                searchByRandomWithoutParticipants()
            }
        }
    }

    //Calculates price range according to value
    private fun returnPrice(price: Double): String {
        return when {
            price > 0.0 && price <= 0.3 -> "Low"
            price > 0.3 && price <= 0.6 -> "Medium"
            price > 0.6 -> "High"
            else -> "Free"
        }

    }

    // function to get the activity suggestion depending on the number of participants and category
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
                    if (activityResponse?.error != "") {
                        binding.tvTitle.text = activityResponse?.error
                        binding.tvParticipantsQuantity.text = ""
                        binding.tvPriceQuantity.text = ""
                    } else {
                        binding.tvTitle.text = activityResponse.activity ?: ""
                        binding.tvParticipantsQuantity.text =
                            activityResponse.participants.toString()
                        binding.tvType.text = category
                        binding.tvPriceQuantity.text = returnPrice(activityResponse.price ?: 0.0)
                    }


                }
            }
        }

    }

    // function to get the activity suggestion depending on the number of participants
    private fun searchRandomWithParticipants(participants: Int) {
        CoroutineScope(Dispatchers.IO).launch {

            val apiResponse: Response<ActivityResponse> = provideApiService()
                .getActivityByParticipants(participants)

            val activityResponse = apiResponse.body()

            runOnUiThread {
                if (apiResponse.isSuccessful) {

                    binding.tvTitle.text = activityResponse?.activity ?: ""
                    binding.tvType.text = "Random"
                    binding.tvParticipantsQuantity.text = activityResponse?.participants.toString()
                    binding.tvPriceQuantity.text = returnPrice(activityResponse?.price ?: 0.0)
                    binding.tvCategory.text = activityResponse?.type ?: ""
                    binding.ivCategory.visibility = View.VISIBLE
                    binding.tvCategory.visibility = View.VISIBLE
                }


            }
        }
    }

    // function to get the activity suggestion depending on the category and without participants
    private fun searchByCategoryWithoutParticipants(category: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val apiResponse: Response<ActivityResponse> = provideApiService()
                .getActivityByType(category.lowercase())
            val activityResponse = apiResponse.body()
            runOnUiThread {
                if (apiResponse.isSuccessful) {
                    binding.tvTitle.text = activityResponse?.activity ?: ""
                    binding.tvType.text = category
                    binding.tvParticipantsQuantity.text =
                        activityResponse?.participants.toString()
                    binding.tvPriceQuantity.text = returnPrice(activityResponse?.price ?: 0.0)

                }
            }

        }
    }

    // function to get the activity suggestion randomly without any parameters
    private fun searchByRandomWithoutParticipants() {
        CoroutineScope(Dispatchers.IO).launch {
            val apiResponse: Response<ActivityResponse> = provideApiService()
                .getRandomActivity()
            val activityResponse = apiResponse.body()
            runOnUiThread {
                if (apiResponse.isSuccessful) {
                    binding.tvTitle.text = activityResponse?.activity ?: ""
                    binding.tvType.text = "Random"
                    binding.tvParticipantsQuantity.text = activityResponse?.participants.toString()
                    binding.tvPriceQuantity.text = returnPrice(activityResponse?.price ?: 0.0)
                    binding.tvCategory.text = activityResponse?.type ?: ""
                    binding.ivCategory.visibility = View.VISIBLE
                    binding.tvCategory.visibility = View.VISIBLE
                }


            }

        }
    }
}


// TODO pantalla random arreglar titulo y agregar el icono

//TODO intent - actualizar screen activity
