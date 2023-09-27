package com.example.testapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val photoGen = findViewById<Button>(R.id.photoGen)
        val caloriesButton = findViewById<Button>(R.id.caloriebutton)
        val gameButton = findViewById<Button>(R.id.gameButton)
        val view = findViewById<ConstraintLayout>(R.id.constraintLayout)
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.pink))
        photoGen.setOnClickListener {
            val intent = Intent(this, PhotoGeneratorActivity::class.java)
            startActivity(intent)
        }

        caloriesButton.setOnClickListener {
            val intent = Intent(this, CalculateCaloriesActivity::class.java)
            startActivity(intent) }

        gameButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent) }
    }
}