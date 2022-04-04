package com.example.ristorante.view.BillMenu

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.ristorante.R
import com.example.ristorante.entity.BillMenu
import com.example.ristorante.entity.Menu
import com.example.ristorante.services.*
import kotlinx.android.synthetic.main.activity_billmenu_form.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillMenuFormActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var menu: Spinner
    private lateinit var quantity: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billmenu_form)

        menu = findViewById(R.id.menuSpinner)
        quantity = findViewById(R.id.quantity)
        title = findViewById(R.id.title_bm)

        this.loadData()
        if(intent.getLongExtra("bill_menu", 0) != 0L){
            title.setText("Editar platillo de esta orden")
        } else {
            title.setText("Agregar platillo a esta orden")
        }
    }

    fun loadData(){
        val service = ServiceB.buildService(InterfaceMenu::class.java)
        val call = service.getAll(intent.getLongExtra("restaurant", 0))

        call.enqueue(object : Callback<List<Menu>> {
            override fun onResponse(
                call: Call<List<Menu>>,
                response: Response<List<Menu>>
            ) {
                when {
                    response.code() == 200 -> {
                        val adapter: ArrayAdapter<Menu> = ArrayAdapter<Menu>(
                            applicationContext,
                            android.R.layout.simple_spinner_dropdown_item,
                            response.body()!!
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        menu.adapter = adapter

                        val bm = intent.getLongExtra("bill_menu", 0L)
                        if(bm != 0L){
                            fillMenuData(bm, adapter)
                        }

                    }
                    response.code() == 401 -> { //Error en el navegador
                        Toast.makeText(this@BillMenuFormActivity, "Ha ocurrido un error. Intente más tarde.", Toast.LENGTH_LONG).show()
                    }
                    response.code() == 404 -> { // Tabla categorías vacia
                        Toast.makeText(this@BillMenuFormActivity, "No se encontraron categorías registradas.", Toast.LENGTH_LONG).show()
                    }
                    response.code() == 500 -> { // Error de conexión al servidor
                        Toast.makeText(this@BillMenuFormActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<Menu>>, t: Throwable) {
                Toast.makeText(this@BillMenuFormActivity, "Ha ocurrido un error.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun fillMenuData(billmenuID: Long, adapter: ArrayAdapter<Menu>){
        val service = ServiceB.buildService(InterfaceBillMenu::class.java)
        val call = service.getById(billmenuID)

        call.enqueue(object : Callback<BillMenu> {
            override fun onResponse(call2: Call<BillMenu>, response: Response<BillMenu>) {
                when {
                    response.code() == 200 -> {
                        var bm: BillMenu = response.body()!!
                        quantity.setText(bm.quantity.toString())

                        for (i in 0 until adapter.count) {
                            val m = adapter.getItem(i) as Menu
                            if(m.id == bm.menu){
                                menuSpinner.setSelection(i)
                                break
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<BillMenu>, t: Throwable) {
                Toast.makeText(this@BillMenuFormActivity,
                    "No se ha podido comunicar con el servidor, verifique su conexión o contacte a algun Administrador",
                    Toast.LENGTH_LONG).show()
            }
        })
    }

    fun save(view: View) {
        val bill = intent.getLongExtra("bill", 0)
        val obj = BillMenu()
        obj.id = intent.getLongExtra("bill_menu", 0)
        obj.bill = bill
        var menu: Menu = menu.selectedItem as Menu
        obj.menu = menu.id
        obj.quantity = quantity.text.toString().toLong()

        val service = ServiceB.buildService(InterfaceBillMenu::class.java)
        val call = service.save(obj)

        call.enqueue(object : Callback<BillMenu> {
            override fun onResponse(
                call: Call<BillMenu>,
                response: Response<BillMenu>
            ) {
                when {
                    response.code() == 200 -> {
                        Toast.makeText(this@BillMenuFormActivity, "Guardado con éxito.", Toast.LENGTH_LONG).show()
                    }

                    response.code() == 401 -> {
                        Toast.makeText(
                            this@BillMenuFormActivity,
                            "Ha ocurrido un error al guardar el registro. Intente más tarde",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    response.code() == 500 -> {
                        Toast.makeText(
                            this@BillMenuFormActivity,
                            "Ha occurido un error en el servidor, por favor contacte al Administrador",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            }

            override fun onFailure(call: Call<BillMenu>, t: Throwable) {
                Toast.makeText(this@BillMenuFormActivity, "FUN Ha ocurrido un error", Toast.LENGTH_LONG)
                    .show()
            }

        })
        setResult(Activity.RESULT_OK)
        this.finish()
    }
}

