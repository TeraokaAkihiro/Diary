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

const val EXTRA_MESSAGE = "com.testapps.akey.diary"

class MainActivity : AppCompatActivity() {

    var dayBlockAdapter: DayBlockAdapter? = null
    var dayBlockList = ArrayList<DayBlock>()

    var titleAdapter: TitleAdapter? = null
    var titleList = ArrayList<DayOfWeekTitle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gCalendar = GregorianCalendar()
        val toYear = gCalendar.get(Calendar.YEAR)
        val toMonth = gCalendar.get(Calendar.MONTH) // Jan = 0...
        var toMonthFirstCalendar = GregorianCalendar()
        toMonthFirstCalendar.set(toYear, toMonth, 1)
        val toMonthFirstDayOfWeek = toMonthFirstCalendar.get(Calendar.DAY_OF_WEEK)

        // Blank before
        for (i: Int in 2..toMonthFirstDayOfWeek) {
            dayBlockList.add(DayBlock(""))
        }
        var toMonthLastDay = gCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i: Int in 1..toMonthLastDay) {
            dayBlockList.add(DayBlock(i.toString()))
        }

        // Blank after
        for (i: Int in dayBlockList.size + 1..7 * 6) {
            dayBlockList.add(DayBlock(""))
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
    }

    fun sendMessage(view: View) {
        val message = "test message"
        val intent = Intent(this, DiaryInputActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
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
            val food = this.titleList[position]

            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var titleView = inflator.inflate(R.layout.day_of_weeks, null)
            titleView.setBackgroundColor(food.color!!)
            titleView.tvTitle.text = food.name!!

            return titleView
        }
    }

    class DayBlockAdapter : BaseAdapter {
        var foodsList = ArrayList<DayBlock>()
        var context: Context? = null

        constructor(context: Context, foodsList: ArrayList<DayBlock>) : super() {
            this.context = context
            this.foodsList = foodsList
        }

        override fun getCount(): Int {
            return foodsList.size
        }

        override fun getItem(position: Int): Any {
            return foodsList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val food = this.foodsList[position]

            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var dayBlockView = inflator.inflate(R.layout.day_block, null)
            dayBlockView.tvName.text = food.name!!

            return dayBlockView
        }
    }

    inner class CustomTouchListener : View.OnTouchListener {
        override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                }
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                }
            }
            return false
        }
    }
}
