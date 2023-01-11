package com.example.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.DB.TaskDatabase

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val db: TaskDatabase = TaskDatabase.getDatabase(applicationContext)


        val buttonReturn: Button = findViewById(R.id.button_return2)
        buttonReturn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val buttonBack: ImageButton = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener {

        }
        val buttonForward: ImageButton = findViewById(R.id.buttonForward)
        buttonForward.setOnClickListener {

        }

    }
}

