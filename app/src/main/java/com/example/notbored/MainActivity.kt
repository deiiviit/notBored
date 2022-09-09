package com.example.notbored
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.example.notbored.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        validateEnableButton()
        onClickButtonStart()
        goActivityTermsConditions()
    }

    /**
     * Método que implementa la navegacion entre las actividades de inicio a categoriesActivity al hacer click en el boton start
     *.also se usa ya que se desea usar el intent para enviar el dato recibido en el fieldtext
     */
    fun onClickButtonStart() {
        binding.btnStart.setOnClickListener {
            val intentToCategoriesActivity = Intent(this, CategoriesActivity::class.java).also {
                it.putExtra("participants", binding.editTextNumber.text.toString().toInt())
            }
            startActivity(intentToCategoriesActivity)
        }
    }

    /**
     * Metodo orquestador para inhabilitar o habilitar el boton Start
     * Se invoca al método validateFieldParticipants  que retorna un boolen
     * En caso de recibir un false, se invoca al método changeStatusButton para que cambie de color
     * addTextChangedListener funciona como un oyente del campo de texto
     * Detecta los cambios desde que el campo esté vacio, cuando se esta escribiendo y se termina de escribir
     */
    private fun validateEnableButton() {
        changeStatusButton(false)
        binding.editTextNumber.addTextChangedListener {
            when {
                !validateFieldParticipants(binding.editTextNumber) -> changeStatusButton(false)
              // Toast.makeText(this, R.string.Data_not_allowed, Toast.LENGTH_LONG).show()
                else -> changeStatusButton(true)
            }
        }
    }

    /**
     * Método implementado para validar que el dato ingresado sea permitido por la aplicación o segun lo requerido
     * Se valida si el campo de texto esta vacio, o si no es numerico o es un numero menor a 0
      * @param edtParticipants Recibe el dato ingresado por el usuario

     */
    private fun validateFieldParticipants(edtParticipants: EditText): Boolean {
        val numberOfParticipants = edtParticipants.text.toString()
        return when {
            numberOfParticipants.isNullOrEmpty()
                    || !isNumeric(numberOfParticipants)
                    || numberOfParticipants.toInt() < 0 -> false
            else -> true
        }
    }

    /**
     * Metodo implementado para realizar el cambio del color del botón y habilitar o inhabilitar el
     * boton start
     */
    private fun changeStatusButton(flagStatusButton: Boolean) {
        binding.btnStart.isEnabled = flagStatusButton
        when {
            flagStatusButton -> binding.btnStart.setBackgroundColor(Color.BLUE)
            else -> binding.btnStart.setBackgroundColor(Color.GRAY)
        }
    }


    /**
     * Metodo implementado para validar si el número que ingresa como parámetro es numerico
     * valida caracter por caracter para saber si es dígito
     * @param toCheck numero que entra para validar
     * @return true si es numerico o false si no lo es
     */
    private fun isNumeric(toCheck: String): Boolean {
        return toCheck.all { char -> char.isDigit() }

    }

    /**
     * Metodo que permite la navegacion a la activity de terminos y condiciones
     * se usa un intent para iniciar la actividad
     */
    private fun goActivityTermsConditions() {
        binding.tvTermsConditions.setOnClickListener() {
            val intentToTermsConditions = Intent(this, TermsConditions::class.java)
            startActivity(intentToTermsConditions)
        }
    }
}