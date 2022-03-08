package com.example.ristorantehttp.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.ristorantehttp.R

class MenuAdminActivity : AppCompatActivity() {
    lateinit var cerrarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_admin)

        cerrarSesion= findViewById(R.id.adminCS)
        //val rest =  intent.getLongExtra("restaurant", 0)
    }

    fun eventAddCategory(view: View){
        val rest =  intent.getLongExtra("restaurant", 0)
        var intento: Intent = Intent(
            this@MenuAdminActivity,
            AddCategoryActivity::class.java
        ).apply{}
        intento.putExtra("restaurant", rest)
        startActivity(intento)
        println("ID del restaurante-> " + rest)
    }

    fun eventAddMeal(view: View){
        val rest =  intent.getLongExtra("restaurant", 0)
        var intent2: Intent = Intent(
            this@MenuAdminActivity,
            AddMealActivity::class.java).apply{}
        intent2.putExtra("restaurant", rest)
        startActivity(intent2)
    }

    fun eventShowIn(view: View){
        val rest =  intent.getLongExtra("restaurant", 0)
        var intent3: Intent = Intent(
            this,
            ShowSessionInActivity::class.java).apply{}
        intent3.putExtra("restaurant", rest)
        startActivity(intent3)
    }

    fun eventShowOut(view: View){
        val rest =  intent.getLongExtra("restaurant", 0)
        var showOut: Intent = Intent(
            this,
            ShowSessionOutActivity::class.java).apply{}
        showOut.putExtra("restaurant", rest)
        startActivity(showOut)
    }

    fun endSession(view: View){
        finish()
    }

    fun changeValue(boolean: Boolean){

    }
}