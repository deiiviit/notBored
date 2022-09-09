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

        getData()

        // click btnBack and back to CategoryActivity
        binding.btnBack.setOnClickListener {
            finish()
        }
        // click btnTryAnother update data calling function getData
        binding.btnTryAnother.setOnClickListener {
            getData()
        }
    }

    /**
     * Method to decide which api request is called based on number of participants and category
     */
    private fun getData() {

        //Retrieves number of participants and category from intent
        val participants = intent.getIntExtra("participants", 1)
        val category = intent.getStringExtra("category") ?: "random"

        // control flow to determine which API call to make
        val oneOrMoreParticipants: Boolean = participants > 0
        val isCategoryRandom: Boolean = category == "random"
        when {
            oneOrMoreParticipants && isCategoryRandom -> searchRandomWithParticipants(participants)
            oneOrMoreParticipants && !isCategoryRandom -> searchActivityByCategoryAndParticipants(
                participants,
                category
            )
            !oneOrMoreParticipants && isCategoryRandom -> searchByRandomWithoutParticipants()
            else -> searchByCategoryWithoutParticipants(category)
        }
    }


    /**
     * Method to return price accessibility based on price specific value
     * @param price specific price of activity
     */
    private fun returnPrice(price: Double): String {
        return when {
            price > 0.0 && price <= 0.3 -> "Low"
            price > 0.3 && price <= 0.6 -> "Medium"
            price > 0.6 -> "High"
            else -> "Free"
        }
    }

    /**
     * This method makes an api request for an activity given a specific number of
     * participants and category and shows the data in the UI
     * @param participants number of participants wanted
     * @param category category wanted
     */
    private fun searchActivityByCategoryAndParticipants(participants: Int, category: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val apiResponse: Response<ActivityResponse> =
                provideApiService().getActivityByParticipantsAndType(
                    participants,
                    category.lowercase()
                )
            runOnUiThread {
                apiResponse.let {
                    when {

                        it.isSuccessful -> {
                            val activityResponse = apiResponse.body()
                            if (activityResponse?.error != "") {
                                binding.tvTitle.text = activityResponse?.error
                                binding.tvParticipantsQuantity.text = ""
                                binding.tvPriceQuantity.text = ""
                            } else {
                                binding.tvTitle.text = activityResponse.activity ?: ""
                                binding.tvParticipantsQuantity.text =
                                    activityResponse.participants.toString()
                                binding.tvType.text = category
                                binding.tvPriceQuantity.text =
                                    returnPrice(activityResponse.price ?: 0.0)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method makes an api request for an activity of random category given a specific number of
     * participants, and shows the data received in the UI
     * @param participants quantity of participants required
     */
    private fun searchRandomWithParticipants(participants: Int) {
        CoroutineScope(Dispatchers.IO).launch {

            val apiResponse: Response<ActivityResponse> = provideApiService()
                .getActivityByParticipants(participants)

            val activityResponse = apiResponse.body()

            runOnUiThread {
                apiResponse.let {
                    when {

                        it.isSuccessful -> {
                            if (activityResponse?.error != "") {
                                 onError(activityResponse?.error ?: "Request failed")
                            } else {
                                binding.tvTitle.text = activityResponse.activity ?: ""
                                binding.tvType.text = "Random"
                                binding.tvParticipantsQuantity.text =
                                    activityResponse.participants.toString()
                                binding.tvPriceQuantity.text =
                                    returnPrice(activityResponse.price ?: 0.0)
                                binding.tvCategory.text =
                                    activityResponse.type?.replaceFirstChar { it.uppercase() } ?: ""
                                binding.ivCategory.visibility = View.VISIBLE
                                binding.tvCategory.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method makes an api request for an activity with a random amount of participants,
     * given a specific category, and shows the data receive in the UI
     * @param category specific category requested
     */
    private fun searchByCategoryWithoutParticipants(category: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val apiResponse: Response<ActivityResponse> = provideApiService()
                .getActivityByType(category.lowercase())
            val activityResponse = apiResponse.body()
            runOnUiThread {
                apiResponse.let {
                    when {
                        it.isSuccessful -> {
                            binding.tvTitle.text = activityResponse?.activity ?: ""
                            binding.tvType.text = category
                            binding.tvParticipantsQuantity.text =
                                activityResponse?.participants.toString()
                            binding.tvPriceQuantity.text =
                                returnPrice(activityResponse?.price ?: 0.0)

                        }
                    }
                }
            }
        }
    }


    /**
     * This method makes an api request for an activity with a random amount of participants,
     * and a random category, and shows the data received in the UI
     */
    private fun searchByRandomWithoutParticipants() {
        CoroutineScope(Dispatchers.IO).launch {
            val apiResponse: Response<ActivityResponse> = provideApiService()
                .getRandomActivity()
            val activityResponse = apiResponse.body()
            runOnUiThread {
                apiResponse.let {
                    when {
                        it.isSuccessful -> {
                            if (activityResponse?.error != "") {
                                onError(activityResponse?.error ?: "Request failed")

                            } else {
                                binding.tvTitle.text = activityResponse.activity ?: ""
                                binding.tvType.text = "Random"
                                binding.tvParticipantsQuantity.text =
                                    activityResponse.participants.toString()
                                binding.tvPriceQuantity.text =
                                    returnPrice(activityResponse.price ?: 0.0)
                                binding.tvCategory.text =
                                    activityResponse.type?.replaceFirstChar { it.uppercase() } ?: ""
                                binding.ivCategory.visibility = View.VISIBLE
                                binding.tvCategory.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    /*Shows given error on the UI*/
    private fun onError(error: String){
        binding.tvTitle.text = error
        binding.tvParticipantsQuantity.text = ""
        binding.tvPriceQuantity.text = ""
    }
}
