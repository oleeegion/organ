package com.example.calendar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.DB.TaskDao
import com.example.DB.TaskEntity


class RecyclerAdapter(private val taskEntities: List<TaskEntity>,
                      private val taskDao: TaskDao,
                      private val act: AppCompatActivity
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private var tasks: MutableList<TaskEntity> = taskEntities as MutableList<TaskEntity>

    // хранение и визуализация элементов списка
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        val idTextView: TextView = itemView.findViewById(R.id.textViewId)
        private val deleteBtn: ImageButton = itemView.findViewById(R.id.delButton)
        private val priorBtn: ImageButton = itemView.findViewById(R.id.priorButton)

        fun setColorTask(task: TaskEntity) {
            when (task.prior) {
                1 ->{
                    priorBtn.setImageDrawable(ContextCompat.getDrawable(act, R.drawable.ic_cube_red)) }
                2 ->{
                    priorBtn.setImageDrawable(ContextCompat.getDrawable(act, R.drawable.ic_cube_yellow)) }
                3 ->{
                    priorBtn.setImageDrawable(ContextCompat.getDrawable(act, R.drawable.ic_cube_green)) }
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        fun deleteTask(index: Int){
            deleteBtn.setOnClickListener {
                val task: TaskEntity? = taskDao.getById(idTextView.text.toString().toInt())
                taskDao.deleteTask(task)
                tasks.removeAt(index)
                notifyDataSetChanged()
            }
        }

        fun getPriorTask(){
            priorBtn.setOnClickListener {
                val popupMenu = PopupMenu(act, priorBtn)
                val task: TaskEntity? = taskDao.getById(idTextView.text.toString().toInt())

                popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.menu1 -> {
                            priorBtn.setImageDrawable(ContextCompat.getDrawable(act, R.drawable.ic_cube_red))
                            taskDao.updatePrior(1, task?.id)}
                        R.id.menu2 -> {
                            priorBtn.setImageDrawable(ContextCompat.getDrawable(act, R.drawable.ic_cube_yellow))
                            taskDao.updatePrior(2, task?.id)}
                        R.id.menu3 -> {
                            priorBtn.setImageDrawable(ContextCompat.getDrawable(act, R.drawable.ic_cube_green))
                            taskDao.updatePrior(3, task?.id)}
                    }
                    true
                })
                popupMenu.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_maket, parent, false)
        return ViewHolder(itemView)
    }

    // привязать к объекту viewHolder данные для отображения
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = taskEntities[position]
        holder.nameTextView.text = pos.name
        holder.idTextView.text = pos.id.toString()

        holder.deleteTask(position)
        holder.getPriorTask()
        holder.setColorTask(pos)
    }

    override fun getItemCount(): Int {
        return taskEntities.size
    }

}