package com.example.table_calc  // Измените на имя вашего пакета

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.table_calc.R

class MainActivity : AppCompatActivity() {

    private lateinit var kursDollarEdit: EditText
    private lateinit var kursEuroEdit: EditText
    private lateinit var rubInput: EditText
    private lateinit var usdOutput: TextView
    private lateinit var calculateButton: Button
    private lateinit var clearButton: Button
    private lateinit var eurOutput: TextView
    private val PREFS_NAME = "CurrencyPrefs"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()

        // Инициализация ячеек
        eurOutput = findViewById(R.id.eurOutput)
        kursDollarEdit = findViewById(R.id.kursDollarEdit)

        usdOutput = findViewById(R.id.usdOutput)
        calculateButton = findViewById(R.id.calculateButton)
        clearButton = findViewById(R.id.clearButton)

        calculateButton.setOnClickListener { calculate() }
        clearButton.setOnClickListener { clearInputs() }
        kursEuroEdit = findViewById(R.id.kursEuroEdit)
        rubInput = findViewById(R.id.rubInput)
    }


    private fun saveData() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("kursDollar", kursDollarEdit.text.toString())
        editor.putString("kursEuro", kursEuroEdit.text.toString())
        editor.putString("rubInput", rubInput.text.toString())
        editor.apply()
    }

    private fun loadData() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        kursDollarEdit.setText(prefs.getString("kursDollar", ""))
        kursEuroEdit.setText(prefs.getString("kursEuro", ""))
        rubInput.setText(prefs.getString("rubInput", ""))
    }

    private fun calculate() {
        val kursDollar = kursDollarEdit.text.toString().toDoubleOrNull() ?: 0.0
        val rub = rubInput.text.toString().toDoubleOrNull() ?: 0.0

        // Проверка на отрицательные числа
        if (kursDollar < 0 || rub < 0) {
            usdOutput.text = "Ошибка: отрицательное число"
            return
        }

        if (kursDollar == 0.0) {
            usdOutput.text = "Недопустим курс 0"
            return
        }

        // Вычисление USD
        val usd = rub / kursDollar
        val formattedUsd = String.format("%.2f", usd)
        usdOutput.text = formattedUsd

        // Вычисление EURO
        val kursEuro = kursEuroEdit.text.toString().toDoubleOrNull() ?: 0.0

        if (kursEuro < 0) {
            eurOutput.text = "Ошибка"
            return
        }

        if (kursEuro == 0.0) {
            eurOutput.text = "Недопустим курс 0"
            return
        }

        val eur = rub / kursEuro
        val formattedEur = String.format("%.2f", eur)
        eurOutput.text = formattedEur

        saveData()
    }

    private fun clearInputs() {
        kursDollarEdit.text.clear()
        kursEuroEdit.text.clear()
        rubInput.text.clear()
        usdOutput.text = ""
        eurOutput.text = ""
        saveData()
    }
}
