package com.example.ristorante.view.DiningTable

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ristorante.R
import com.example.ristorante.entity.DiningTable
import com.example.ristorante.services.InterfaceDiningTable
import com.example.ristorante.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiningTableFormActivity: AppCompatActivity() {
    private lateinit var code: EditText
    private lateinit var people: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diningtable_form)

        code = findViewById(R.id.code)
        people = findViewById(R.id.people)
    }

    fun validate(): Boolean{
        if(code.text.toString().isEmpty()){
            Toast.makeText(this,"No ha introducido el código de la mesa.", Toast.LENGTH_LONG).show()
            return false
        } else if(people.text.toString().isEmpty()){
            Toast.makeText(this,"No ha introducido el número de personas de la mesa.", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    // Guardar una categoría
    fun save(view: View){
        if(validate()){
            val obj = DiningTable()
            obj.code = code.text.toString()
            obj.people = people.text.toString().toInt()
            obj.restaurant = intent.getLongExtra("restaurant", 0)

            val service = ServiceB.buildService(InterfaceDiningTable::class.java)
            val call = service.save(obj)

            call.enqueue(object : Callback<DiningTable> {
                override fun onResponse(
                    call: Call<DiningTable>,
                    response: Response<DiningTable>
                ) {
                    when { // Guardado con éxito
                        response.code() == 200 -> {
                            Toast.makeText(
                                this@DiningTableFormActivity,
                                "Guardado con éxito.",
                                Toast.LENGTH_LONG
                            ).show()
                        } // Error en el navegador
                        response.code() == 401 -> {
                            Toast.makeText(
                                this@DiningTableFormActivity,
                                "Ha ocurrido un error al guardar el registro. Intente más tarde.",
                                Toast.LENGTH_LONG
                            ).show()
                        } // Error de conexión al servidor
                        response.code() == 500 -> {
                            Toast.makeText(
                                this@DiningTableFormActivity,
                                "Ha occurido un error en el servidor, por favor contacte al Administrador.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<DiningTable>, t: Throwable) {
                    Toast.makeText(
                        this@DiningTableFormActivity,
                        "Ha ocurrido un error.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
            this.finish()
        }
    }
}