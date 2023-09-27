package com.example.testapplication

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

class PhotoGeneratorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_generator)

        val inputCategory  = findViewById<EditText>(R.id.categoryInput)
        val generateButton = findViewById<Button>(R.id.generateButton)
        val image = findViewById<ImageView>(R.id.generatedImage)
        val viewModel = MainViewModel()
        val view = findViewById<ConstraintLayout>(R.id.photoGenView)

        view.setBackgroundColor(ContextCompat.getColor(this, R.color.pink))
        image.setImageResource(R.drawable.imageborder)

        generateButton.setOnClickListener {
            val category = inputCategory.text
            viewModel.randomImageRequest(category.toString())
            inputCategory.setText("")
        }

        viewModel.randImageResponse.observe(this) { response ->
            if (response != null) {
                //parse image using glide
                Glide.with(this)
                    .load(response)
                    .into(image)

            } else {
                Log.e("MainActivity", "API Response is null")
            }
        }
    }
}