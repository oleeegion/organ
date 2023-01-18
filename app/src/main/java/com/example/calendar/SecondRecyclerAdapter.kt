package com.example.calendar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.DB.ProjectDao
import com.example.DB.ProjectEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class SecondRecyclerAdapter(private val projectEntities: List<ProjectEntity>,
                            private val projectDao: ProjectDao,
                            private val act: AppCompatActivity
): RecyclerView.Adapter<SecondRecyclerAdapter.ViewHolder>() {
    private var projects: MutableList<ProjectEntity> = projectEntities as MutableList<ProjectEntity>

    // хранение и визуализация элементов списка
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        val idTextView: TextView = itemView.findViewById(R.id.textViewId)
        private val delButton: ImageButton = itemView.findViewById(R.id.delButton)
        private val buttonDeadline: ImageButton = itemView.findViewById(R.id.addDeadline)

        private fun currentDate(): String? {
            val curDate = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val formatted = curDate.format(formatter)
            return formatted
        }

        fun getColorDeadline(project: ProjectEntity, index: Int) {
            val date: TextView = itemView.findViewById(R.id.textViewDeadline)
            val deadlineExe: TextView = itemView.findViewById(R.id.textViewExe)
            date.text = project.date
            if (project.date == currentDate()){
                date.setTextColor(Color.parseColor("#e66b6b"))
                deadlineExe.setTextColor(Color.parseColor("#e66b6b"))
                createNotification()
                createChannel("01", "deadline")
            }
        }
        fun addDeadline(project: ProjectEntity, index: Int) {
            val date: TextView = itemView.findViewById(R.id.textViewDeadline)
            val deadlineExe: TextView = itemView.findViewById(R.id.textViewExe)

            buttonDeadline.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                val day_choice = DatePickerDialog(act, { _, year, monthOfYear, dayOfMonth ->
                    val dayOfMonth = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                    val mon = monthOfYear + 1
                    val month = if (mon < 10) "0$mon" else "$mon"
                    date.text = "$dayOfMonth.$month.$year"

                    projectDao.updateDate("$dayOfMonth.$month.$year", project.id)
                    if ("$dayOfMonth.$month.$year" == currentDate()){
                        date.setTextColor(Color.parseColor("#e66b6b"))
                        deadlineExe.setTextColor(Color.parseColor("#e66b6b"))
                        createNotification()
                        createChannel("01", "deadline")}
                    else {
                        date.setTextColor(Color.parseColor("#948185"))
                        deadlineExe.setTextColor(Color.parseColor("#948185")) }
                }, year, month, day)
                day_choice.show()
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        fun deleteProject(index: Int){
            delButton.setOnClickListener {
                val project: ProjectEntity? = projectDao.getById(idTextView.text.toString().toInt())
                projectDao.deleteProject(project)
                projects.removeAt(index)
                notifyDataSetChanged()
            }
        }

        @SuppressLint("UnspecifiedImmutableFlag")
        fun createNotification() {
            val notificationId = 1
            val intent = Intent(act.applicationContext, SecondActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                act.applicationContext,
                notificationId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            val builder = NotificationCompat.Builder(act, "01")
                .setSmallIcon(R.drawable.ic_candy)
                .setLargeIcon(BitmapFactory.decodeResource(act.resources, R.drawable.ic_panic))
                .setContentTitle("Внимание")
                .setContentText("Дедлайн горит!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

            val notificationManager = NotificationManagerCompat.from(act)
            notificationManager.notify(notificationId, builder.build())
        }

        private fun createChannel(channelId: String, channelName: String) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW)
            val notificationManager = act.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_project_maket, parent, false)
        return ViewHolder(itemView)
    }

    // привязать к объекту viewHolder данные для отображения
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = projectEntities[position]
        holder.nameTextView.text = pos.name
        holder.idTextView.text = pos.id.toString()

        holder.deleteProject(position)
        holder.addDeadline(pos, position)
        holder.getColorDeadline(pos, position)
    }

    override fun getItemCount(): Int {
        return projectEntities.size
    }



}