package com.example.ristorante.view.BillMenu

import android.app.Activity
import android.app.AlertDialog
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
import com.example.ristorante.containers.BillMenuContainer
import com.example.ristorante.entity.BillMenu
import com.example.ristorante.services.InterfaceBillMenu
import com.example.ristorante.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillMenuActivity : AppCompatActivity() {
    private var table_billmenu: TableLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billmenu)

        table_billmenu = findViewById(R.id.table_billmenu)
        this.loadData()
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            this.loadData()
        }
    }

    fun add(view: View){
        val restaurant = intent.getLongExtra("restaurant", 0L)
        val bill = intent.getLongExtra("bill", 0L)
        var intent: Intent = Intent(
            this@BillMenuActivity,
            BillMenuFormActivity::class.java
        ).apply{}
        intent.putExtra("bill_menu", 0L)
        intent.putExtra("restaurant", restaurant)
        intent.putExtra("bill", bill)
        getResult.launch(intent)
    }

    fun update(view: View){
        val restaurant = intent.getLongExtra("restaurant", 0L)
        val bill = intent.getLongExtra("bill", 0L)
        var intent: Intent = Intent(
            this@BillMenuActivity,
            BillMenuFormActivity::class.java
        ).apply{}
        intent.putExtra("bill_menu", view.id.toLong())
        intent.putExtra("restaurant", restaurant)
        intent.putExtra("bill", bill)
        getResult.launch(intent)
    }

    fun delete(view: View){
        val mAlertDialog = AlertDialog.Builder(this@BillMenuActivity)
        mAlertDialog.setIcon(R.mipmap.ic_launcher_round)
        mAlertDialog.setTitle("Pedido de la orden.")
        mAlertDialog.setMessage("¿Está seguro que quiere elimiar el siguiente pedido de la orden?")
        mAlertDialog.setPositiveButton("Aceptar") { dialog, id ->
            val service = ServiceB.buildService(InterfaceBillMenu::class.java)
            val call = service.deleteById(view.id.toLong())

            call.enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    when {
                        response.code() == 200 -> {
                            loadData()
                            Toast.makeText(this@BillMenuActivity, "Se ha eliminado el pedido correctamente.", Toast.LENGTH_LONG).show()
                        }
                        response.code() == 401 -> {
                            Toast.makeText(this@BillMenuActivity, "Ha ocurrido un error. Intente más tarde", Toast.LENGTH_LONG).show()
                        }
                        response.code() == 404 -> {
                            Toast.makeText(this@BillMenuActivity, "No se encontraron cuentas registradas", Toast.LENGTH_LONG).show()
                        }
                        response.code() == 500 -> {
                            Toast.makeText(this@BillMenuActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    loadData()
                    Toast.makeText(this@BillMenuActivity, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
                }
            })
        }
        mAlertDialog.setNegativeButton("Cancelar"){ dialog, id -> }
        mAlertDialog.show()
    }

    fun loadData() {
        table_billmenu?.removeViews(1, Math.max(0, table_billmenu!!.getChildCount() - 1))

        val service = ServiceB.buildService(InterfaceBillMenu::class.java)
        val call = service.getAll(intent.getLongExtra("bill", 0L))

        call.enqueue(object : Callback<List<BillMenuContainer>> {
            override fun onResponse(
                call: Call<List<BillMenuContainer>>,
                response: Response<List<BillMenuContainer>>
            ) {
                when {
                    response.code() == 200 -> {
                        response.body()!!.forEach {
                            val register = LayoutInflater.from(this@BillMenuActivity)
                                .inflate(R.layout.table_row_billmenu, null, false)
                            val colMenu: TextView = register.findViewById<View>(R.id.colMenu) as TextView
                            val colQuantity: TextView = register.findViewById<View>(R.id.colQuantity) as TextView
                            val btnEdit: View = register.findViewById<View>(R.id.btn_editM) as Button
                            val btnDelete: View = register.findViewById<View>(R.id.btn_deleteMenu) as Button
                            colMenu.text = ""+it.name
                            colQuantity.text = ""+it.quantity
                            btnEdit.id = (""+ it.id).toInt()
                            btnDelete.id = (""+ it.id).toInt()
                            table_billmenu?.addView(register)
                        }
                    }
                    response.code() == 401 -> {
                        Toast.makeText(this@BillMenuActivity, "Ha ocurrido un error. Intente más tarde", Toast.LENGTH_LONG).show()
                    }
                    response.code() == 404 -> {
                        Toast.makeText(this@BillMenuActivity, "No se encontraron cuentas registradas", Toast.LENGTH_LONG).show()
                    }
                    response.code() == 500 -> {
                        Toast.makeText(this@BillMenuActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<BillMenuContainer>>, t: Throwable) {
                Toast.makeText(this@BillMenuActivity, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }
        })
    }
}