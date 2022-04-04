package com.example.ristorante.view.Menu

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ristorante.R
import com.example.ristorante.entity.Category
import com.example.ristorante.entity.Menu
import com.example.ristorante.services.InterfaceCategory
import com.example.ristorante.services.InterfaceMenu
import com.example.ristorante.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuFormActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var title: TextView
    private lateinit var name: EditText
    private lateinit var category: Category
    private lateinit var categorySpinner: Spinner
    private lateinit var price: EditText
    private lateinit var available: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_form)

        title = findViewById(R.id.title_m)
        name = findViewById(R.id.name_m)
        categorySpinner = findViewById(R.id.category_m)
        price = findViewById(R.id.price_m)
        available = findViewById(R.id.available_m)
        available.isChecked = true

        this.loadData()
        if(intent.getLongExtra("menu", 0) != 0L){
            title.setText("Editar platillo")
        } else {
            title.setText("Añadir platillo")
        }
    }

    fun loadData(){
        val service = ServiceB.buildService(InterfaceCategory::class.java)
        val call = service.getAll(intent.getLongExtra("restaurant", 0))

        call.enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                when {
                    response.code() == 200 -> {
                        val adapter: ArrayAdapter<Category> = ArrayAdapter<Category>(
                            applicationContext,
                            android.R.layout.simple_spinner_dropdown_item,
                            response.body()!!
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        categorySpinner.adapter = adapter

                        val menu = intent.getLongExtra("menu", 0L)
                        if(menu != 0L){
                            fillMenuData(menu, adapter)
                        }
                    }
                    response.code() == 401 -> { //Error en el navegador
                        Toast.makeText(this@MenuFormActivity, "Ha ocurrido un error. Intente más tarde.", Toast.LENGTH_LONG).show()
                    }
                    response.code() == 404 -> { // Tabla categorías vacia
                        Toast.makeText(this@MenuFormActivity, "No se encontraron categorías registradas.", Toast.LENGTH_LONG).show()
                    }
                    response.code() == 500 -> { // Error de conexión al servidor
                        Toast.makeText(this@MenuFormActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Toast.makeText(this@MenuFormActivity, "Ha ocurrido un error.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun fillMenuData(menuID: Long, adapter: ArrayAdapter<Category>){
        val service = ServiceB.buildService(InterfaceMenu::class.java)
        val call = service.getById(menuID)

        call.enqueue(object : Callback<Menu> {
            override fun onResponse(call2: Call<Menu>, response: Response<Menu>) {
                when {
                    response.code() == 200 -> {
                        var menu: Menu = response.body()!!
                        name.setText(menu.name)
                        price.setText(menu.price.toString())

                        available.isChecked = menu.available != 0L

                        for (i in 0 until adapter.count) {
                            val category = adapter.getItem(i) as Category
                            if(category.id == menu.category){
                                categorySpinner.setSelection(i)
                                break
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Menu>, t: Throwable) {
                Toast.makeText(this@MenuFormActivity,
                    "No se ha podido comunicar con el servidor, verifique su conexión o contacte a algun Administrador",
                    Toast.LENGTH_LONG).show()
            }
        })
    }

    fun validate(): Boolean {
        if(name.text.toString().isEmpty()){
            throw Exception("No ha establecido un nombre.")
        } else if (price.text.toString().isEmpty()){
            throw Exception("No ha establecido el precio.")
        }
        return true
    }

    // Función para guardar platillos
    fun save(view: View){
        if(validate()){
            val obj = Menu()
            obj.id = intent.getLongExtra("menu", 0)
            obj.name = name.text.toString()
            category = categorySpinner.selectedItem as Category
            obj.category = category.id
            obj.price = price.text.toString().toFloat()
            obj.available = if (available.isChecked) 1 else 0

            val service = ServiceB.buildService(InterfaceMenu::class.java)
            val call = service.save(obj)

            call.enqueue(object : Callback<Menu> {
                override fun onResponse(
                    call: Call<Menu>,
                    response: Response<Menu>
                ) {
                    when {
                        response.code() == 200 -> {
                            Toast.makeText(this@MenuFormActivity, "Guardado con éxito.", Toast.LENGTH_LONG)
                                .show()
                        }
                        response.code() == 401 -> {
                            Toast.makeText(
                                this@MenuFormActivity,
                                "Ha ocurrido un error al guardar el registro. Intente más tarde",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        response.code() == 500 -> {
                            Toast.makeText(
                                this@MenuFormActivity,
                                "Ha occurido un error en el servidor, por favor contacte al Administrador",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<Menu>, t: Throwable) {
                    Toast.makeText(this@MenuFormActivity, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
                }

            })
            setResult(Activity.RESULT_OK)
            this.finish()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        category = parent.getItemAtPosition(pos) as Category
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        //Another interface callback
    }
}

