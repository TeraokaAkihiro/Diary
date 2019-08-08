package com.testapps.akey.diary.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.testapps.akey.diary.R
import com.testapps.akey.diary.model.CalendarMonth
import com.testapps.akey.diary.model.DayBlock
import com.testapps.akey.diary.ui.holder.CalendarMonthViewHolder
import java.util.ArrayList
import com.nakama.arraypageradapter.ArrayViewPagerAdapter
import com.testapps.akey.diary.model.DateTime
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable


class CalendarMonthAdapter(
    calendarMonthList: ArrayList<CalendarMonth>
) : ArrayViewPagerAdapter<CalendarMonth>(calendarMonthList) {

    var selectedDayBlock: DayBlock? = null

    override fun getView(inflater: LayoutInflater, container: ViewGroup, item: CalendarMonth, position: Int): View {
        Log.i("akey", "getView")
        val v = inflater.inflate(R.layout.calendar_month, container, false)
        val vh = CalendarMonthViewHolder(v, container.context, item.date.year, item.date.month)
        var prevView: View? = null
        var prevColorId: Int? = null
        vh.gvDayBlocks.setOnItemClickListener { _, view, clickPosition, _ ->
            selectedDayBlock = vh.dayBlockList[clickPosition]
        }
        Log.i("akey", item.date.toString())
        //container.addView(v)
        return v
    }

    fun addFirst() {
        var addDate = DateTime()
        addDate.setDateTime(getItem(0).date)
        addDate.addMonths(-1)
        add(0, CalendarMonth(addDate))
    }

    fun addLast() {
        var addDate = DateTime()
        addDate.setDateTime(getItem(count - 1).date)
        addDate.addMonths(1)
        add(count, CalendarMonth(addDate))
    }
}
