package com.asterisk.simpletodoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private var listOfTask = mutableListOf<String>()
    private lateinit var taskItemAdapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvTasks = findViewById<RecyclerView>(R.id.rvTasks)
        val addTaskBtn = findViewById<Button>(R.id.addTaskBtn)
        val etTaskField = findViewById<EditText>(R.id.etAddTask)

        loadItems()

        taskItemAdapter = TaskItemAdapter(listOfTask, object : OnLongClickListener {
            override fun onItemLongClick(position: Int) {
                listOfTask.removeAt(position)
                taskItemAdapter.notifyDataSetChanged()
                saveItems()
            }
        })
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = taskItemAdapter

        addTaskBtn.setOnClickListener {
            listOfTask.add(etTaskField.text.toString())
            taskItemAdapter.notifyItemInserted(listOfTask.size - 1)
            etTaskField.setText("")
            saveItems()
        }
    }


    private fun getDataFile(): File {
        return File(filesDir, "data.txt")
    }

    private fun loadItems() {
        try {
            listOfTask = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTask)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}