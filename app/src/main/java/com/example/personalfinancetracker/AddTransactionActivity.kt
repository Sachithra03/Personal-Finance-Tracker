package com.example.personalfinancetracker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personalfinancetracker.databinding.ActivityAddTransactionBinding

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val amountText = binding.etAmount.text.toString()
            val category = binding.etCategory.text.toString()
            val date = binding.etDate.text.toString()

            if (title.isEmpty() || amountText.isEmpty() || category.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null) {
                Toast.makeText(this, "Invalid amount!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Here you can later send back data to MainActivity if needed
            Toast.makeText(this, "Transaction Added!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}