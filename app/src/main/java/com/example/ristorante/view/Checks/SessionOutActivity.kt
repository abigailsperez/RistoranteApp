package com.example.ristorante.view.Checks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import com.example.ristorante.R
import com.example.ristorante.container.Session
import com.example.ristorante.services.InterfaceSession
import com.example.ristorante.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SessionOutActivity : AppCompatActivity() {

    private var table_seOut: TableLayout?=null
    private lateinit var listData: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_out)

        table_seOut = findViewById(R.id.table_seOut)

        this.loadData()
    }
    fun loadData(){
        val service = ServiceB.buildService(InterfaceSession::class.java)
        val call = service.getAllOut(intent.getLongExtra("restaurant", 0))

        call.enqueue(object : Callback<List<Session>> {
            override fun onResponse(
                call: Call<List<Session>>,
                response: Response<List<Session>>
            ) {
                when {
                    response.code() == 200 -> {
                        listData = ArrayList()
                        response.body()!!.forEach {
                            val register = LayoutInflater.from(this@SessionOutActivity)
                                .inflate(R.layout.table_row_in, null, false)
                            val colName: TextView = register.findViewById<View>(R.id.colName) as TextView
                            val colDate: TextView = register.findViewById<View>(R.id.colDate) as TextView
                            colName.text = "" + it.user
                            colDate.text = "" + it.dateSession
                            table_seOut?.addView(register)
                        }
                    } //Error en el navegador
                    response.code() == 401 -> {
                        Toast.makeText(this@SessionOutActivity, "Ha ocurrido un error. Intente más tarde", Toast.LENGTH_LONG).show()
                    } //Tabla categorías vacia
                    response.code() == 404 -> {
                        Toast.makeText(this@SessionOutActivity, "No se encontraron usuarios registrados", Toast.LENGTH_LONG).show()
                    } //Error de conexión al servidor
                    response.code() == 500 -> {
                        Toast.makeText(this@SessionOutActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<Session>>, t: Throwable) {
                Toast.makeText(this@SessionOutActivity, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }
        })
    }
}