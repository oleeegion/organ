package com.example.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import androidx.room.Room
import com.example.DB.TaskDatabase
import com.example.DB.TaskEntity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val db: TaskDatabase = TaskDatabase.getDatabase(applicationContext)

        val dayTV: TextView = findViewById(R.id.textViewDay)
        val arrayWeek = arrayOf("ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ")
        var day: String = arrayWeek[0]
        dayTV.text = day


        val button: Button = findViewById(R.id.button_return2)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val buttonBack: ImageButton = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener {
            var index = arrayWeek.indexOf(day) - 1
            if (index < 0)
                index = arrayWeek.size - 1
            day = arrayWeek[index]
            dayTV.text = day
//            val db: TaskDatabase = Room.databaseBuilder(
//                applicationContext,
//                TaskDatabase::class.java, "room-database"
//            )
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build()
//            refreshScheduleList(db, day)
        }
        val buttonForward: ImageButton = findViewById(R.id.buttonForward)
        buttonForward.setOnClickListener {
            var index = arrayWeek.indexOf(day) + 1
            if (index >= arrayWeek.size)
                index = 0
            day = arrayWeek[index]
            dayTV.text = day

//            val db: TaskDatabase = Room.databaseBuilder(
//                applicationContext,
//                TaskDatabase::class.java, "room-database"
//            )
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build()
//            refreshScheduleList(db, day)
        }


//        val thread = Thread {
//            try {
////                val db1: TaskDatabase = Room.databaseBuilder(
////                    applicationContext,
////                    TaskDatabase::class.java, "room-database"
////                ).allowMainThreadQueries()
////                    .fallbackToDestructiveMigration()
////                    .build()
//                val db1: TaskDatabase = TaskDatabase.getDatabase(applicationContext)
//                val schedule = parse_schedule()
//                val subjects: List<TaskEntity> = createSubjectList(day, arrayWeek, schedule, 3)
//                val dao = db1.getTaskDao()
//                dao.deleteByTag("subj")
//                for (subj in subjects) {
//                    dao.addTask(subj)
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//        thread.start()

//        val db: TaskDatabase = Room.databaseBuilder(
//            applicationContext,
//            TaskDatabase::class.java, "room-database"
//        )
//            .allowMainThreadQueries()
//            .fallbackToDestructiveMigration()
//            .build()
//        refreshScheduleList(db, day)
    }}


//    private fun createSubjectList(_day: String, arrayWeek: Array<String>, schedule: MutableList<TableRow>, _groupIndex: Int): List<TaskEntity> {
//        val result = mutableListOf<TaskEntity>()
//        for (subj in schedule.drop(1)) {
//            val day = subj[0]
//            val name = subj[_groupIndex]
//            if (name == "") {
//                continue
//            }
//            val time = subj[1]
//            if (day == null || time == null || name == null ) {
//                throw Exception("TIME OR NAME CANNOT BE NULL ABORT MISSION WE'RE ALL GONNA DIE")
////                println()
//            }
//            result.add(TaskEntity(0, name, time, "subj", day))
//        }
//
//        return result
//    }

//    private fun refreshScheduleList(db: TaskDatabase, day: String) {
//        val recyclerView: RecyclerView = findViewById(R.id.secondRecyclerView)
//        val dao = db.getTaskDao()
//        val subjects: List<TaskEntity> = dao.getAll().filter { it.tag == "subj" && it.day == day }
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = SecondRecyclerAdapter(subjects)
//    }
//}
