package com.testapps.akey.diary.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.testapps.akey.diary.R
import com.testapps.akey.diary.model.DayBlock
import kotlinx.android.synthetic.main.day_block.view.*
import java.util.*
import kotlin.collections.ArrayList

class DayBlockAdapter : BaseAdapter {
    var dayBlockList = ArrayList<DayBlock>()
    var context: Context? = null

    constructor(context: Context, year: Int, month: Int) : super() {
        this.context = context
        this.dayBlockList = getMonthDayBlocks(year, month)
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

    private fun getMonthDayBlocks(year: Int, month: Int): ArrayList<DayBlock> {
        var list = ArrayList<DayBlock>()

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
