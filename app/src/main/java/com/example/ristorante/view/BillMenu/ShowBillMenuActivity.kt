package com.example.ristorante.view.BillMenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ristorante.R

class ShowBillMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_row_billmenu)
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