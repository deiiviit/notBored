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
     * Metodo implementado para obtener la cantidad de participantes ingresados a traves de un intent.getIntExtra
     * Se crea el evento al boton random para que navegue hacia la activity SuggestionActivity
     * Se usa putExtra para enviar el numero de participantes y _________________________
     */
    fun getParticipantsFromIntent(){
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
     * Metodo implementado para crear una lista con las categorias requeridas
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