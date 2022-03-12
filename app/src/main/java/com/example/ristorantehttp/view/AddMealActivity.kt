package com.example.ristorantehttp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.ristorantehttp.R
import com.example.ristorantehttp.container.ResponseMenu
import com.example.ristorantehttp.container.SaveMenu
import com.example.ristorantehttp.services.InterfaceSaveMenu
import com.example.ristorantehttp.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast


class AddMealActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var name: EditText
    private lateinit var category: EditText
    private lateinit var price: EditText
    //private lateinit var available: EditText
    private lateinit var av_spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meal)

        name= findViewById(R.id.name_m)
        category= findViewById(R.id.category_m)
        price= findViewById(R.id.price_m)
        //available= findViewById(R.id.available)
        av_spinner = findViewById(R.id.available_spinner)

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.available_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            av_spinner.adapter = adapter
        }
        av_spinner.onItemSelectedListener = this
    }

    //Función para guardar platillos
    fun saveMenuFun(view: View) {

        if(name.text.toString().isEmpty()){
            Toast.makeText(this,"Campo nombre vacío.", Toast.LENGTH_LONG).show()
        }else if (category.text.toString().isEmpty()){
                Toast.makeText(this,"Campo categoría vacío.", Toast.LENGTH_LONG).show()
        }else if (price.text.toString().isEmpty()){
            Toast.makeText(this,"Campo precio vacío.", Toast.LENGTH_LONG).show()
        } else {

            println("FUNCIÓN GUARDAR MENÚ")

            val saveMenuObj = SaveMenu()
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
                            Toast.makeText(this@AddMealActivity, "Guardado con éxito.", Toast.LENGTH_LONG)
                                .show()
                        }

                        response.code() == 401 -> {
                            Toast.makeText(
                                this@AddMealActivity,
                                "Ha ocurrido un error al guardar el registro. Intente más tarde",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        response.code() == 500 -> {
                            Toast.makeText(
                                this@AddMealActivity,
                                "Ha occurido un error en el servidor, por favor contacte al Administrador",
                                Toast.LENGTH_LONG
                            ).show()
                        }


                    }
                }

                override fun onFailure(call: Call<ResponseMenu>, t: Throwable) {
                    Toast.makeText(
                        this@AddMealActivity,
                        "FUN Ha ocurrido un error",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

            })
            name.setText("")
            category.setText("")
            price.setText("")
            //available.setText("")
        }
    }

    fun viewMenu(view: android.view.View) {
        //Toast.makeText(this,view.id.toString(), Toast.LENGTH_LONG).show()
        val rest =  intent.getLongExtra("restaurant", 0)
        val intent: Intent = Intent(
            this,
            ShowMenuActivity::class.java
        ).apply {}
        intent.putExtra("restaurant", rest)
        startActivity(intent)
    }

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            val available = parent.getItemAtPosition(pos)
            if (available == 0) {
                println("habilitado")
            }else if (available == 1){
                println("deshabilitado")
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            //Another interface callback
        }

    /*override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        parent.getItemAtPosition(pos)
        Toast.makeText(
            adapterView.getContext(),
            (adapterView.getItemAtPosition(position)).getNombre(),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }*/

}

