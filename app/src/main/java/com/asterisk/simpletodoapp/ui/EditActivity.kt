package com.asterisk.simpletodoapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.asterisk.simpletodoapp.R
import com.asterisk.simpletodoapp.other.Constants.KEY_ITEM_POSITION
import com.asterisk.simpletodoapp.other.Constants.KEY_ITEM_TEXT

class EditActivity : AppCompatActivity() {

    private lateinit var etText: EditText
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        title = getString(R.string.edit_item)

        etText = findViewById(R.id.etUpdateItem)
        saveBtn = findViewById(R.id.btnSave)

        var valueToUpdate = intent.getStringExtra(KEY_ITEM_TEXT)
        val valueToUpdatePosition = intent.extras?.getInt(KEY_ITEM_POSITION)
        etText.setText(valueToUpdate)

        saveBtn.setOnClickListener {
            val intent = Intent()
            intent.apply {
                this.putExtra(KEY_ITEM_TEXT, etText.text.toString())
                this.putExtra(KEY_ITEM_POSITION, valueToUpdatePosition)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}