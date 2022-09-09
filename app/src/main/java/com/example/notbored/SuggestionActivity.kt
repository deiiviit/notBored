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

        // getData centralized
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
     * Metodo orquestador para obtener informacion de las actividades segun los participantes o actividades seleccionadas
     */
    private fun getData() {
        //retrieves number of participants and category from intent
        val participants = intent.getIntExtra("participants", 1)
        val category = intent.getStringExtra("category") ?: "random"

        // control flow to determine which API call to make
        val oneOrMoreParticipants: Boolean = participants > 0;
        val isCategoryRandom: Boolean = category == "random";
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
     * Metodo implementado para categorizar los precios de acuerdo al valor que retorna la API
     * @param price valor recibido desde la API
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
     * Metodo encargado de obtener la informacion de la API cuando se ingresa numero de participantes y se selecciona categoria
     * Se hace el llamado a la API
     *Se setean propiedades a mostrar en vistas
     * @param participants Cantidad de participantes ingresados
     * @param category categoria seleccionada por el usuario
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
                        //Si se hace el llamado a la API correctamente
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
     * Metodo encargado de obtener la informacion de la API cuando se ingresa numero de participantes
     * Se hace el llamado a la API
     *Se setean propiedades a mostrar en vistas
     * @param participants Cantidad de participantes ingresados
     */
    private fun searchRandomWithParticipants(participants: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            //se hace el llamado a la API
            val apiResponse: Response<ActivityResponse> = provideApiService()
                .getActivityByParticipants(participants)
            //Se extrae el body de la api
            val activityResponse = apiResponse.body()

            runOnUiThread {
                apiResponse.let {
                    when {
                        //cuando es exitosa se van a setear todos los campos
                        it.isSuccessful -> {
                            binding.tvTitle.text = activityResponse?.activity ?: ""
                            binding.tvType.text = "Random"
                            binding.tvParticipantsQuantity.text =
                                activityResponse?.participants.toString()
                            binding.tvPriceQuantity.text =
                                returnPrice(activityResponse?.price ?: 0.0)
                            binding.tvCategory.text = activityResponse?.type ?: ""
                            binding.ivCategory.visibility = View.VISIBLE
                            binding.tvCategory.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    /**
     * Metodo encargado de obtener la informacion de la API cuando se selecciona categoria
     * Se hace el llamado a la API
     *Se setean propiedades a mostrar en vistas
     * @param category categoria seleccionada por el usuario
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
     * method to get random activity suggestions without any parameters
     * Se hace el llamado a la API
     *Se setean propiedades a mostrar en vistas
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
                            binding.tvTitle.text = activityResponse?.activity ?: ""
                            binding.tvType.text = "Random"
                            binding.tvParticipantsQuantity.text =
                                activityResponse?.participants.toString()
                            binding.tvPriceQuantity.text =
                                returnPrice(activityResponse?.price ?: 0.0)
                            binding.tvCategory.text = activityResponse?.type ?: ""
                            binding.ivCategory.visibility = View.VISIBLE
                            binding.tvCategory.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
}


