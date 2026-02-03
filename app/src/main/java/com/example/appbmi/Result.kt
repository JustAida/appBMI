package com.example.appbmi

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appbmi.databinding.ActivityResultBinding
import kotlin.math.roundToInt

class Result : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val gender = intent.getStringExtra("gender").toString()
        val age = intent.getIntExtra("age", 0)
        val weight = intent.getIntExtra("weight", 0).toDouble()
        val height = intent.getIntExtra("height", 0).toDouble()
        val lifestyle = intent.getIntExtra("lifestyle", -1)

        val bmi = calculateBMI(weight, height)
        val bmiStatus = getBMIStatus(bmi)
        binding.txtBMIResult.text = bmiStatus
        val bmr = calculateBMR(weight, height, age, gender)
        val lifestyleLevel = lifestyleLevel(lifestyle)
        val tdee = calculateTDEE(bmr, lifestyleLevel)
        calculateMacros(tdee, weight, binding)

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun calculateBMI(weightKg: Double, heightCm: Double): Double {
        val heightM = heightCm / 100.0
        return weightKg / (heightM * heightM)
    }

    fun getBMIStatus(bmi: Double): String {
        return when {
            bmi < 18.5 -> "ผอมไป (เสี่ยงขาดสารอาหาร)"
            bmi in 18.5..22.9 -> "สมส่วน (ดีมาก สุขภาพดี)"
            bmi in 23.0..24.9 -> "ท้วม (เริ่มอ้วน มีความเสี่ยงนิดๆ)"
            bmi in 25.0..29.9 -> "อ้วน (ต้องเริ่มคุมอาหาร ออกกำลังกาย)"
            else -> "อ้วนมาก (เสี่ยงโรคสูง ต้องระวังด่วน)"
        }
    }

    fun calculateBMR(weightKg: Double, heightCm: Double, age: Int, gender: String): Double {
        val baseCalculation = (10 * weightKg) + (6.25 * heightCm) - (5 * age)
        return when (gender) {
            "ชาย" -> baseCalculation + 5
            "หญิง" -> baseCalculation - 161
            else -> 0.0
        }
    }

    fun lifestyleLevel(index: Int): Double {
        return when {
            index == 0 -> 1.2
            index == 1 -> 1.375
            index == 2 -> 1.55
            index == 3 -> 1.725
            else -> 0.0
        }
    }
    fun calculateTDEE(bmr: Double, lifestyleLevel: Double): Double {
        return bmr * lifestyleLevel
    }

    fun calculateMacros(tdee: Double,weightKg: Double, binding: ActivityResultBinding) {
        val protein = (tdee * 0.20) / 4
        val fat = (tdee * 0.30) / 9
        val carbs = (tdee * 0.50) / 4
        val water = weightKg * 33
        binding.txtProteinVal.text = protein.roundToInt().toString()
        binding.txtFatValue.text = fat.roundToInt().toString()
        binding.txtCarbVal.text = carbs.roundToInt().toString()
        binding.txtWaterVal.text = water.roundToInt().toString()
        binding.txtCalVal.text = tdee.toInt().toString()
    }

}