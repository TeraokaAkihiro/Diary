package com.testapps.akey.diary

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.MenuItem
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_diary_input.*
import kotlinx.android.synthetic.main.content_diary_input.view.*
import java.text.SimpleDateFormat
import java.util.*

var dateString: String = ""
var date: Date? = null

class DiaryInputActivity : AppCompatActivity() {

    lateinit var diaryDBHelper: DiaryDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_input)
        setSupportActionBar(toolbar)

        diaryDBHelper = DiaryDBHelper(this)

        // FloatingActionButton above keyboard
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dateString = intent.getStringExtra(EXTRA_MESSAGE)
        date = SimpleDateFormat(DATE_FORMAT).parse(dateString)

        include.editText.setText(diaryDBHelper.readDiary(dateString))

        save.setOnClickListener {
            onSaveClick()
        }
    }

    private fun onSaveClick() {
        var diaryModel = DiaryModel(date = dateString, text = include.editText.text.toString())
        if (!diaryDBHelper.updateDiary(diaryModel)) {
            diaryDBHelper.insertDiary(diaryModel)
        }
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                popAlertDialog()
        }
        return true // Never close
    }

    private fun popAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(R.string.save_dialog_message)
            .setCancelable(false)
            .setPositiveButton(R.string.save_dialog_ok) { _, _ -> finish() }
            .setNegativeButton(R.string.save_dialog_no) { dialog, _ -> dialog.cancel() }
        val alert = dialogBuilder.create()
        alert.show()
    }
}
