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

        initListView()
        // get participants from intent


        binding.btnRandom.setOnClickListener {
            val intent = Intent(this, SuggestionActivity::class.java).also {
                it.putExtra("participants", intent.getStringExtra("participants"))
                it.putExtra("random", true)
            }
            startActivity(intent)
        }

    }

    private fun initListView() {
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
        binding.lvCategories.adapter = CategoriesAdapter(categoriesList, this)
    }


}