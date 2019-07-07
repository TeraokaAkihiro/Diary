package com.testapps.akey.diary.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.testapps.akey.diary.R
import com.testapps.akey.diary.ui.holder.CalendarMonthViewHolder
import java.util.ArrayList

class CalendarMonthAdapter(private var context: Context, private val itemCount: Int) :
    RecyclerView.Adapter<CalendarMonthViewHolder>() {

    private var appList: ArrayList<CalendarMonthViewHolder> = ArrayList<CalendarMonthViewHolder>()

    override fun getItemCount(): Int {
        return itemCount
    }

    override fun onBindViewHolder(vh: CalendarMonthViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarMonthViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val v = layoutInflater.inflate(R.layout.calendar_month, parent, false)
        val vh = CalendarMonthViewHolder(v, context)
        appList.add(vh)
        return vh
    }
}
