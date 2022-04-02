package com.example.ristorante.view.BillMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.ristorante.R
import com.example.ristorante.container.BillMenu
import com.example.ristorante.services.InterfaceBillMenu
import com.example.ristorante.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBillMenuActivity : AppCompatActivity() {

    private lateinit var menu: EditText
    private lateinit var quantity: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billmenu_form)

        menu= findViewById(R.id.menu)
        quantity= findViewById(R.id.quantity)
    }

//Consumo de web services
//Función para guardar categorias
    fun saveBillMenuFun(view: View) {

        println("FUNCIÓN GUARDAR CUENTA MENÚ")

        val bill = intent.getLongExtra("bill", 0)
        val saveBillMenuObj = BillMenu()
        saveBillMenuObj.bill = bill //TextView del name(categoria)
        saveBillMenuObj.menu = menu.text.toString().toLong() //response.body()!!.restaurant
        saveBillMenuObj.quantity = quantity.text.toString().toLong()

        val service = ServiceB.buildService(InterfaceBillMenu::class.java)
        val call = service.save(saveBillMenuObj)

        call.enqueue(object : Callback<BillMenu> {
            override fun onResponse(
                call: Call<BillMenu>,
                response: Response<BillMenu>
            ) {
                when {

                    response.code() == 200 -> {
                        Toast.makeText(this@AddBillMenuActivity, "Guardado con éxito.", Toast.LENGTH_LONG).show()
                    }

                    response.code() == 401 -> {
                        Toast.makeText(
                            this@AddBillMenuActivity,
                            "Ha ocurrido un error al guardar el registro. Intente más tarde",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    response.code() == 500 -> {
                        Toast.makeText(
                            this@AddBillMenuActivity,
                            "Ha occurido un error en el servidor, por favor contacte al Administrador",
                            Toast.LENGTH_LONG
                        ).show()
                    }


                }
            }

            override fun onFailure(call: Call<BillMenu>, t: Throwable) {
                Toast.makeText(this@AddBillMenuActivity, "FUN Ha ocurrido un error", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }
}

