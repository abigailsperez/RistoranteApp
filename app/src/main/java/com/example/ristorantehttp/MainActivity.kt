package com.example.ristorantehttp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ristorantehttp.container.LoginUser
import com.example.ristorantehttp.container.ResponseLogin
import com.example.ristorantehttp.services.InterfaceUser
import com.example.ristorantehttp.services.ServiceB
import com.example.ristorantehttp.view.MenuAdminActivity
import com.example.ristorantehttp.view.MenuCashierActivity
import com.example.ristorantehttp.view.MenuWaiterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
    }

    //Función para iniciar sesión
    fun accessLogin(view: View){

        val userLoginObj = LoginUser()
        userLoginObj.email = email.text.toString() //TextView del email
        userLoginObj.password = password.text.toString() //TextView del password

        //Consumo de web services
        val service = ServiceB.buildService(InterfaceUser::class.java)
        val call = service.login(userLoginObj)

        call.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                when {
                    //Inicio de sesión éxitoso
                    response.code() == 200 -> {

                        val idUser: Long = response.body()!!.id //Obtiene el id del usuario
                        val usergroup: Long = response.body()!!.userGroup //Obtiene el id del grupo que pertenece el usuario
                        val rest: Long = response.body()!!.restaurant //Obtiene el id del restaurante al que pertenece el usuario

                        //Valida el grupo al que pertenece y arroja la ventana hacia el menú
                        if (usergroup == 2.toLong()) {
                            //Vamos a agregar la nueva actividad hacia el menu administrador
                            val intent: Intent = Intent(
                                this@MainActivity,
                                MenuAdminActivity::class.java
                            ).apply {}
                            intent.putExtra("restaurant", rest) //Se envia el id del restaurante a la siguiente actividad
                            startActivity(intent)
                        } else if (usergroup == 5.toLong()) {
                            //Vamos a agregar la nueva actividad hacia el menu cajero
                            val intent: Intent = Intent(
                                this@MainActivity,
                                MenuCashierActivity::class.java
                            ).apply {}
                            intent.putExtra("restaurant", rest)
                            startActivity(intent)
                        } else if (usergroup == 3.toLong()) {
                            //Vamos a agregar la nueva actividad hacia el menu mesero
                            val intent: Intent = Intent(
                                this@MainActivity,
                                MenuWaiterActivity::class.java
                            ).apply {}
                            intent.putExtra("idUser", idUser) //Se envia el id del usuario a la siguiente actividad
                            intent.putExtra("restaurant", rest)
                            startActivity(intent)
                        }
                        println("ID del restaurante-> " + response.body()!!.restaurant)
                    }
                    //Error usuario incorrecto
                    response.code() == 404 -> {
                        //Se muestra el mensaje en consola
                        println("Usuario incorrecto.")
                        //Se muestra el mensaje en la vista
                        Toast.makeText(this@MainActivity, "Usuario incorrecto.", Toast.LENGTH_LONG).show()
                    }
                    //Error contraseña incorrecta
                    response.code() == 401 -> {
                        println("Contraseña incorrecta.")
                        //Se muestra el mensaje en la vista
                        Toast.makeText(this@MainActivity, "Contraseña incorrecta.", Toast.LENGTH_LONG).show()
                    }
                    //No se encontró en la BD
                    response.code() == 500 -> {
                        println("Credenciales inválidas.")
                        //Se muestra el mensaje en la vista
                        Toast.makeText(this@MainActivity, "Credenciales inválidas.", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Toast.makeText(this@MainActivity, "No se ha podido comunicar con el servidor, verifique su conexión o contacte a algun Administrador", Toast.LENGTH_LONG).show()
            }

        })
    }
}