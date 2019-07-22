package com.testapps.akey.diary.ui

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.*
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList
import com.testapps.akey.diary.model.DayOfWeekTitle
import com.testapps.akey.diary.R
import com.testapps.akey.diary.data.local.DiaryDBHelper
import com.testapps.akey.diary.model.CalendarMonth
import com.testapps.akey.diary.model.DateTime
import com.testapps.akey.diary.ui.adapter.CalendarMonthAdapter
import com.testapps.akey.diary.ui.adapter.TitleAdapter
import java.util.*

const val EXTRA_MESSAGE = "com.testapps.akey.diary"
const val INTENT_DATE_FORMAT = "yyyyMMdd"

class MainActivity : AppCompatActivity() {

    lateinit var diaryDBHelper: DiaryDBHelper

    var calendarMonthList: ArrayList<CalendarMonth> = ArrayList()
    var calendarMonthAdapter: CalendarMonthAdapter = CalendarMonthAdapter(this, calendarMonthList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diaryDBHelper = DiaryDBHelper(this)

        var calendar = GregorianCalendar()
        var date = DateTime()
        date.setDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1)
        calendarMonthList.add(CalendarMonth(date))

        var recyclerView: RecyclerView = findViewById(R.id.rvDayBlocks)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = calendarMonthAdapter


        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvDayBlocks)

        addTitle()

        tvDayText.setOnClickListener {
            val date = calendarMonthAdapter.selectedDayBlock?.getIntentString()
            if (date.isNullOrEmpty()) return@setOnClickListener
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
