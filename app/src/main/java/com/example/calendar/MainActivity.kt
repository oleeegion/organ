package com.example.calendar

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.DB.TaskDatabase
import com.example.DB.TaskEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //метод для вызова экрана

        val db: TaskDatabase = TaskDatabase.getDatabase(applicationContext)
        val taskDao = db.getTaskDao()

        currentDate()
        refreshRecyclerView(db)

        val btSelectDate: TextView = findViewById(R.id.btSelectDate)
        btSelectDate.setOnClickListener {
            val dateTV: TextView = findViewById(R.id.idDate)
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val day_choice = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                val dayOfMonth = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                val mon = monthOfYear + 1
                val month = if (mon < 10) "0$mon" else "$mon"
                dateTV.text = "$dayOfMonth.$month.$year"
                refreshRecyclerView(db)
            }, year, month, day)
            day_choice.show()
        }
        val buttonAct: Button = findViewById(R.id.button_activity)
        buttonAct.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        val buttonAdd: ImageButton = findViewById(R.id.imageButton1)
        buttonAdd.setOnClickListener {
            val dateTV: TextView = findViewById(R.id.idDate)
            val editText: EditText = findViewById(R.id.editText)
            val text: String = editText.text.toString()
            if (text.isNotEmpty()) {
                taskDao.addTask(TaskEntity(0, text, dateTV.text.toString(), 0))
                editText.text.clear()
                refreshRecyclerView(db)
            } else
                Toast.makeText(applicationContext, "Введите задачу", Toast.LENGTH_SHORT).show()
        }
    }


    private fun currentDate() {
        val dateTV: TextView = findViewById(R.id.idDate)
        val curDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatted = curDate.format(formatter)
        dateTV.text = formatted
    }

    private fun refreshRecyclerView(database: TaskDatabase) {
        val taskDao = database.getTaskDao()
        val dateTV: TextView = findViewById(R.id.idDate)
        val selectedDate = dateTV.text.toString()
        val taskEntities: List<TaskEntity> = taskDao.getAll().filter { it.date == selectedDate }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter(taskEntities, taskDao, this)
    }
}





