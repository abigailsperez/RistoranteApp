package com.example.ristorantehttp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ristorantehttp.R
import com.example.ristorantehttp.controller.ControllerCategory

class MenuWaiterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_waiter)
    }

    fun btnAddBill(view: android.view.View) {
        val idUser=  intent.getLongExtra("idUser", 0)
        val idRest=  intent.getLongExtra("restaurant", 0)
        val intent: Intent = Intent(
            this,
            AddBillActivity::class.java
        ).apply {}
        intent.putExtra("idUser", idUser)
        intent.putExtra("restaurant", idRest)
        startActivity(intent)
    }

    fun endSession(view: View){
        finish()
    }
}