package com.example.personalfinancetracker

data class Transaction(
    val id: Long,
    val title: String,
    val amount: Double,
    val category: String,
    val date: String
)
