package com.example.testapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class calInfo(val name : String, val total_calories : String, val calories_per_hour : String, val duration_minutes : String)
class CalorieAdapter(private val dataList: List<calInfo>) : RecyclerView.Adapter<CalorieAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val total_calories: TextView = itemView.findViewById(R.id.totalCals)
        val calories_per_hour: TextView = itemView.findViewById(R.id.caloriesPerHour)
        val duration_minutes: TextView = itemView.findViewById(R.id.durationMinutes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calorie_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = dataList[position].name
        holder.total_calories.text = dataList[position].total_calories
        holder.calories_per_hour.text = dataList[position].calories_per_hour
        holder.duration_minutes.text = dataList[position].duration_minutes
    }

    override fun getItemCount() = dataList.size
}