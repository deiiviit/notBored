package com.example.notbored


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notbored.APIServices.APIService
import com.example.notbored.APIServices.ActivityResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.notbored.APIServices.provideApiService
import com.example.notbored.databinding.ActivitySuggestionBinding
import retrofit2.Response


class SuggestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuggestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchRandom()

        // click btnBack and back to CategoryActivity
        binding.btnBack.setOnClickListener {
            finish()
        }
    }



    private fun searchRandom() {
        CoroutineScope(Dispatchers.IO).launch {

           val apiResponse : Response<ActivityResponse> = provideApiService()
               .getRandomActivity()

            val activityResponse = apiResponse.body()

            runOnUiThread { if (apiResponse.isSuccessful) {
                val activity  = activityResponse?.activity ?: ""
                binding.tvTitle.text = activity

            } }

        }
    }



}