package com.testapps.akey.diary.ui.holder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.GridView
import com.testapps.akey.diary.R
import com.testapps.akey.diary.model.DayBlock
import com.testapps.akey.diary.ui.adapter.DayBlockAdapter
import java.util.*

class CalendarMonthViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {

    private val gvDayBlocks: GridView = itemView.findViewById(R.id.gvDayBlocks)

    var dayBlockAdapter: DayBlockAdapter? = null
    var dayBlockList = ArrayList<DayBlock>()

    init {
        addDayBlocks(context)
    }

    private fun addDayBlocks(context: Context) {
        val calendar = GregorianCalendar()
        val toYear = calendar.get(Calendar.YEAR)
        val toMonth = calendar.get(Calendar.MONTH)
        var toMonthFirstCalendar = GregorianCalendar()
        toMonthFirstCalendar.set(toYear, toMonth, 1)
        val toMonthFirstDayOfWeek = toMonthFirstCalendar.get(Calendar.DAY_OF_WEEK)
        val toMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i: Int in 2..toMonthFirstDayOfWeek) {
            dayBlockList.add(DayBlock())
        }

        for (i: Int in 1..toMonthLastDay) {
            dayBlockList.add(DayBlock(toYear, toMonth, i))
        }

        for (i: Int in dayBlockList.size + 1..7 * 6) {
            dayBlockList.add(DayBlock())
        }

        dayBlockAdapter = DayBlockAdapter(context, dayBlockList)

        gvDayBlocks.adapter = dayBlockAdapter
    }
}
