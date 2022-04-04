package com.example.ristorante.view.Bill

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.ristorante.R
import com.example.ristorante.entity.Bill
import com.example.ristorante.entity.DiningTable
import com.example.ristorante.services.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillFormActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var dt: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_form)

        title = findViewById(R.id.title_b)
        dt = findViewById(R.id.dtSpinner)
        this.loadData()
        if(intent.getLongExtra("menu", 0) != 0L){
            title.setText("Editar orden")
        } else {
            title.setText("Añadir orden")
        }
    }

    fun loadData(){
        val service = ServiceB.buildService(InterfaceDiningTable::class.java)
        val call = service.getAll(intent.getLongExtra("restaurant", 0))

        call.enqueue(object : Callback<List<DiningTable>> {
            override fun onResponse(
                call: Call<List<DiningTable>>,
                response: Response<List<DiningTable>>
            ) {
                when {
                    response.code() == 200 -> {
                        val adapter: ArrayAdapter<DiningTable> = ArrayAdapter<DiningTable>(
                            applicationContext,
                            android.R.layout.simple_spinner_dropdown_item,
                            response.body()!!
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        dt.adapter = adapter

                        val bill = intent.getLongExtra("bill", 0L)
                        if(bill != 0L){
                            for (i in 0 until adapter.count) {
                                val d = adapter.getItem(i) as DiningTable
                                if (d.id == bill) {
                                    dt.setSelection(i)
                                    break
                                }
                            }
                        }
                    }
                    response.code() == 401 -> { //Error en el navegador
                        Toast.makeText(this@BillFormActivity, "Ha ocurrido un error. Intente más tarde.", Toast.LENGTH_LONG).show()
                    }
                    response.code() == 404 -> { // Tabla categorías vacia
                        Toast.makeText(this@BillFormActivity, "No se encontraron categorías registradas.", Toast.LENGTH_LONG).show()
                    }
                    response.code() == 500 -> { // Error de conexión al servidor
                        Toast.makeText(this@BillFormActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<DiningTable>>, t: Throwable) {
                Toast.makeText(this@BillFormActivity, "Ha ocurrido un error.", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun save(view: View) {
        val obj = Bill()
        obj.id = intent.getLongExtra("bill", 0)
        obj.completed = 0
        val diningTable = dt.selectedItem as DiningTable
        obj.diningTable = diningTable.id
        obj.user = intent.getLongExtra("user", 0)

        val service = ServiceB.buildService(InterfaceBill::class.java)
        val call = service.save(obj)

        call.enqueue(object : Callback<Bill> {
            override fun onResponse(
                call: Call<Bill>,
                response: Response<Bill>
            ) {
                when {
                    response.code() == 200 -> {
                        Toast.makeText(this@BillFormActivity, "Guardado con éxito.", Toast.LENGTH_LONG).show()
                    }
                    response.code() == 401 -> {
                        Toast.makeText(
                            this@BillFormActivity,
                            "Ha ocurrido un error al guardar el registro. Intente más tarde",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    response.code() == 500 -> {
                        Toast.makeText(
                            this@BillFormActivity,
                            "Ha occurido un error en el servidor, por favor contacte al Administrador",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<Bill>, t: Throwable) {
                Toast.makeText(this@BillFormActivity, "Ha ocurrido un error", Toast.LENGTH_LONG)
                    .show()
            }
        })
        setResult(Activity.RESULT_OK)
        this.finish()
    }
}



