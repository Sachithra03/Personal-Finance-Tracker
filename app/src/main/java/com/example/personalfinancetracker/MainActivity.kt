package com.example.personalfinancetracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personalfinancetracker.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var transactionAdapter: TransactionAdapter
    private val transactionList = mutableListOf<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadTransactions()
        setupRecyclerView()

        binding.btnAddTransaction.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadTransactions() {
        val sharedPref = getSharedPreferences("transactions", MODE_PRIVATE)
        val gson = Gson()
        val type = object : TypeToken<MutableList<Transaction>>() {}.type
        val json = sharedPref.getString("transaction_list", null)
        if (json != null) {
            transactionList.addAll(gson.fromJson(json, type))
        }
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter(transactionList)
        binding.rvTransactions.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = transactionAdapter
        }
    }
}
