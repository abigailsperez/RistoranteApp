package com.example.ristorantehttp.model.entity

import java.util.*

data class Session(
    var id: Long,
    var dateSession: Date,
    var inOut: Int,
    var user: Long,
)
