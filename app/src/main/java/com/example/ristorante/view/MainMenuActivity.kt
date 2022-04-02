package com.example.ristorante.view

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ristorante.R
import com.example.ristorante.container.Session
import com.example.ristorante.container.User
import com.example.ristorante.services.InterfaceSession
import com.example.ristorante.services.ServiceB
import com.example.ristorante.view.Bill.BillsActivity
import com.example.ristorante.view.Category.CategoryActivity
import com.example.ristorante.view.Checks.SessionInActivity
import com.example.ristorante.view.Checks.SessionOutActivity
import com.example.ristorante.view.DiningTable.DiningTableFormActivity
import com.example.ristorante.view.Menu.MenuFormActivity
import com.example.ristorante.view.Menu.MenuActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainMenuActivity : AppCompatActivity() {
    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getSerializableExtra("user") as? User

        // Determina que vista se muestra al usuario, la de administrador o la de empleado
        if((user!!.userGroup == 1L) or (user!!.userGroup == 2L))
            setContentView(R.layout.activity_main_admin)
        else
            setContentView(R.layout.activity_main_employee)
    }

    fun eventManageCategories(view: View){
        var intent: Intent = Intent(
            this@MainMenuActivity,
            CategoryActivity::class.java
        ).apply{}
        intent.putExtra("restaurant", user!!.restaurant)
        startActivity(intent)
    }

    fun eventManageMenu(view: View){
        var intent: Intent = Intent(
            this@MainMenuActivity,
            MenuActivity::class.java
        ).apply{}
        intent.putExtra("restaurant", user!!.restaurant)
        startActivity(intent)
    }

    fun eventManageDinningTables(view: View){
        var intent: Intent = Intent(
            this@MainMenuActivity,
            DiningTableFormActivity::class.java
        ).apply{}
        intent.putExtra("restaurant", user!!.restaurant)
        startActivity(intent)
    }

    fun eventManageBills(view: View){
        var intent: Intent = Intent(
            this@MainMenuActivity,
            BillsActivity::class.java
        ).apply{}
        intent.putExtra("user", user!!.id)
        intent.putExtra("restaurant", user!!.restaurant)
        startActivity(intent)
    }

    fun addIn(view: View){
        val service = ServiceB.buildService(InterfaceSession::class.java)
        val call = service.checkIn(user!!.id)

        call.enqueue(object : Callback<Session> {
            override fun onResponse(
                call: Call<Session>,
                response: Response<Session>
            ) {
                when {
                    response.code() == 200 -> {
                        val mAlertDialog = AlertDialog.Builder(this@MainMenuActivity)
                        mAlertDialog.setIcon(R.mipmap.ic_launcher_round)
                        mAlertDialog.setTitle("Registro de entrada")
                        mAlertDialog.setMessage("Se ha registrado correctamente tu entrada en el restaurante:" +
                                "\n\nFecha: " + response.body()!!.dateSession +
                                "\nEmpleado: " + user!!.name + " " + user!!.lastName +
                                "\nTipo de registro: Entrada")
                        mAlertDialog.setPositiveButton("Aceptar") { dialog, id -> }
                        mAlertDialog.show()
                    } // Error en el navegador
                    response.code() == 401 -> {
                        Toast.makeText(this@MainMenuActivity, "Ha ocurrido un error al guardar el registro. Intente más tarde.", Toast.LENGTH_LONG).show()
                    } // Error de conexión al servidor
                    response.code() == 500 -> {
                        Toast.makeText(this@MainMenuActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador.", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<Session>, t: Throwable) {
                Toast.makeText(this@MainMenuActivity, "Ha ocurrido un error.", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun addOut(view: View){
        val service = ServiceB.buildService(InterfaceSession::class.java)
        val call = service.checkOut(user!!.id)

        call.enqueue(object : Callback<Session> {
            override fun onResponse(
                call: Call<Session>,
                response: Response<Session>
            ) {
                when {
                    response.code() == 200 -> {
                        val mAlertDialog = AlertDialog.Builder(this@MainMenuActivity)
                        mAlertDialog.setIcon(R.mipmap.ic_launcher_round)
                        mAlertDialog.setTitle("Registro de salida")
                        mAlertDialog.setMessage("Se ha registrado correctamente tu salida en el restaurante:" +
                                "\n\nFecha: " + response.body()!!.dateSession +
                                "\nEmpleado: " + user!!.name + " " + user!!.lastName +
                                "\nTipo de registro: Salida")
                        mAlertDialog.setPositiveButton("Aceptar") { dialog, id -> }
                        mAlertDialog.show()
                    } // Error en el navegador
                    response.code() == 401 -> {
                        Toast.makeText(this@MainMenuActivity, "Ha ocurrido un error al guardar el registro. Intente más tarde.", Toast.LENGTH_LONG).show()
                    } // Error de conexión al servidor
                    response.code() == 500 -> {
                        Toast.makeText(this@MainMenuActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador.", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<Session>, t: Throwable) {
                Toast.makeText(this@MainMenuActivity, "Ha ocurrido un error.", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun logout(view: View){
        finish()
    }

    fun eventShowIn(view: View){
        var intent: Intent = Intent(
            this,
            SessionInActivity::class.java).apply{}
        intent.putExtra("restaurant", user!!.restaurant)
        startActivity(intent)
    }

    fun eventShowOut(view: View){
        var intent: Intent = Intent(
            this,
            SessionOutActivity::class.java).apply{}
        intent.putExtra("restaurant", user!!.restaurant)
        startActivity(intent)
    }

}