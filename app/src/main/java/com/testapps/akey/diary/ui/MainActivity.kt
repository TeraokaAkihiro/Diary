package com.testapps.akey.diary.ui

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList
import com.testapps.akey.diary.model.DayOfWeekTitle
import com.testapps.akey.diary.R
import com.testapps.akey.diary.data.local.DiaryDBHelper
import com.testapps.akey.diary.model.CalendarMonth
import com.testapps.akey.diary.model.DateTime
import com.testapps.akey.diary.ui.adapter.DayOfWeekAdapter
import java.util.*
import android.view.Menu
import android.view.MenuItem
import com.testapps.akey.diary.ui.adapter.DayBlockAdapter

const val EXTRA_MESSAGE = "com.testapps.akey.diary"
const val INTENT_DATE_FORMAT = "yyyyMMdd"

class MainActivity : AppCompatActivity() {

    lateinit var diaryDBHelper: DiaryDBHelper
    private var nowMonth: DateTime = DateTime()
    private var selectedDate: DateTime? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diaryDBHelper = DiaryDBHelper(this)

        var calendarMonthList: ArrayList<CalendarMonth> = ArrayList()

        var calendar = GregorianCalendar()
        nowMonth.setDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1)
        calendarMonthList.add(CalendarMonth(nowMonth))

        var dayBlockAdapter = DayBlockAdapter(this, nowMonth.year, nowMonth.month)
        gvDayBlocks.adapter = dayBlockAdapter

        addDayOfWeek()

        setTitle(nowMonth.toString("yyyy/MM"))

        gvDayBlocks.setOnItemClickListener { parent, view, position, id ->
            val item = dayBlockAdapter.getItem(position)
            selectedDate = item.date
            val text = readDiaryText(item.date?.toString("yyyyMMdd") ?: "")
            tvDayText.text = text
        }

        tvDayText.setOnClickListener {
            val selectedDate = selectedDate?.toString("yyyyMMdd") ?: ""
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

    private fun addDayOfWeek() {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_options_menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuListOptionPrev ->
                nowMonth.addMonths(-1)
            R.id.menuListOptionNext ->
                nowMonth.addMonths(1)
        }
        var calendarMonthList: ArrayList<CalendarMonth> = ArrayList()

        var calendar = GregorianCalendar()
        calendarMonthList.add(CalendarMonth(nowMonth))

        var dayBlockAdapter = DayBlockAdapter(this, nowMonth.year, nowMonth.month)
        gvDayBlocks.adapter = dayBlockAdapter
        setTitle(nowMonth.toString("yyyy/MM"))
        return super.onOptionsItemSelected(item)
    }
}
