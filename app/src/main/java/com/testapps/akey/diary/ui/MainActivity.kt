package com.testapps.akey.diary.ui

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList
import com.testapps.akey.diary.model.DayOfWeekTitle
import com.testapps.akey.diary.R
import com.testapps.akey.diary.data.local.DiaryDBHelper
import com.testapps.akey.diary.model.CalendarMonth
import com.testapps.akey.diary.model.DateTime
import com.testapps.akey.diary.ui.adapter.CalendarMonthAdapter
import com.testapps.akey.diary.ui.adapter.DayOfWeekAdapter
import java.util.*
import android.support.v4.view.ViewPager.OnPageChangeListener

const val EXTRA_MESSAGE = "com.testapps.akey.diary"
const val INTENT_DATE_FORMAT = "yyyyMMdd"

class MainActivity : AppCompatActivity() {

    lateinit var diaryDBHelper: DiaryDBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diaryDBHelper = DiaryDBHelper(this)

        var calendarMonthList: ArrayList<CalendarMonth> = ArrayList()

        var calendar = GregorianCalendar()
        var date = DateTime()
        date.setDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1)
        calendarMonthList.add(CalendarMonth(date))

        var viewPager: ViewPager = findViewById(R.id.vpDayBlocks)
        var calendarMonthAdapter = CalendarMonthAdapter(calendarMonthList)
        viewPager.adapter = calendarMonthAdapter

        var currentPage = 12 * 4
        for (i in 1..currentPage) {
            calendarMonthAdapter.addFirst()
            calendarMonthAdapter.addLast()
        }

        viewPager.currentItem = currentPage

        addTitle()

        tvDayText.setOnClickListener {
            val selectedDate = calendarMonthAdapter.selectedDayBlock?.getIntentString()
            if (selectedDate.isNullOrEmpty()) return@setOnClickListener
            val intent = Intent(this, DiaryInputActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, selectedDate)
            }
            startActivity(intent)
        }
    }

    private fun readDiaryText(date: String): String {
        return diaryDBHelper.readDiary(date)
    }

    private fun addTitle() {
        var dayOfWeekAdapter: DayOfWeekAdapter?
        var dayOfWeekList = ArrayList<DayOfWeekTitle>()

        val dayOfWeeks = resources.getStringArray(R.array.day_of_week)

        for (i in 0 until dayOfWeeks.size) {
            dayOfWeekList.add(DayOfWeekTitle(dayOfWeeks[i], Color.GRAY))
        }
        dayOfWeekList.first().color = Color.rgb(244, 67, 54)
        dayOfWeekList.last().color = Color.rgb(3, 169, 244)

        dayOfWeekAdapter = DayOfWeekAdapter(this, dayOfWeekList)
        gvDayOfWeeks.adapter = dayOfWeekAdapter
    }
}
