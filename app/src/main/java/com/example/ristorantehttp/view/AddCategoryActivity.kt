package com.example.ristorantehttp.view
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.ristorantehttp.R
import com.example.ristorantehttp.container.ListCategoriaResPonseKT
import com.example.ristorantehttp.container.ResponseCategory
import com.example.ristorantehttp.container.SaveCategory
import com.example.ristorantehttp.controller.ControllerCategory
import com.example.ristorantehttp.services.InterfaceListCategory
import com.example.ristorantehttp.services.InterfaceSaveCategory
import com.example.ristorantehttp.services.ServiceB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.TableLayout
import kotlinx.android.synthetic.main.table_row_category.*
import android.view.ViewGroup


class AddCategoryActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private var table_category:TableLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        name= findViewById(R.id.name)
        table_category=findViewById(R.id.table_category)
    }

    //Función para guardar categorias
    fun saveCategoryFun(view: View){

        if(name.text.toString().isEmpty()){
            Toast.makeText(this,"Campo nombre vacío.", Toast.LENGTH_LONG).show()
        }else {
            println("FUNCIÓN GUARDAR CATEGORÍA")

            val rest = intent.getLongExtra("restaurant", 0) //Obtiene el id del restaurante al que pertenece el usuario
            val saveCategoryObj = SaveCategory() //Se crea el objeto category
            saveCategoryObj.name = name.text.toString()  //TextView del name(categoria)
            saveCategoryObj.restaurant = rest //Número de restaurante
            println("ID del restaurante-> " + rest) //Impreme el id en consola

            //Consumo de web services
            val service = ServiceB.buildService(InterfaceSaveCategory::class.java)
            val call = service.saveCategory(saveCategoryObj)

            call.enqueue(object : Callback<ResponseCategory> {
                override fun onResponse(
                    call: Call<ResponseCategory>,
                    response: Response<ResponseCategory>
                ) {
                    when {
                        //Guardado con éxito
                        response.code() == 200 -> {
                            Toast.makeText(
                                this@AddCategoryActivity,
                                "Guardado con éxito.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        //Error en el navegador
                        response.code() == 401 -> {
                            Toast.makeText(
                                this@AddCategoryActivity,
                                "Ha ocurrido un error al guardar el registro. Intente más tarde.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        //Error de conexión al servidor
                        response.code() == 500 -> {
                            Toast.makeText(
                                this@AddCategoryActivity,
                                "Ha occurido un error en el servidor, por favor contacte al Administrador.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseCategory>, t: Throwable) {
                    Toast.makeText(
                        this@AddCategoryActivity,
                        "FUN Ha ocurrido un error.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
            name.setText("") //Se limpia el TextView
        }
    }

    //Función para obtener lista de categorias
    private lateinit var listData: ArrayList<String>

    fun getListCategoryFun(view: View){

        table_category?.removeAllViews() //Se limpia la tabla de categorías
        var otroContador = 0
        val idRest  =  intent.getLongExtra("restaurant", 0) //Número de restaurante

        //Consumo de web services
        val service = ServiceB.buildService(InterfaceListCategory::class.java)
        val call = service.getReceive(idRest)

        call.enqueue(object : Callback<List<ListCategoriaResPonseKT>> {
            override fun onResponse(
                call: Call<List<ListCategoriaResPonseKT>>,
                response: Response<List<ListCategoriaResPonseKT>>
            ) {
                when {
                    response.code() == 200 -> {

                        listData = ArrayList()

                        response.body()!!.forEach {
                            //Se busca en la BD
                            var data =
                                "id " + it.id +
                                "name" + it.name
                            listData.add(data)

                            //Se imprime en consola
                            println("\n\n")
                            println(
                                "\nid: " + it.id +
                                "\nname: " + it.name)

                            //Se llena la tabla
                            val register =
                                LayoutInflater.from(this@AddCategoryActivity).inflate(R.layout.table_row_category, null, false)
                            val colId: TextView = register.findViewById<View>(R.id.colId) as TextView
                            val colName: TextView = register.findViewById<View>(R.id.colName) as TextView
                            val btnEdit: View = register.findViewById<View>(R.id.btn_editCategory) as Button
                            colId.text = ""+ it.id
                            colName.text = ""+it.name
                            btnEdit.id = (""+ it.id).toInt()
                            table_category?.addView(register)

                            otroContador ++
                        }
                    }
                    //Error en el navegador
                    response.code() == 401 -> {
                         Toast.makeText(this@AddCategoryActivity, "Ha ocurrido un error. Intente más tarde.", Toast.LENGTH_LONG).show()
                    }
                    //Tabla categorías vacia
                     response.code() == 404 -> {
                         Toast.makeText(this@AddCategoryActivity, "No se encontraron categorías registradas.", Toast.LENGTH_LONG).show()
                     }
                     //Error de conexión al servidor
                     response.code() == 500 -> {
                         Toast.makeText(this@AddCategoryActivity, "Ha occurido un error en el servidor, por favor contacte al Administrador.", Toast.LENGTH_LONG).show()
                     }
                }
            }
             override fun onFailure(call: Call<List<ListCategoriaResPonseKT>>, t: Throwable) {
                 Toast.makeText(this@AddCategoryActivity, "FUN Ha ocurrido un error.", Toast.LENGTH_LONG).show()
             }
        })
    }

    //Función que devuelve la vista de editar categoría
    fun tableEdit(view: View){
        val idRest =  intent.getLongExtra("restaurant", 0)
        val idCat: Long = view.id.toLong() //Se guarda el id de la categoría en una variable
        Toast.makeText(this,view.id.toString(), Toast.LENGTH_LONG).show() //Muestra el id de la categoría
        val intent: Intent = Intent(
            this,
            EditCategoryActivity::class.java
        ).apply {}
        intent.putExtra("category", idCat) //Se envia el id de la categoría a la siguiente actividad
        intent.putExtra("restaurant", idRest)
        startActivity(intent)
    }
}