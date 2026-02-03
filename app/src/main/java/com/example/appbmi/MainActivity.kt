package com.example.appbmi

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.appbmi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val spinner = binding.spnLifestyle
        ArrayAdapter.createFromResource(
            this,
            R.array.lifestyleArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.btnCal.setOnClickListener {
            var gender = ""
            val rBtnMale = binding.rBtnMale
            val rBtnFemale = binding.rBtnFemale
            if (rBtnMale.isChecked) {
                gender = rBtnMale.text.toString()
            } else if (rBtnFemale.isChecked) {
                gender = rBtnFemale.text.toString()
            }

            val age = binding.edtAge.text.toString().toInt()
            val weight = binding.edtWeight.text.toString().toInt()
            val height = binding.edtHeight.text.toString().toInt()
            val lifestyle = binding.spnLifestyle.selectedItemPosition

            val intent = Intent(this, Result::class.java)
            intent.putExtra("gender", gender)
            intent.putExtra("age", age)
            intent.putExtra("weight", weight)
            intent.putExtra("height", height)
            intent.putExtra("lifestyle", lifestyle)

            startActivity(intent)
        }

    }
}