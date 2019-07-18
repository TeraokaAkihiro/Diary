package com.testapps.akey.diary.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.testapps.akey.diary.R
import com.testapps.akey.diary.model.CalendarMonth
import com.testapps.akey.diary.model.DayBlock
import com.testapps.akey.diary.ui.holder.CalendarMonthViewHolder
import java.util.ArrayList

class CalendarMonthAdapter(private val context: Context, private val calendarMonthList: ArrayList<CalendarMonth>) :
    RecyclerView.Adapter<CalendarMonthViewHolder>() {

    var selectedDayBlock: DayBlock? = null

    override fun getItemCount(): Int {
        return calendarMonthList.size
    }

    override fun onBindViewHolder(vh: CalendarMonthViewHolder, position: Int) {
        vh.onCreateDayBlocks(
            calendarMonthList[position].year!!,
            calendarMonthList[position].month!!
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarMonthViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val v = layoutInflater.inflate(R.layout.calendar_month, parent, false)
        val vh = CalendarMonthViewHolder(v, context)
        vh.gvDayBlocks.setOnItemClickListener { _, _, position, _ ->
            selectedDayBlock = vh.dayBlockList[position]
        }
        return vh
    }
}
