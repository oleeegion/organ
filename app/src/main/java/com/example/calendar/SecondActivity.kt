package com.example.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.DB.ProjectEntity
import com.example.DB.TaskDatabase

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val db: TaskDatabase = TaskDatabase.getDatabase(applicationContext)
        val themeDao = db.getThemeDao()
        setTheme(themeDao, this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val projectDao = db.getProjectDao()

        refreshRecyclerView(db)


        val buttonReturn: Button = findViewById(R.id.button_return2)
        buttonReturn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val buttonAdd: ImageButton = findViewById(R.id.imageButtonAdd)
        buttonAdd.setOnClickListener {
            val editText: EditText = findViewById(R.id.editText)
            val text: String = editText.text.toString()
            if (text.isNotEmpty()) {
                projectDao.addProject(ProjectEntity(0, text, ""))
                editText.text.clear()
                refreshRecyclerView(db)
            } else
                Toast.makeText(applicationContext, "Введите имя проекта", Toast.LENGTH_SHORT).show()
        }

    }



    private fun refreshRecyclerView(database: TaskDatabase) {
        val projectDao = database.getProjectDao()
        val projectEntities: List<ProjectEntity> = projectDao.getAll()

        val recyclerView: RecyclerView = findViewById(R.id.secondRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SecondRecyclerAdapter(projectEntities, projectDao, this)
    }
}

