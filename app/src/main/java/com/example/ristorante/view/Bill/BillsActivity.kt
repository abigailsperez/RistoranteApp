package com.example.ristorante.view.Bill

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ristorante.R
import com.example.ristorante.containers.BillContainer
import com.example.ristorante.entity.Bill
import com.example.ristorante.services.InterfaceBill
import com.example.ristorante.services.ServiceB
import com.example.ristorante.view.BillMenu.BillMenuActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillsActivity : AppCompatActivity() {
    private var table_bills: TableLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        table_bills = findViewById(R.id.table_bills)
        this.loadData()
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            this.loadData()
        }
    }

    fun add(view: View){
        val restaurant = intent.getLongExtra("restaurant", 0)
        val user = intent.getLongExtra("user", 0)
        var intent: Intent = Intent(
            this@BillsActivity,
            BillFormActivity::class.java
        ).apply{}
        intent.putExtra("bill", 0L)
        intent.putExtra("restaurant", restaurant)
        intent.putExtra("user", user)
        getResult.launch(intent)
    }

    fun update(view: View){
        val restaurant = intent.getLongExtra("restaurant", 0)
        val user = intent.getLongExtra("user", 0)
        var intent: Intent = Intent(
            this@BillsActivity,
            BillMenuActivity::class.java
        ).apply{}
        intent.putExtra("bill", view.id.toLong())
        intent.putExtra("restaurant", restaurant)
        intent.putExtra("user", user)
        getResult.launch(intent)
    }

    fun loadData() {
        table_bills?.removeViews(1, Math.max(0, table_bills!!.getChildCount() - 1))

        val service = ServiceB.buildService(InterfaceBill::class.java)
        val call = service.getAll(intent.getLongExtra("restaurant", 0))

        call.enqueue(object : Callback<List<BillContainer>> {
            override fun onResponse(
                call: Call<List<BillContainer>>,
                response: Response<List<BillContainer>>
            ) {
                when {
                    response.code() == 200 -> {
                        response.body()!!.forEach {
                            val register = LayoutInflater.from(this@BillsActivity)
                                .inflate(R.layout.table_row_bill, null, false)
                            val colTable: TextView = register.findViewById<View>(R.id.colTable) as TextView
                            val colDate: TextView = register.findViewById<View>(R.id.colDateBill) as TextView
                            val btnEdit: View = register.findViewById<View>(R.id.btn_editBill) as Button
                            colDate.text = ""+it.dateBill
                            colTable.text = ""+it.code
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
            override fun onFailure(call: Call<List<BillContainer>>, t: Throwable) {
                Toast.makeText(this@BillsActivity, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }
        })
    }
}