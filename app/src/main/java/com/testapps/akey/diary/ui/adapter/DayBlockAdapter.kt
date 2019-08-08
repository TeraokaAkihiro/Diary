package com.testapps.akey.diary.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import com.testapps.akey.diary.R
import com.testapps.akey.diary.model.DateTime
import com.testapps.akey.diary.model.DayBlock
import com.testapps.akey.diary.ui.INTENT_DATE_FORMAT
import kotlinx.android.synthetic.main.day_block.view.*
import java.util.*
import kotlin.collections.ArrayList

class DayBlockAdapter : BaseAdapter {
    var dayBlockList = ArrayList<DayBlock>()
    var context: Context? = null

    var toYear: Int = 0
    var toMonth: Int = 0

    constructor(context: Context, year: Int, month: Int) : super() {
        this.context = context
        this.dayBlockList = getMonthDayBlocks(year, month)
        toYear = year
        toMonth = month
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
        val dayBlock = dayBlockList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var dayBlockView = inflator.inflate(R.layout.day_block, null)

        dayBlockView.tvName.text = dayBlock.name

        val li = dayBlockView.findViewById(R.id.linearLayout) as LinearLayout
        if (!(toYear == dayBlock.date?.year ?: 0 || toMonth == dayBlock.date?.month ?: 0)) {
            li.setBackgroundColor(context!!.resources.getColor(R.color.calendarBackgroundSelected))
        } else {
            li.setBackgroundColor(context!!.resources.getColor(R.color.calendarBackgroundDefault))
        }

        return dayBlockView
    }

    private fun getMonthDayBlocks(year: Int, month: Int): ArrayList<DayBlock> {
        var list = ArrayList<DayBlock>()

        // 当月の１日の曜日から表示上の最初の日曜日の日付を求めてそっからカウントしていくじょい
        var calendar = GregorianCalendar()
        calendar.set(year, month, 1)
        val toMonthFirstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val toMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i: Int in 2..toMonthFirstDayOfWeek) {
            list.add(DayBlock())
        }

        for (i: Int in 1..toMonthLastDay) {
            list.add(DayBlock(year, month, i))
        }

        for (i: Int in list.size + 1..7 * 6) {
            list.add(DayBlock())
        }
        return list
    }
}
