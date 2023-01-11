package com.example.calendar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.DB.TaskDatabase


class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val db: TaskDatabase = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java, "room-database"
            ).allowMainThreadQueries()
             .build()

        val taskDao = db.getTaskDao()



        val buttonTestReturn: Button = findViewById(R.id.button_test_return)
        buttonTestReturn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val button: Button = findViewById(R.id.button_test)
        button.setOnClickListener {
            val query = taskDao.getAll()
            println(query)

        }



    }
}