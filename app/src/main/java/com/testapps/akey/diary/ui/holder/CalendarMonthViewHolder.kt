package com.testapps.akey.diary.ui.holder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.GridView
import com.testapps.akey.diary.R
import com.testapps.akey.diary.model.DayBlock
import com.testapps.akey.diary.ui.adapter.DayBlockAdapter
import java.util.*

class CalendarMonthViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

    val gvDayBlocks: GridView = itemView.findViewById(R.id.gvDayBlocks)

    var dayBlockList = ArrayList<DayBlock>()

    fun onCreateDayBlocks(year: Int, month: Int) {
        var dayBlockAdapter = DayBlockAdapter(context, year, month)
        dayBlockList = dayBlockAdapter.dayBlockList
        gvDayBlocks.adapter = dayBlockAdapter
    }
}
