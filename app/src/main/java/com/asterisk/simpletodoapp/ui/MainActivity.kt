package com.asterisk.simpletodoapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asterisk.simpletodoapp.other.OnLongClickListener
import com.asterisk.simpletodoapp.R
import com.asterisk.simpletodoapp.adapter.TaskItemAdapter
import com.asterisk.simpletodoapp.other.Constants.KEY_ITEM_POSITION
import com.asterisk.simpletodoapp.other.Constants.KEY_ITEM_TEXT
import com.asterisk.simpletodoapp.other.Constants.EDIT_TEXTREQUEST_CODE
import com.asterisk.simpletodoapp.other.OnClickListener
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
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

        val onLongClick = object : OnLongClickListener {
            override fun onItemLongClick(position: Int) {
                listOfTask.removeAt(position)
                taskItemAdapter.notifyDataSetChanged()
                saveItems()
            }
        }

        val onClick = object : OnClickListener {
            override fun onItemClicked(position: Int) {
                val intent = Intent(this@MainActivity, EditActivity::class.java)
                intent.putExtra(KEY_ITEM_TEXT, listOfTask[position])
                intent.putExtra(KEY_ITEM_POSITION, position)
                startActivityForResult(intent, EDIT_TEXTREQUEST_CODE)
            }

        }


        taskItemAdapter = TaskItemAdapter(listOfTask, onLongClick, onClick)

        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = taskItemAdapter

        addTaskBtn.setOnClickListener {
            listOfTask.add(etTaskField.text.toString())
            taskItemAdapter.notifyItemInserted(listOfTask.size - 1)
            etTaskField.setText("")
            saveItems()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_TEXTREQUEST_CODE && resultCode == RESULT_OK) {
            val updatedText = data?.getStringExtra(KEY_ITEM_TEXT)
            val position = data?.extras?.getInt(KEY_ITEM_POSITION)
            if (updatedText != null && position != null) {
                listOfTask[position] = updatedText
            }

            if (position != null) {
                taskItemAdapter.notifyItemChanged(position)
            }
            saveItems()

        } else {
            Log.w("Warning", "unknown call to mainActivity")
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