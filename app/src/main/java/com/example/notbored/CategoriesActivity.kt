package com.example.notbored

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notbored.adapter.CategoriesAdapter
import com.example.notbored.databinding.ActivityCategoriesBinding

class CategoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListView()
    }

    private fun initListView() {
        val categoriesList = listOf("Education","Recreational", "Social", "Diy", "Charity", "Cooking", "Relaxation", "Music", "Busywork")
        binding.lvCategories.adapter = CategoriesAdapter(categoriesList,this)
    }

}