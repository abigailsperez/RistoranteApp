package com.example.ristorante.view.Category

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.example.ristorante.R
import com.example.ristorante.entity.Category
import com.example.ristorante.services.InterfaceCategory
import com.example.ristorante.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoryActivity : AppCompatActivity() {

    private var table_category: TableLayout? = null
    private lateinit var listData: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        table_category = findViewById(R.id.table_category)
        this.loadData()
    }

    private val getResult = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            this.loadData()
        }
    }

    fun add(view: View){
        val restaurant = intent.getLongExtra("restaurant", 0)
        var intent: Intent = Intent(
            this@CategoryActivity,
            CategoryFormActivity::class.java
        ).apply{}
        intent.putExtra("category", 0L)
        intent.putExtra("restaurant", restaurant)
        getResult.launch(intent)
    }

    fun update(view: View){
        val restaurant = intent.getLongExtra("restaurant", 0)
        var intent: Intent = Intent(
            this@CategoryActivity,
            CategoryFormActivity::class.java
        ).apply{}
        intent.putExtra("category", view.id.toLong())
        intent.putExtra("restaurant", restaurant)
        getResult.launch(intent)
    }

    // Función para obtener lista de categorias
    fun loadData(){
        table_category?.removeViews(1, Math.max(0, table_category!!.getChildCount() - 1))

        val service = ServiceB.buildService(InterfaceCategory::class.java)
        val call = service.getAll(intent.getLongExtra("restaurant", 0))

        call.enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                when {
                    response.code() == 200 -> {
                        response.body()!!.forEach {
                            val register = LayoutInflater.from(this@CategoryActivity)
                                    .inflate(R.layout.table_row_category, null, false)
                                val colId: TextView = register.findViewById<View>(R.id.colId) as TextView
                                val colName: TextView = register.findViewById<View>(R.id.colTable) as TextView
                                val btnEdit: View = register.findViewById<View>(R.id.btn_editCategory) as Button
                            colId.text = ""+ it.id
                            colName.text = ""+it.name
                            btnEdit.id = (""+ it.id).toInt()
                            table_category?.addView(register)
                        }
                    }
                    response.code() == 401 -> { //Error en el navegador
                         Toast.makeText(this@CategoryActivity, "Ha ocurrido un error. Intente más tarde.", Toast.LENGTH_LONG).show()
                    }
                     response.code() == 404 -> { // Tabla categorías vacia
                         Toast.makeText(this@CategoryActivity, "No se encontraron categorías registradas.", Toast.LENGTH_LONG).show()
                     }
                     response.code() == 500 -> { // Error de conexión al servidor
                         Toast.makeText(this@CategoryActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador.", Toast.LENGTH_LONG).show()
                     }
                }
            }
             override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                 Toast.makeText(this@CategoryActivity, "Ha ocurrido un error.", Toast.LENGTH_LONG).show()
             }
        })
    }
}