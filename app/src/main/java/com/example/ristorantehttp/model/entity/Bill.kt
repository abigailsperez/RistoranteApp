package com.example.ristorantehttp.model.entity

import java.text.DateFormat
import java.time.LocalDateTime
import java.util.*

data class Bill(
    var id: Long,
    var completed: Int,
    var dateBill: LocalDateTime,
    var date_Bill: String,
    var diningTable: Long,
    var user: Long,
)
