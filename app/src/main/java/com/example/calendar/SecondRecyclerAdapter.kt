package com.example.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.DB.TaskEntity

class SecondRecyclerAdapter(private val subjects: List<TaskEntity>
): RecyclerView.Adapter<SecondRecyclerAdapter.ViewHolder>() {


    // хранение и визуализация элементов списка
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val subjectView: TextView = itemView.findViewById(R.id.textViewSubject)
        val timeView: TextView = itemView.findViewById(R.id.textViewTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.schedule_maket, parent, false)
        return ViewHolder(itemView)
    }

    // привязать к объекту viewHolder данные для отображения
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = subjects[position]

        holder.timeView.text = pos.date
        holder.subjectView.text = pos.name

    }

    override fun getItemCount(): Int {
        return subjects.size
    }



}