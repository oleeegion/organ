package com.example.calendar

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.DB.TaskDatabase
import com.example.DB.TaskEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var datePicker: DatePickerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //метод для вызова экрана


        val db: TaskDatabase = TaskDatabase.getDatabase(applicationContext)
        val taskDao = db.getTaskDao()

        currentDate()
        refreshRecyclerView(db)


        datePicker = DatePickerHelper(this)
        val btSelectDate: TextView = findViewById(R.id.btSelectDate)
        btSelectDate.setOnClickListener {
            showDatePickerDialog(db)
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
                taskDao.addTask(TaskEntity(0, text, dateTV.text.toString()))
                editText.text.clear()
                refreshRecyclerView(db)
            }
            else
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

    fun refreshRecyclerView(database: TaskDatabase) {
        val taskDao = database.getTaskDao()
        val dateTV: TextView = findViewById(R.id.idDate)
        val selectedDate = dateTV.text.toString()
        val taskEntities: List<TaskEntity> = taskDao.getAll().filter { it.date == selectedDate }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter(taskEntities, taskDao)

    }


    private fun showDatePickerDialog(database: TaskDatabase) {
        val dateTV: TextView = findViewById(R.id.idDate)
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)
        datePicker.showDialog(d, m, y, object: DatePickerHelper.Callback {

            override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                val mon = month + 1
                val monthStr = if (mon < 10) "0$mon" else "$mon"
                dateTV.text = "$dayStr.$monthStr.$year"
                refreshRecyclerView(database)
            }
        })
    }
}


class DatePickerHelper(context: Context, isSpinnerType: Boolean = false) {
    private var dialog: DatePickerDialog
    private var callback: Callback? = null
    private val listener =
        DatePickerDialog.OnDateSetListener {datePicker, year, monthOfYear, dayOfMonth ->
            callback?.onDateSelected(dayOfMonth, monthOfYear, year)
        }
    init {
        val style = if (isSpinnerType) R.style.SpinnerDatePickerDialog else 0
        val cal = Calendar.getInstance()
        dialog = DatePickerDialog(context, style, listener,
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
    }
    fun showDialog(dayOfMonth: Int, month: Int, year: Int, callback: Callback?) {
        this.callback = callback
        dialog.datePicker.init(year, month, dayOfMonth, null)
        dialog.show()
    }

    interface Callback {
        fun onDateSelected(dayOfMonth: Int, month: Int, year: Int)
    }

}





