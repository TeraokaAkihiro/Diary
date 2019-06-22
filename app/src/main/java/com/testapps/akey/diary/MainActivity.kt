package com.testapps.akey.diary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.day_of_weeks.view.*
import kotlinx.android.synthetic.main.day_block.view.*
import java.util.*
import kotlin.collections.ArrayList
import android.view.MotionEvent
import android.widget.AdapterView

const val EXTRA_MESSAGE = "com.testapps.akey.diary"
const val DATE_FORMAT = "yyyyMMdd"

class MainActivity : AppCompatActivity() {

    var dayBlockAdapter: DayBlockAdapter? = null
    var dayBlockList = ArrayList<DayBlock>()

    var selectedDayBlock: DayBlock? = null

    var titleAdapter: TitleAdapter? = null
    var titleList = ArrayList<DayOfWeekTitle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendar = GregorianCalendar()
        val toYear = calendar.get(Calendar.YEAR)
        val toMonth = calendar.get(Calendar.MONTH) // Jan = 0...
        var toMonthFirstCalendar = GregorianCalendar()
        toMonthFirstCalendar.set(toYear, toMonth, 1)
        val toMonthFirstDayOfWeek = toMonthFirstCalendar.get(Calendar.DAY_OF_WEEK)
        val toMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Blank before
        for (i: Int in 2..toMonthFirstDayOfWeek) {
            dayBlockList.add(DayBlock())
        }

        for (i: Int in 1..toMonthLastDay) {
            dayBlockList.add(DayBlock(toYear, toMonth, i))
        }

        // Blank after
        for (i: Int in dayBlockList.size + 1..7 * 6) {
            dayBlockList.add(DayBlock())
        }

        dayBlockAdapter = DayBlockAdapter(this, dayBlockList)

        titleList.add(DayOfWeekTitle("Sun", Color.rgb(244, 67, 54)))
        titleList.add(DayOfWeekTitle("MON", Color.GRAY))
        titleList.add(DayOfWeekTitle("TUE", Color.GRAY))
        titleList.add(DayOfWeekTitle("WED", Color.GRAY))
        titleList.add(DayOfWeekTitle("THU", Color.GRAY))
        titleList.add(DayOfWeekTitle("FRI", Color.GRAY))
        titleList.add(DayOfWeekTitle("SUT", Color.rgb(3, 169, 244)))

        titleAdapter = TitleAdapter(this, titleList)

        gvDayOfWeeks.adapter = titleAdapter
        gvDayBlocks.setOnTouchListener(CustomTouchListener())
        gvDayBlocks.adapter = dayBlockAdapter

        gvDayBlocks.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, id ->
            selectedDayBlock = dayBlockList[position]
            tvDayText.text = selectedDayBlock!!.dateString
        }
        tvDayText.setOnClickListener {
            if (selectedDayBlock == null) return@setOnClickListener
            val date = selectedDayBlock!!.dateString
            val intent = Intent(this, DiaryInputActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, date)
            }
            startActivity(intent)
        }
    }

    class TitleAdapter : BaseAdapter {
        var titleList = ArrayList<DayOfWeekTitle>()
        var context: Context? = null

        constructor(context: Context, foodsList: ArrayList<DayOfWeekTitle>) : super() {
            this.context = context
            this.titleList = foodsList
        }

        override fun getCount(): Int {
            return titleList.size
        }

        override fun getItem(position: Int): Any {
            return titleList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val title = this.titleList[position]

            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var titleView = inflator.inflate(R.layout.day_of_weeks, null)
            titleView.setBackgroundColor(title.color!!)
            titleView.tvTitle.text = title.name!!

            return titleView
        }

    }

    class DayBlockAdapter : BaseAdapter {
        var dayBlockList = ArrayList<DayBlock>()
        var context: Context? = null

        constructor(context: Context, foodsList: ArrayList<DayBlock>) : super() {
            this.context = context
            this.dayBlockList = foodsList
        }

        override fun getCount(): Int {
            return dayBlockList.size
        }

        override fun getItem(position: Int): Any {
            return dayBlockList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val dayBlock = this.dayBlockList[position]

            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var dayBlockView = inflator.inflate(R.layout.day_block, null)

            dayBlockView.tvName.text = dayBlock.name

            return dayBlockView
        }
    }

    inner class CustomTouchListener : View.OnTouchListener {
        override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    var name = view.tvName
                }
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                }
            }
            return false
        }
    }
}
