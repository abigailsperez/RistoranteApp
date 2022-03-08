package com.example.ristorantehttp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.ristorantehttp.R
import com.example.ristorantehttp.container.ResponseCategory
import com.example.ristorantehttp.container.SaveCategory
import com.example.ristorantehttp.controller.ControllerCategory
import com.example.ristorantehttp.services.InterfaceSaveCategory
import com.example.ristorantehttp.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditCategoryActivity : AppCompatActivity() {

    private lateinit var name: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_category)
        name= findViewById(R.id.name_c)
    }

    //Función para guardar categorias
    fun updateCategoryFun(view: View){
        if(name.text.toString().isEmpty()){
            Toast.makeText(this,"Campo nombre vacío.", Toast.LENGTH_LONG).show()
        }else {

            println("FUNCIÓN EDITAR CATEGORÍA")

            val idCat = intent.getLongExtra("category", 0)
            val rest = intent.getLongExtra("restaurant", 0)

            val saveCategoryObj = SaveCategory()
            saveCategoryObj.id = idCat
            saveCategoryObj.name = name.text.toString()  //TextView del name(categoria)
            saveCategoryObj.restaurant = rest//Número de restaurante
            println("ID del restaurante-> " + rest)

            //Consumo de web services
            val service = ServiceB.buildService(InterfaceSaveCategory::class.java)
            val call = service.saveCategory(saveCategoryObj)

            call.enqueue(object : Callback<ResponseCategory> {
                override fun onResponse(
                    call: Call<ResponseCategory>,
                    response: Response<ResponseCategory>
                ) {
                    when {
                        //Guardado con éxito
                        response.code() == 200 -> {
                            Toast.makeText(
                                this@EditCategoryActivity,
                                "Guardado con éxito.",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        //Error en el navegador
                        response.code() == 401 -> {
                            Toast.makeText(
                                this@EditCategoryActivity,
                                "Ha ocurrido un error al guardar el registro. Intente más tarde",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        //Error de conexión al servidor
                        response.code() == 500 -> {
                            Toast.makeText(
                                this@EditCategoryActivity,
                                "Ha occurido un error en el servidor, por favor contacte al Administrador",
                                Toast.LENGTH_LONG
                            ).show()
                        }


                    }
                }

                override fun onFailure(call: Call<ResponseCategory>, t: Throwable) {
                    Toast.makeText(
                        this@EditCategoryActivity,
                        "FUN Ha ocurrido un error",
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
            name.setText("")
        }
    }

}