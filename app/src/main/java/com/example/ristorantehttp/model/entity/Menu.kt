package com.example.ristorantehttp.model.entity

data class Menu(
    var id: Long,
    var name: String,
    var category: String,
    var available: Int,
    var price: Long
)
