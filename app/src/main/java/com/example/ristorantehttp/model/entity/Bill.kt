package com.example.ristorantehttp.model.entity

import java.util.*

data class Bill(
    var id: Long,
    var date_bill: Date,
    var dinning_table: Long,
    var user: Long,
)
