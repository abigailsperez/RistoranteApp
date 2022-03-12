package com.example.ristorantehttp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.ristorantehttp.R
import com.example.ristorantehttp.container.ListBillResPonseKT
import com.example.ristorantehttp.container.ListCategoriaResPonseKT
import com.example.ristorantehttp.container.ResponseBill
import com.example.ristorantehttp.container.SaveBill
import com.example.ristorantehttp.controller.ControllerBill
import com.example.ristorantehttp.services.InterfaceBill
import com.example.ristorantehttp.services.InterfaceListCategory
import com.example.ristorantehttp.services.ServiceB
import okhttp3.internal.http.toHttpDateOrNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddBillActivity : AppCompatActivity() {

    private lateinit var diningT: EditText
    private var table_bills: TableLayout?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bill)

        diningT= findViewById(R.id.diningTable)
        table_bills=findViewById(R.id.table_bills)
    }

    //Función para guardar cuentas
    fun saveBillFun(view: View) {

        println("FUNCIÓN GUARDAR CUENTA")

        val idUser =  intent.getLongExtra("idUser", 0)
        val c: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val strDate: String = sdf.format(c.getTime())
        println(""+strDate)

        val saveBillObj = SaveBill()
        saveBillObj.date_Bill= strDate
        saveBillObj.completed= 0
        saveBillObj.diningTable = diningT.text.toString().toLong() //TextView del name(categoria)
        saveBillObj.user = idUser //response.body()!!.restaurant

        println("mesa: "+  diningT.text)

        val service = ServiceB.buildService(InterfaceBill::class.java) //Consumo de web services
        val call = service.saveBill(saveBillObj)

        call.enqueue(object : Callback<ResponseBill> {
            override fun onResponse(
                call: Call<ResponseBill>,
                response: Response<ResponseBill>
            ) {
                when {

                    response.code() == 200 -> {
                        Toast.makeText(this@AddBillActivity, "Guardado con éxito.", Toast.LENGTH_LONG).show()
                    }

                    response.code() == 401 -> {
                        Toast.makeText(
                            this@AddBillActivity,
                            "Ha ocurrido un error al guardar el registro. Intente más tarde",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    response.code() == 500 -> {
                        Toast.makeText(
                            this@AddBillActivity,
                            "Ha occurido un error en el servidor, por favor contacte al Administrador",
                            Toast.LENGTH_LONG
                        ).show()
                    }


                }
            }

            override fun onFailure(call: Call<ResponseBill>, t: Throwable) {
                Toast.makeText(this@AddBillActivity, "FUN Ha ocurrido un error", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }

    //Función para obtener lista de cuentas del mesero
    private lateinit var listData: ArrayList<String>

    fun getListBillsFun(view: View){

        var otroContador = 0
        //val idUser =  intent.getLongExtra("idUser", 0)
        val idRest =  intent.getLongExtra("restaurant", 0)

        //Consumo de web services
        val service = ServiceB.buildService(InterfaceBill::class.java)
        val call = service.getBill(idRest)

        call.enqueue(object : Callback<List<ListBillResPonseKT>> {
            override fun onResponse(
                call: Call<List<ListBillResPonseKT>>,
                response: Response<List<ListBillResPonseKT>>
            ) {
                when {
                    response.code() == 200 -> {

                        listData = ArrayList()

                        response.body()!!.forEach {
                            //Se busca en la BD
                            var data =
                                "id " + it.id +
                                        "completed" + it.completed +
                                        "date_bill" + it.date_Bill +
                                        "dining_table" + it.diningTable +
                                        "user" + it.user
                            listData.add(data)

                            //Se imprime en consola
                            println("\n\n")
                            println(
                                "\nid: " + it.id +
                                        "\ncompleted: " + it.completed +
                                        "\ndate_bill: " + it.date_Bill+
                                        "\ndining_table: " + it.diningTable+
                                        "\nuser: " + it.user)

                            //Se llena la tabla

                            val register =
                                LayoutInflater.from(this@AddBillActivity).inflate(R.layout.table_row_bill_waiter, null, false)
                            val colWaiter: TextView = register.findViewById<View>(R.id.colWaiter) as TextView
                            val colTable: TextView = register.findViewById<View>(R.id.colTable) as TextView
                            val colDate: TextView = register.findViewById<View>(R.id.colDate) as TextView
                            val btnEdit: View = register.findViewById<View>(R.id.btn_editBill) as Button
                            colDate.text = ""+it.date_Bill.toString()
                            colTable.text = ""+it.diningTable
                            colWaiter.text = ""+ it.user
                            btnEdit.id = (""+ it.id).toInt()
                            table_bills?.addView(register)

                            otroContador ++
                        }
                    }
                    //Error en el navegador
                    response.code() == 401 -> {
                        Toast.makeText(this@AddBillActivity, "Ha ocurrido un error. Intente más tarde", Toast.LENGTH_LONG).show()
                    }

                    //Tabla categorías vacia
                    response.code() == 404 -> {
                        Toast.makeText(this@AddBillActivity, "No se encontraron cuentas registradas", Toast.LENGTH_LONG).show()
                    }

                    //Error de conexión al servidor
                    response.code() == 500 -> {
                        Toast.makeText(this@AddBillActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<ListBillResPonseKT>>, t: Throwable) {
                Toast.makeText(this@AddBillActivity, "FUN Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }

        })

    }

    fun tableEdit(view: View){
        val idBill: Long = view.id.toLong()
        Toast.makeText(this,view.id.toString(), Toast.LENGTH_LONG).show()
        val intent: Intent = Intent(
            this,
            EditBillMenuActivity::class.java
        ).apply {}
        intent.putExtra("bill", idBill)
        startActivity(intent)
    }
}



