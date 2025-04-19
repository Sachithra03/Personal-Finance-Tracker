package com.example.personalfinancetracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personalfinancetracker.databinding.ActivityAddTransactionBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding

    private val categories = listOf(
        "Select Category",
        "Food", "Social Life", "Pets", "Transport", "Culture",
        "Household", "Apparel", "Beauty", "Health", "Education", "Gift", "Other"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter

        binding.btnSave.setOnClickListener {
            saveTransaction()
        }
    }

    private fun saveTransaction() {
        val title = binding.etTitle.text.toString()
        val amountText = binding.etAmount.text.toString()
        val selectedPosition = binding.spinnerCategory.selectedItemPosition
        val selectedCategory = binding.spinnerCategory.selectedItem.toString()
        val date = binding.etDate.text.toString()

        if (title.isEmpty() || amountText.isEmpty() || selectedPosition == 0 || date.isEmpty()) {
            Toast.makeText(this, "Please fill all fields and select a valid category!", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null) {
            Toast.makeText(this, "Invalid amount!", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPref = getSharedPreferences("transactions", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val gson = Gson()
        val existingJson = sharedPref.getString("transaction_list", null)
        val type = object : TypeToken<MutableList<Transaction>>() {}.type
        val transactionList: MutableList<Transaction> = if (existingJson != null) {
            gson.fromJson(existingJson, type)
        } else {
            mutableListOf()
        }

        val newTransaction = Transaction(
            id = System.currentTimeMillis(),
            title = title,
            amount = amount,
            category = selectedCategory,
            date = date
        )
        transactionList.add(newTransaction)

        val updatedJson = gson.toJson(transactionList)
        editor.putString("transaction_list", updatedJson)
        editor.apply()

        Toast.makeText(this, "Transaction Saved!", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
