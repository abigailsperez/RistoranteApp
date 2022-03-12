package com.example.ristorantehttp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.ristorantehttp.R
import com.example.ristorantehttp.container.ListBillMenuResPonseKT
import com.example.ristorantehttp.container.ListCategoriaResPonseKT
import com.example.ristorantehttp.services.InterfaceBillMenu
import com.example.ristorantehttp.services.InterfaceListCategory
import com.example.ristorantehttp.services.ServiceB
import kotlinx.android.synthetic.main.activity_edit_bill_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditBillMenuActivity : AppCompatActivity() {

    private var table_billmenu: TableLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bill_menu)

        table_billmenu=findViewById(R.id.table_billmenu)
        getBillMenuCategoryFun(this)
    }

    fun addBillMenu(view: View){
        val idBill =  intent.getLongExtra("bill", 0)
        //Toast.makeText(this,view.idBill, Toast.LENGTH_LONG).show()
        val intent: Intent = Intent(
            this,
            AddBillMenuActivity::class.java
        ).apply {}
        intent.putExtra("bill", idBill)
        startActivity(intent)
    }

    //Función para obtener lista de categorias
    private lateinit var listData: ArrayList<String>

    fun getBillMenuCategoryFun(view: EditBillMenuActivity){

        var otroContador = 0
        val idBill =  intent.getLongExtra("bill", 0)

        //Consumo de web services
        val service = ServiceB.buildService(InterfaceBillMenu::class.java)
        val call = service.getBillMenu(idBill)

        call.enqueue(object : Callback<List<ListBillMenuResPonseKT>> {
            override fun onResponse(
                call: Call<List<ListBillMenuResPonseKT>>,
                response: Response<List<ListBillMenuResPonseKT>>
            ) {
                when {
                    response.code() == 200 -> {

                        listData = ArrayList()

                        response.body()!!.forEach {
                            //Se busca en la BD
                            var data =
                                "id " + it.id +
                                        "bill" + it.bill +
                                        "menu" + it.menu+
                                        "quantity" + it.quantity
                            listData.add(data)

                            //Se imprime en consola
                            println("\n\n")
                            println(
                                "\nid: " + it.id +
                                        "\nname: " + it.bill)

                            //Se llena la tabla
                            val register =
                                LayoutInflater.from(this@EditBillMenuActivity).inflate(R.layout.table_row_bill_menu, null, false)
                            val colMenu: TextView = register.findViewById<View>(R.id.colMenu) as TextView
                            val colQuantity: TextView = register.findViewById<View>(R.id.colQuantity) as TextView
                            val btnEdit: View = register.findViewById<View>(R.id.btn_deleteMenu) as Button
                            colMenu.text = ""+ it.menu
                            colQuantity.text = ""+it.quantity
                            btnEdit.id = (""+ it.id).toInt()
                            table_billmenu?.addView(register)

                            otroContador ++
                        }
                    }
                    //Error en el navegador
                    response.code() == 401 -> {
                        Toast.makeText(this@EditBillMenuActivity, "Ha ocurrido un error. Intente más tarde", Toast.LENGTH_LONG).show()
                    }

                    //Tabla categorías vacia
                    response.code() == 404 -> {
                        Toast.makeText(this@EditBillMenuActivity, "No se encontraron categorías registrados", Toast.LENGTH_LONG).show()
                    }

                    //Error de conexión al servidor
                    response.code() == 500 -> {
                        Toast.makeText(this@EditBillMenuActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<ListBillMenuResPonseKT>>, t: Throwable) {
                Toast.makeText(this@EditBillMenuActivity, "FUN Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }

        })

    }


    fun tableDelete(view: View){
        val idRest =  intent.getLongExtra("restaurant", 0)
        val idCat: Long = view.id.toLong()
        Toast.makeText(this,view.id.toString(), Toast.LENGTH_LONG).show()
        val intent: Intent = Intent(
            this,
            AddBillMenuActivity::class.java
        ).apply {}
        intent.putExtra("category", idCat)
        intent.putExtra("restaurant", idRest)
        startActivity(intent)
    }
}