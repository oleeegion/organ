package com.example.calendar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.DB.TaskDatabase
import com.example.DB.TaskEntity


class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val db: TaskDatabase = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java, "room-database"
            ).allowMainThreadQueries()
             .build()
//
//
        val taskDao = db.getTaskDao()
//        taskDao.deleteAll()
//        val task = TaskEntity(null, "testNull", "01/02/1973")
//        val query = taskDao.getAll()
//        println(query)
//        if (query != null) {
//            println("task added. Name: " + query.name + query.id)
//        } else {
//            println("Пустая строка")
//        }





        val buttonTestReturn: Button = findViewById(R.id.button_test_return)
        buttonTestReturn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val button: Button = findViewById(R.id.button_test)
        button.setOnClickListener {
            val query = taskDao.getAll()
//            taskDao.addTask(TaskEntity(0, "testTask", "01.02.2003"))
            println(query)

//            if (query != null) {
//                print("task added. Name: " + query.name + query.id)
//            } else {
//                print("Пустая строка")
//            }

        }



    }
}