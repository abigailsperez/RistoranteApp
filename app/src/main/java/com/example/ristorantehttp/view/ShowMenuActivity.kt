package com.example.ristorantehttp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.ristorantehttp.R
import com.example.ristorantehttp.container.ListMenuResPonseKT
import com.example.ristorantehttp.services.InterfaceListMenu
import com.example.ristorantehttp.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowMenuActivity : AppCompatActivity() {

    //private lateinit var spinner: Spinner
    private var table_menu: TableLayout?=null
    private lateinit var listData: ArrayList<String> //Función para obtener lista de platillos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_menu)

        table_menu=findViewById(R.id.table_menu)

        getListMenuFun(this)
    }

    //Función para obtener lista de platillos
    //private lateinit var listData: ArrayList<String>

    fun getListMenuFun(view: ShowMenuActivity){

        var otroContador = 0
        val rest =  intent.getLongExtra("restaurant", 0)
        val idRestaurant = rest //Número de restaurante

        //Consumo de web services
        val service = ServiceB.buildService(InterfaceListMenu::class.java)
        val call = service.getReceive(idRestaurant)

        call.enqueue(object : Callback<List<ListMenuResPonseKT>> {
            override fun onResponse(
                call: Call<List<ListMenuResPonseKT>>,
                response: Response<List<ListMenuResPonseKT>>
            ) {
                when {
                    response.code() == 200 -> {

                        listData = ArrayList()

                        response.body()!!.forEach {
                            //Se busca en la BD
                            var data =
                                "id " + it.id +
                                        "name" + it.name +
                                        "category" + it.category+
                                        "price" + it.price +
                                        "available" + it.available
                            listData.add(data)

                            //Se imprime en consola
                            println("\n\n")
                            println(
                                "\nid: " + it.id +
                                        "\nname: " + it.name +
                                "\ncategory" + it.category +
                                        "\nprice" + it.price +
                                        "\navailable" + it.available)

                            //Se llena la tabla

                            val register =
                                LayoutInflater.from(this@ShowMenuActivity).inflate(R.layout.table_row_menu, null, false)
                            //val colId: TextView = register.findViewById<View>(R.id.colId) as TextView
                            val colName: TextView = register.findViewById<View>(R.id.colName) as TextView
                            val colPrice: TextView = register.findViewById<View>(R.id.colPrice) as TextView
                            val colCategory: TextView = register.findViewById<View>(R.id.colCategory) as TextView
                            val btnEditMenu: View = register.findViewById<View>(R.id.btn_editMenu) as Button
                            //colId.text = ""+ it.id
                            colName.text = ""+it.name
                            colPrice.text = ""+ it.price
                            colCategory.text  = ""+ it.category
                            btnEditMenu.id = (""+ it.id).toInt()
                            table_menu?.addView(register)

                            otroContador ++
                        }
                    }
                    //Error en el navegador
                    response.code() == 401 -> {
                        Toast.makeText(this@ShowMenuActivity, "Ha ocurrido un error. Intente más tarde", Toast.LENGTH_LONG).show()
                    }

                    //Tabla categorías vacia
                    response.code() == 404 -> {
                        Toast.makeText(this@ShowMenuActivity, "No se encontraron categorias registrados", Toast.LENGTH_LONG).show()
                    }

                    //Error de conexión al servidor
                    response.code() == 500 -> {
                        Toast.makeText(this@ShowMenuActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<ListMenuResPonseKT>>, t: Throwable) {
                Toast.makeText(this@ShowMenuActivity, "FUN Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }

        })

    }

    fun tableEditMenu(view: View){
        val idMenu: Long = view.id.toLong()
        Toast.makeText(this,view.id.toString(), Toast.LENGTH_LONG).show()
        val intent: Intent = Intent(
            this,
            EditMealActivity::class.java
        ).apply {}
        intent.putExtra("menu", idMenu)
        startActivity(intent)
    }

}