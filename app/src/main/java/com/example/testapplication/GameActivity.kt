package com.example.testapplication

import android.content.Context
import android.graphics.Paint.Align
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat

class GameActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_game)
            val DEBUG_MODE = false
            val NUM_BOMBS = 10
            val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
            val numRows = 20
            val numCols = 10

            var boxes = Array(20) { Array(10) { Box() }}
            boxes.flatMap { it.asList() }
                .shuffled()
                .take(NUM_BOMBS)
                .forEach { it.isBomb = true }


            for(i in 0 until numRows){
                for(j in 0 until numCols){
                    val square = TextView(this)
                    square.layoutParams = GridLayout.LayoutParams(
                        GridLayout.spec(i),
                        GridLayout.spec(j)
                    ).apply {
                        width = 100
                        height = 100
                        setMargins(5,5,5,5)
                    }
                    square.setBackgroundColor(ContextCompat.getColor(this, R.color.pink))

                    if(boxes[i][j].isBomb && DEBUG_MODE){
                        square.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                    }
                    square.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    gridLayout.addView(square)

                    square.setOnClickListener {
                        if(!boxes[i][j].isFlagged){
                            if(boxes[i][j].isBomb){
                                Toast.makeText(this, "YOU HIT A BOMB", Toast.LENGTH_SHORT).show()
                            }else{
                                val bombCount = checkSurroundingBombs(i, j, boxes, numCols, numRows)
                                square.text = bombCount.toString()
                                square.setBackgroundColor(ContextCompat.getColor(this, R.color.white))

                                if(bombCount == 0){

                                    val startRowValue = maxOf(i-1, 0)
                                    val startColsValue = maxOf(j-1, 0)
                                    val endRowsValue = minOf(i+1, numRows)
                                    val endColsValue = minOf(j+1, numCols)


                                    for(row in startRowValue  until endRowsValue+1) {
                                        for (cols in startColsValue  until endColsValue+1) {
                                            val index = getIndex(row,cols,numCols)
                                            val newSquare = gridLayout.getChildAt(index) as TextView
                                            newSquare.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                                            val numBombs = checkSurroundingBombs(row, cols, boxes, numCols, numRows)
                                            newSquare.text = numBombs.toString()
                                        }
                                    }
                                }
                            }
                        }

                    }

                    square.setOnLongClickListener(){
                        if(boxes[i][j].isFlagged){
                            square.text = ""
                        }else{
                            square.text = "F"
                        }
                        boxes[i][j].isFlagged = !boxes[i][j].isFlagged
                        true
                    }
                }
            }
        }
}

fun gameOver(){

}



fun getIndex(i : Int, j : Int, numCols : Int) : Int{
    return i*numCols+j
}

fun checkSurroundingBombs(i : Int, j : Int, boxes : Array<Array<Box>>, numCols : Int, numRows : Int ) : Int{
    var bombCount = 0
    val startRowValue = maxOf(i-1, 0)
    val startColsValue = maxOf(j-1, 0)
    val endRowsValue = minOf(i+1, numRows)
    val endColsValue = minOf(j+1, numCols)


    for(row in startRowValue  until endRowsValue+1) {
        for (cols in startColsValue  until endColsValue+1) {
            if(boxes[row][cols].isBomb){
                bombCount++
            }
        }
    }
    return bombCount
}

data class Box (var isBomb : Boolean = false, var isFlagged : Boolean = false)