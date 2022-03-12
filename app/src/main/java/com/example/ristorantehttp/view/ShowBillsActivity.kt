package com.example.ristorantehttp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import com.example.ristorantehttp.R
import com.example.ristorantehttp.container.ListBillResPonseKT
import com.example.ristorantehttp.services.InterfaceBill
import com.example.ristorantehttp.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowBillsActivity : AppCompatActivity() {

    //private lateinit var spinner: Spinner
    private var table_bills: TableLayout?=null
    private lateinit var listData: ArrayList<String> //Función para obtener lista de platillos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_bills)

        table_bills=findViewById(R.id.table_bills)

        getListBillsFun(this)
    }

    //Función para obtener lista de platillos
    fun getListBillsFun(showBillsActivity: ShowBillsActivity) {

        println("FUNCIÓN VER CUENTAS")

        var otroContador = 0
        val rest =  intent.getLongExtra("restaurant", 0)
        val idRestaurant = rest //Número de restaurante

        //Consumo de web services
        val service = ServiceB.buildService(InterfaceBill::class.java)
        val call = service.getBill(idRestaurant)

        call.enqueue(object : Callback<List<ListBillResPonseKT>> {
            override fun onResponse(
                call: Call<List<ListBillResPonseKT>>,
                response: Response<List<ListBillResPonseKT>>
            ) {
                when {
                    response.code() == 200 -> {

                        listData = ArrayList()

                        response.body()!!.forEach {
                            //Se busca en la BD
                            var data =
                                "id " + it.id +
                                        "date_bill" + it.date_Bill +
                                        "dining_table" + it.diningTable +
                                        "user" + it.user
                            listData.add(data)

                            //Se imprime en consola
                            println("\n\n")
                            println(
                                "\nid: " + it.id +
                                        "\ndate_bill: " + it.date_Bill +
                                        "\ndining_table: " + it.diningTable +
                                        "\nuser: " + it.user)

                            //Se llena la tabla

                            val register =
                                LayoutInflater.from(this@ShowBillsActivity).inflate(R.layout.table_row_bills, null, false)
                            val colId: TextView = register.findViewById<View>(R.id.colId) as TextView
                            val colTable: TextView = register.findViewById<View>(R.id.colTable) as TextView
                            val colWaiter: TextView = register.findViewById<View>(R.id.colWaiter) as TextView
                            val colDate: TextView = register.findViewById<View>(R.id.colDate) as TextView
                            colId.text = ""+ it.id
                            colTable.text = ""+ it.diningTable
                            colWaiter.text = ""+it.user
                            colDate.text = ""+ it.date_Bill
                            table_bills?.addView(register)

                            otroContador ++
                        }
                    }
                    //Error en el navegador
                    response.code() == 401 -> {
                        Toast.makeText(this@ShowBillsActivity, "Ha ocurrido un error. Intente más tarde", Toast.LENGTH_LONG).show()
                    }

                    //Tabla categorías vacia
                    response.code() == 404 -> {
                        Toast.makeText(this@ShowBillsActivity, "No se encontraron cuentas registrados", Toast.LENGTH_LONG).show()
                    }

                    //Error de conexión al servidor
                    response.code() == 500 -> {
                        Toast.makeText(this@ShowBillsActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<ListBillResPonseKT>>, t: Throwable) {
                Toast.makeText(this@ShowBillsActivity, "FUN Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }

        })

    }

}