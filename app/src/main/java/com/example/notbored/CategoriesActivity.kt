package com.example.notbored

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.notbored.adapter.CategoriesAdapter
import com.example.notbored.databinding.ActivityCategoriesBinding


class CategoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getParticipantsFromIntent()

    }

    /**
     * Implemented method to get the number of participants entered through an intent.getIntExtra
     * The event is created for the random button so that it navigates to the SuggestionActivity activity
     * putExtra is used to send the number of participants and _________________________
     */
    private fun getParticipantsFromIntent(){
        val participants = intent.getIntExtra("participants", 0)
        initListView(participants)

        binding.btnRandom.setOnClickListener {
            val intent = Intent(this, SuggestionActivity::class.java).also {
                it.putExtra("participants", participants)
                it.putExtra("category", "random")
            }
            startActivity(intent)
        }
    }

    /**
     * Implemented method to create a list with the required categories
     *
     */
    private fun initListView(participants: Int) {
        val categoriesList = listOf(
            "Education",
            "Recreational",
            "Social",
            "Diy",
            "Charity",
            "Cooking",
            "Relaxation",
            "Music",
            "Busywork"
        )
        binding.lvCategories.adapter = CategoriesAdapter(categoriesList, this, participants)
    }


}