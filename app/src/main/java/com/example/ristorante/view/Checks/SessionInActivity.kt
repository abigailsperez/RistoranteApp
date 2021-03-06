package com.example.ristorante.view.Checks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import com.example.ristorante.R
import com.example.ristorante.containers.SessionContainer
import com.example.ristorante.entity.Session
import com.example.ristorante.services.InterfaceSession
import com.example.ristorante.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SessionInActivity : AppCompatActivity() {

    private var table_session: TableLayout? = null
    private lateinit var listData: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_in)

        table_session = findViewById(R.id.table_seIn)

        this.loadData()
    }

    fun loadData(){
        val service = ServiceB.buildService(InterfaceSession::class.java)
        val call = service.getAllIn(intent.getLongExtra("restaurant", 0))

        call.enqueue(object : Callback<List<SessionContainer>> {
            override fun onResponse(
                call: Call<List<SessionContainer>>,
                response: Response<List<SessionContainer>>
            ) {
                when {
                    response.code() == 200 -> {
                        listData = ArrayList()
                        response.body()!!.forEach {
                            val register = LayoutInflater.from(this@SessionInActivity)
                                .inflate(R.layout.table_row_session, null, false)
                            val colName: TextView = register.findViewById<View>(R.id.colTable) as TextView
                            val colDate: TextView = register.findViewById<View>(R.id.colDate) as TextView
                            colName.text = "" + it.name
                            colDate.text = "" + it.dateSession
                            table_session?.addView(register)
                        }
                    } //Error en el navegador
                    response.code() == 401 -> {
                        Toast.makeText(this@SessionInActivity, "Ha ocurrido un error. Intente m??s tarde", Toast.LENGTH_LONG).show()
                    } //Tabla categor??as vacia
                    response.code() == 404 -> {
                        Toast.makeText(this@SessionInActivity, "No se encontraron usuarios registrados", Toast.LENGTH_LONG).show()
                    } //Error de conexi??n al servidor
                    response.code() == 500 -> {
                        Toast.makeText(this@SessionInActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<SessionContainer>>, t: Throwable) {
                Toast.makeText(this@SessionInActivity, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }
        })
    }
}