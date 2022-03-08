package com.example.ristorantehttp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import com.example.ristorantehttp.R
import com.example.ristorantehttp.container.ListSessionResPonseKT
import com.example.ristorantehttp.services.InterfaceListSession
import com.example.ristorantehttp.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowSessionOutActivity : AppCompatActivity() {

    private var table_seOut: TableLayout?=null
    private lateinit var listData: ArrayList<String> //Función para obtener la lista de sesiones

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_session_out)

        table_seOut=findViewById(R.id.table_seOut)

        getListSeOutFun(this)
    }
    fun getListSeOutFun(view: ShowSessionOutActivity){

        var otroContador = 0
        val rest=  intent.getLongExtra("restaurant", 0)
        val idRestaurant = rest //Número de restaurante

        //Consumo de web services
        val service = ServiceB.buildService(InterfaceListSession::class.java)
        val call = service.getReceiveOut(idRestaurant)

        call.enqueue(object : Callback<List<ListSessionResPonseKT>> {
            override fun onResponse(
                call: Call<List<ListSessionResPonseKT>>,
                response: Response<List<ListSessionResPonseKT>>
            ) {
                when {
                    response.code() == 200 -> {

                        listData = ArrayList()

                        response.body()!!.forEach {
                            //Se busca en la BD
                            var data =
                                "id " + it.id +
                                        "date_session" + it.dateSession+
                                        "in_out" + it.inOut +
                                        "user" + it.user
                            listData.add(data)

                            //Se imprime en consola
                            println("\n\n")
                            println(
                                "\nid: " + it.id +
                                        "\ndate_session: " + it.dateSession+
                                        "\nin_out: " + it.inOut +
                                        "\nuser: " + it.user)

                            //Se llena la tabla

                            val register =
                                LayoutInflater.from(this@ShowSessionOutActivity).inflate(R.layout.table_row_in, null, false)
                            val colName: TextView = register.findViewById<View>(R.id.colName) as TextView
                            val colDate: TextView = register.findViewById<View>(R.id.colDate) as TextView
                            colName.text = ""+it.user
                            colDate.text = ""+it.dateSession
                            table_seOut?.addView(register)

                            otroContador ++
                        }
                    }
                    //Error en el navegador
                    response.code() == 401 -> {
                        Toast.makeText(this@ShowSessionOutActivity, "Ha ocurrido un error. Intente más tarde", Toast.LENGTH_LONG).show()
                    }

                    //Tabla categorías vacia
                    response.code() == 404 -> {
                        Toast.makeText(this@ShowSessionOutActivity, "No se encontraron usuarios registrados", Toast.LENGTH_LONG).show()
                    }

                    //Error de conexión al servidor
                    response.code() == 500 -> {
                        Toast.makeText(this@ShowSessionOutActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<ListSessionResPonseKT>>, t: Throwable) {
                Toast.makeText(this@ShowSessionOutActivity, "FUN Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }

        })

    }
}