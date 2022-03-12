package com.example.ristorantehttp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.ristorantehttp.R
import com.example.ristorantehttp.container.ResponseBill
import com.example.ristorantehttp.container.ResponseBillMenu
import com.example.ristorantehttp.container.SaveBill
import com.example.ristorantehttp.container.SaveBillMenu
import com.example.ristorantehttp.controller.ControllerBill_Menu
import com.example.ristorantehttp.services.InterfaceBill
import com.example.ristorantehttp.services.InterfaceBillMenu
import com.example.ristorantehttp.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBillMenuActivity : AppCompatActivity() {

    private lateinit var menu: EditText
    private lateinit var quantity: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bill_menu)

        menu= findViewById(R.id.menu)
        quantity= findViewById(R.id.quantity)
    }

//Consumo de web services
//Función para guardar categorias
    fun saveBillMenuFun(view: View) {

        println("FUNCIÓN GUARDAR CUENTA MENÚ")

        val bill = intent.getLongExtra("bill", 0)
        val saveBillMenuObj = SaveBillMenu()
        saveBillMenuObj.bill = bill //TextView del name(categoria)
        saveBillMenuObj.menu = menu.text.toString().toLong() //response.body()!!.restaurant
        saveBillMenuObj.quantity = quantity.text.toString().toLong()

        val service = ServiceB.buildService(InterfaceBillMenu::class.java)
        val call = service.saveBillMenu(saveBillMenuObj)

        call.enqueue(object : Callback<ResponseBillMenu> {
            override fun onResponse(
                call: Call<ResponseBillMenu>,
                response: Response<ResponseBillMenu>
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

            override fun onFailure(call: Call<ResponseBillMenu>, t: Throwable) {
                Toast.makeText(this@AddBillMenuActivity, "FUN Ha ocurrido un error", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }
}

