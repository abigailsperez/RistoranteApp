package com.example.ristorantehttp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.ristorantehttp.R
import com.example.ristorantehttp.container.ResponseMenu
import com.example.ristorantehttp.container.SaveMenu
import com.example.ristorantehttp.controller.ControllerMenu
import com.example.ristorantehttp.services.InterfaceSaveMenu
import com.example.ristorantehttp.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMealActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var category: EditText
    private lateinit var price: EditText
    private lateinit var available: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_meal)

        name= findViewById(R.id.name_m)
        category= findViewById(R.id.category_m)
        price= findViewById(R.id.price_m)
        available= findViewById(R.id.available)
    }

    //Función para editar platillos
    fun updateMenuFun(view: View) {

        if(name.text.toString().isEmpty()){
            Toast.makeText(this,"Campo nombre vacío.", Toast.LENGTH_LONG).show()
        }else if (category.text.toString().isEmpty()){
            Toast.makeText(this,"Campo categoría vacío.", Toast.LENGTH_LONG).show()
        }else if (price.text.toString().isEmpty()){
            Toast.makeText(this,"Campo precio vacío.", Toast.LENGTH_LONG).show()
        }else if (available.text.toString().isEmpty()){
            Toast.makeText(this,"Campo habilitar vacío.", Toast.LENGTH_LONG).show()
        } else {

            println("FUNCIÓN GUARDAR MENÚ")

            val idMenu = intent.getLongExtra("menu", 0)

            val saveMenuObj = SaveMenu()
            saveMenuObj.id = idMenu
            saveMenuObj.name = name.text.toString()//response.body()!!.restaurant
            saveMenuObj.category = category.text.toString().toLong() //response.body()!!.restaurant
            saveMenuObj.price = price.text.toString().toLong()
            //saveMenuObj.available = available.text.toString().toLong() //TextView del name(categoria)
            saveMenuObj.available = 1 //TextView del name(categoria)

            //Consumo de web services
            val service = ServiceB.buildService(InterfaceSaveMenu::class.java)
            val call = service.saveMenu(saveMenuObj)

            call.enqueue(object : Callback<ResponseMenu> {
                override fun onResponse(
                    call: Call<ResponseMenu>,
                    response: Response<ResponseMenu>
                ) {
                    when {

                        response.code() == 200 -> {
                            Toast.makeText(this@EditMealActivity, "GUARDADO", Toast.LENGTH_LONG)
                                .show()
                        }

                        response.code() == 401 -> {
                            Toast.makeText(
                                this@EditMealActivity,
                                "Ha ocurrido un error al guardar el registro. Intente más tarde",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        response.code() == 500 -> {
                            Toast.makeText(
                                this@EditMealActivity,
                                "Ha occurido un error en el servidor, por favor contacte al Administrador",
                                Toast.LENGTH_LONG
                            ).show()
                        }


                    }
                }

                override fun onFailure(call: Call<ResponseMenu>, t: Throwable) {
                    Toast.makeText(
                        this@EditMealActivity,
                        "FUN Ha ocurrido un error",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

            })
            name.setText("")
            category.setText("")
            price.setText("")
            available.setText("")
        }

        val intent: Intent = Intent(
            this,
            AddMealActivity::class.java
        ).apply {}
        startActivity(intent)
    }

}