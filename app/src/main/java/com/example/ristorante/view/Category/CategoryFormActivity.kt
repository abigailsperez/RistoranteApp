package com.example.ristorante.view.Category

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ristorante.R
import com.example.ristorante.entity.Category
import com.example.ristorante.services.InterfaceCategory
import com.example.ristorante.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFormActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var name: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_form)

        title = findViewById(R.id.title_m)
        name = findViewById(R.id.code)

        if(intent.getLongExtra("category", 0) != 0L){
            this.loadData()
            title.setText("Editar categoría")
        } else {
            title.setText("Añadir categoría")
        }
    }

    fun loadData(){
        val service = ServiceB.buildService(InterfaceCategory::class.java)
        val call = service.getById(intent.getLongExtra("category", 0))

        call.enqueue(object : Callback<Category> {
            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                when {
                    response.code() == 200 -> {
                        name.setText(response.body()!!.name)
                    }
                }
            }
            override fun onFailure(call: Call<Category>, t: Throwable) {
                Toast.makeText(this@CategoryFormActivity,
                    "No se ha podido comunicar con el servidor, verifique su conexión o contacte a algun Administrador",
                    Toast.LENGTH_LONG).show()
            }
        })
    }

    fun validate(): Boolean{
        if(name.text.toString().isEmpty()){
            Toast.makeText(this,"Campo nombre vacío.", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    // Guardar una categoría
    fun save(view: View){
        if(validate()){
            val obj = Category()
            obj.id = intent.getLongExtra("category", 0)
            obj.name = name.text.toString()
            obj.restaurant = intent.getLongExtra("restaurant", 0)

            val service = ServiceB.buildService(InterfaceCategory::class.java)
            val call = service.save(obj)

            call.enqueue(object : Callback<Category> {
                override fun onResponse(
                    call: Call<Category>,
                    response: Response<Category>
                ) {
                    when { // Guardado con éxito
                        response.code() == 200 -> {
                            Toast.makeText(
                                this@CategoryFormActivity,
                                "Guardado con éxito.",
                                Toast.LENGTH_LONG
                            ).show()
                        } // Error en el navegador
                        response.code() == 401 -> {
                            Toast.makeText(
                                this@CategoryFormActivity,
                                "Ha ocurrido un error al guardar el registro. Intente más tarde.",
                                Toast.LENGTH_LONG
                            ).show()
                        } // Error de conexión al servidor
                        response.code() == 500 -> {
                            Toast.makeText(
                                this@CategoryFormActivity,
                                "Ha occurido un error en el servidor, por favor contacte al Administrador.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<Category>, t: Throwable) {
                    Toast.makeText(
                        this@CategoryFormActivity,
                        "Ha ocurrido un error.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
            setResult(Activity.RESULT_OK)
            this.finish()
        }
    }
}