package com.example.ristorante.view.Menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.ristorante.R
import com.example.ristorante.entity.Menu
import com.example.ristorante.services.InterfaceMenu
import com.example.ristorante.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.DecimalFormat

class MenuActivity: AppCompatActivity()  {

    private var table_menu: TableLayout? = null
    private lateinit var listData: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        table_menu = findViewById(R.id.table_menu)
        this.loadData()
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            this.loadData()
        }
    }

    fun add(view: View){
        val restaurant = intent.getLongExtra("restaurant", 0)
        var intent: Intent = Intent(
            this@MenuActivity,
            MenuFormActivity::class.java
        ).apply{}
        intent.putExtra("menu", 0L)
        intent.putExtra("restaurant", restaurant)
        getResult.launch(intent)
    }

    fun update(view: View){
        val restaurant = intent.getLongExtra("restaurant", 0)
        var intent: Intent = Intent(
            this@MenuActivity,
            MenuFormActivity::class.java
        ).apply{}
        intent.putExtra("menu", view.id.toLong())
        intent.putExtra("restaurant", restaurant)
        getResult.launch(intent)
    }

    fun loadData(){
        table_menu?.removeViews(1, Math.max(0, table_menu!!.getChildCount() - 1))

        val service = ServiceB.buildService(InterfaceMenu::class.java)
        val call = service.getAll(intent.getLongExtra("restaurant", 0))

        call.enqueue(object : Callback<List<Menu>> {
            override fun onResponse(
                call: Call<List<Menu>>,
                response: Response<List<Menu>>
            ) {
                when {
                    response.code() == 200 -> {
                        val df = DecimalFormat("#.##")
                        df.roundingMode = RoundingMode.DOWN

                        response.body()!!.forEach {
                            val register = LayoutInflater.from(this@MenuActivity)
                                .inflate(R.layout.table_row_menu, null, false)
                            val colName: TextView = register.findViewById<View>(R.id.colTable) as TextView
                            val colPrice: TextView = register.findViewById<View>(R.id.colDateBill) as TextView
                            val btnEditMenu: View = register.findViewById<View>(R.id.btn_editBill) as Button
                            colName.text = ""+it.name
                            colPrice.text = "$"+ df.format(it.price)
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