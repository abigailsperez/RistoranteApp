package com.example.ristorante.view.Bill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.ristorante.R
import com.example.ristorante.container.Bill
import com.example.ristorante.services.InterfaceBill
import com.example.ristorante.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillFormActivity : AppCompatActivity() {

    private lateinit var diningT: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_form)

        diningT = findViewById(R.id.diningTable)
    }

    fun save(view: View) {
        val obj = Bill()
        obj.id = intent.getLongExtra("bill", 0)
        obj.completed = 0
        obj.diningTable = diningT.text.toString().toLong()
        obj.user = intent.getLongExtra("user", 0)

        val service = ServiceB.buildService(InterfaceBill::class.java) // Consumo de web services
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
    }
}



