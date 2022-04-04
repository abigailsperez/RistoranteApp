package com.example.ristorante

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ristorante.entity.User
import com.example.ristorante.services.InterfaceUser
import com.example.ristorante.services.ServiceB
import com.example.ristorante.view.MainMenuActivity
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

    // Función para iniciar sesión
    fun accessLogin(view: View){

        val userLoginObj = User()
        userLoginObj.email = email.text.toString() //TextView del email
        userLoginObj.password = password.text.toString() //TextView del password

        val service = ServiceB.buildService(InterfaceUser::class.java)
        val call = service.login(userLoginObj)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                when {
                    response.code() == 200 -> { // Inicio de sesión éxitoso
                        // Se envía al usuario al menú
                        val intent: Intent = Intent(
                            this@MainActivity,
                            MainMenuActivity::class.java
                        ).apply {}
                        intent.putExtra("user", response.body()) // Se anexan los datos del usuario
                        startActivity(intent)
                    }
                    response.code() == 404 -> { // Error usuario incorrecto
                        Toast.makeText(this@MainActivity, "Usuario incorrecto.", Toast.LENGTH_LONG).show()
                    }
                    response.code() == 401 -> { // Error contraseña incorrecta
                        Toast.makeText(this@MainActivity, "Contraseña incorrecta.", Toast.LENGTH_LONG).show()
                    }
                    response.code() == 500 -> { // Credenciales incorrectas o no existentes en la base de datos
                        Toast.makeText(this@MainActivity, "Credenciales inválidas.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "No se ha podido comunicar con el servidor, verifique su conexión o contacte a algun Administrador", Toast.LENGTH_LONG).show()
            }
        })
    }
}