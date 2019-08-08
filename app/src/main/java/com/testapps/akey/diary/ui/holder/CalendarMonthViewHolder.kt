package com.testapps.akey.diary.ui.holder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.GridView
import com.testapps.akey.diary.R
import com.testapps.akey.diary.model.DayBlock
import com.testapps.akey.diary.ui.adapter.DayBlockAdapter

class CalendarMonthViewHolder(itemView: View, private val context: Context, year: Int, month: Int) :
    RecyclerView.ViewHolder(itemView) {

    val gvDayBlocks: GridView = itemView.findViewById(R.id.gvDayBlocks)

    var dayBlockAdapter = DayBlockAdapter(context, year, month)

    var dayBlockList: ArrayList<DayBlock> = dayBlockAdapter.dayBlockList

    init {
        gvDayBlocks.adapter = dayBlockAdapter
    }
}
