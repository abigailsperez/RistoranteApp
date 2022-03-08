package com.example.ristorantehttp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ristorantehttp.R

class ShowBillMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_row_bill_menu)
    }

    fun tableDeleteMenu(view: View){
        Toast.makeText(this,view.id.toString(), Toast.LENGTH_LONG).show()
        val intent: Intent = Intent(
            this,
            EditBillMenuActivity::class.java
        ).apply {}
        startActivity(intent)
    }
}