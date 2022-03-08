package com.example.ristorantehttp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.ristorantehttp.R

class MenuCashierActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_cashier)
    }

    fun showListBill(view: View){
        val rest =  intent.getLongExtra("restaurant", 0)
        var intent: Intent = Intent(
            this@MenuCashierActivity,
            ShowBillsActivity::class.java
        ).apply{}
        intent.putExtra("restaurant", rest)
        startActivity(intent)
    }

    fun endSession(view: View){
        finish()
    }
}