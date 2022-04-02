package com.example.ristorante.view.Menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ristorante.R
import com.example.ristorante.container.Menu
import com.example.ristorante.services.InterfaceMenu
import com.example.ristorante.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity: AppCompatActivity()  {

    private var table_menu: TableLayout? = null
    private lateinit var listData: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        table_menu = findViewById(R.id.table_menu)
        this.loadData()
    }

    fun add(view: View){
        val restaurant = intent.getLongExtra("restaurant", 0)
        var intent: Intent = Intent(
            this@MenuActivity,
            MenuFormActivity::class.java
        ).apply{}
        intent.putExtra("menu", 0)
        intent.putExtra("restaurant", restaurant)
        startActivity(intent)
    }

    fun update(view: View){
        val restaurant = intent.getLongExtra("restaurant", 0)
        var intent: Intent = Intent(
            this@MenuActivity,
            MenuFormActivity::class.java
        ).apply{}
        intent.putExtra("menu", view.id.toLong())
        intent.putExtra("restaurant", restaurant)
        startActivity(intent)
    }

    fun loadData(){
        val service = ServiceB.buildService(InterfaceMenu::class.java)
        val call = service.getAll(intent.getLongExtra("restaurant", 0))

        call.enqueue(object : Callback<List<Menu>> {
            override fun onResponse(
                call: Call<List<Menu>>,
                response: Response<List<Menu>>
            ) {
                when {
                    response.code() == 200 -> {

                        listData = ArrayList()

                        response.body()!!.forEach {
                            var data = "id " + it.id +
                                        "name" + it.name +
                                        "category" + it.category+
                                        "price" + it.price +
                                        "available" + it.available
                            listData.add(data)

                            val register =
                                LayoutInflater.from(this@MenuActivity).inflate(R.layout.table_row_menu, null, false)
                            val colName: TextView = register.findViewById<View>(R.id.colName) as TextView
                            val colPrice: TextView = register.findViewById<View>(R.id.colPrice) as TextView
                            val btnEditMenu: View = register.findViewById<View>(R.id.btn_editMenu) as Button
                            colName.text = ""+it.name
                            colPrice.text = "$"+ it.price
                            btnEditMenu.id = (""+ it.id).toInt()
                            table_menu?.addView(register)
                        }
                    } //Error en el navegador
                    response.code() == 401 -> {
                        Toast.makeText(this@MenuActivity, "Ha ocurrido un error. Intente más tarde", Toast.LENGTH_LONG).show()
                    } //Tabla categorías vacia
                    response.code() == 404 -> {
                        Toast.makeText(this@MenuActivity, "No se encontraron categorias registrados", Toast.LENGTH_LONG).show()
                    } //Error de conexión al servidor
                    response.code() == 500 -> {
                        Toast.makeText(this@MenuActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<Menu>>, t: Throwable) {
                Toast.makeText(this@MenuActivity, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }
        })
    }
}