package com.testapps.akey.diary.ui

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList
import com.testapps.akey.diary.model.DayBlock
import com.testapps.akey.diary.model.DayOfWeekTitle
import com.testapps.akey.diary.R
import com.testapps.akey.diary.data.local.DiaryDBHelper
import com.testapps.akey.diary.model.CalendarMonth
import com.testapps.akey.diary.ui.adapter.CalendarMonthAdapter
import com.testapps.akey.diary.ui.adapter.TitleAdapter

const val EXTRA_MESSAGE = "com.testapps.akey.diary"
const val DATE_FORMAT = "yyyyMMdd"

class MainActivity : AppCompatActivity() {

    lateinit var diaryDBHelper: DiaryDBHelper

    var selectedDayBlock: DayBlock? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diaryDBHelper = DiaryDBHelper(this)

        var calendarMonthAdapter: CalendarMonthAdapter
        var calendarMonthList = ArrayList<CalendarMonth>()
        calendarMonthList.add(CalendarMonth())
        calendarMonthList.add(CalendarMonth())
        calendarMonthList.add(CalendarMonth())
        calendarMonthAdapter = CalendarMonthAdapter(this, calendarMonthList.size)

        var recyclerView: RecyclerView = findViewById(R.id.rvDayBlocks)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = calendarMonthAdapter

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rvDayBlocks)

        addTitle()

        tvDayText.setOnClickListener {
            if (selectedDayBlock == null) return@setOnClickListener
            val date = selectedDayBlock!!.dateString
            val intent = Intent(this, DiaryInputActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, date)
            }
            startActivity(intent)
        }
    }

    private fun readDiaryText(date: String): String {
        return diaryDBHelper.readDiary(date)
    }

    private fun addTitle() {
        var titleAdapter: TitleAdapter?
        var titleList = ArrayList<DayOfWeekTitle>()

        val dayOfWeeks = resources.getStringArray(R.array.day_of_week)

        for (i in 0 until dayOfWeeks.size) {
            titleList.add(DayOfWeekTitle(dayOfWeeks[i], Color.GRAY))
        }
        titleList[0].color = Color.rgb(244, 67, 54)
        titleList[dayOfWeeks.size - 1].color = Color.rgb(3, 169, 244)

        titleAdapter = TitleAdapter(this, titleList)
        gvDayOfWeeks.adapter = titleAdapter
    }
}
