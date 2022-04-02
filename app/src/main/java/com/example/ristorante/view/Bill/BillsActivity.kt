package com.example.ristorante.view.Bill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import com.example.ristorante.R
import com.example.ristorante.container.Bill
import com.example.ristorante.services.InterfaceBill
import com.example.ristorante.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillsActivity : AppCompatActivity() {
    private var table_bills: TableLayout?=null
    private lateinit var listData: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        table_bills = findViewById(R.id.table_bills)

        this.loadData()
    }

    fun add(view: View){
        val restaurant = intent.getLongExtra("restaurant", 0)
        val user = intent.getLongExtra("user", 0)
        var intent: Intent = Intent(
            this@BillsActivity,
            BillFormActivity::class.java
        ).apply{}
        intent.putExtra("bill", 0)
        intent.putExtra("restaurant", restaurant)
        intent.putExtra("user", user)
        startActivity(intent)
    }

    fun update(view: View){
        val restaurant = intent.getLongExtra("restaurant", 0)
        val user = intent.getLongExtra("user", 0)
        var intent: Intent = Intent(
            this@BillsActivity,
            BillFormActivity::class.java
        ).apply{}
        intent.putExtra("bill", view.id.toLong())
        intent.putExtra("restaurant", restaurant)
        intent.putExtra("user", user)
        startActivity(intent)
    }

    fun loadData() {
        val service = ServiceB.buildService(InterfaceBill::class.java)
        val call = service.getAll(intent.getLongExtra("restaurant", 0))

        call.enqueue(object : Callback<List<Bill>> {
            override fun onResponse(
                call: Call<List<Bill>>,
                response: Response<List<Bill>>
            ) {
                when {
                    response.code() == 200 -> {
                        listData = ArrayList()

                        response.body()!!.forEach {
                            val register = LayoutInflater.from(this@BillsActivity)
                                .inflate(R.layout.table_row_bill_waiter, null, false)
                            val colWaiter: TextView = register.findViewById<View>(R.id.colWaiter) as TextView
                            val colTable: TextView = register.findViewById<View>(R.id.colTable) as TextView
                            val colDate: TextView = register.findViewById<View>(R.id.colDate) as TextView
                            val btnEdit: View = register.findViewById<View>(R.id.btn_editBill) as Button
                            colDate.text = ""+it.dateBill
                            colTable.text = ""+it.diningTable
                            colWaiter.text = ""+ it.user
                            btnEdit.id = (""+ it.id).toInt()
                            table_bills?.addView(register)

                        }
                    }
                    response.code() == 401 -> {
                        Toast.makeText(this@BillsActivity, "Ha ocurrido un error. Intente más tarde", Toast.LENGTH_LONG).show()
                    }
                    response.code() == 404 -> {
                        Toast.makeText(this@BillsActivity, "No se encontraron cuentas registradas", Toast.LENGTH_LONG).show()
                    }
                    response.code() == 500 -> {
                        Toast.makeText(this@BillsActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<Bill>>, t: Throwable) {
                Toast.makeText(this@BillsActivity, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }
        })
    }
}