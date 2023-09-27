package com.example.testapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CalculateCaloriesActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculate_calories)
        val view = findViewById<ConstraintLayout>(R.id.calConstraintLayout)
        val viewModel = MainViewModel()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val generateButton = findViewById<Button>(R.id.generateCalButton)
        val activityText = findViewById<EditText>(R.id.calorieInput)
        var activity = ""

        val data = mutableListOf<calInfo>()

        view.setBackgroundColor(ContextCompat.getColor(this, R.color.pink))


        generateButton.setOnClickListener {
            //dismiss the keyboard
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            //request activity from view model
            viewModel.getCalorieInfoRequest(activityText.text.toString())
            activity = activityText.text.toString()
            activityText.setText("")
        }

        viewModel.calInfoResponse.observe(this) { response ->
            if (response != null) {

                data.clear()

                for(info in response){
                   data.add(0, calInfo("Name: ${info.name}", "Total Calories: ${info.total_calories}", "Calories Per Hour: ${info.calories_per_hour}" , "Duration Minutes: ${info.duration_minutes}"))
               }
            } else {
                Log.e("MainActivity", "API Response is null")
            }

            if(data.isEmpty()){
                Toast.makeText(this, "Oops! There are no results for $activity", Toast.LENGTH_SHORT).show()
            }
            //load data into calorie adapter
            recyclerView.adapter = CalorieAdapter(data)
        }
    }
}