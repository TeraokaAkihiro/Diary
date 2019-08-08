package com.testapps.akey.diary.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.testapps.akey.diary.R
import com.testapps.akey.diary.model.DayBlock
import com.testapps.akey.diary.model.DayOfWeekTitle
import kotlinx.android.synthetic.main.day_of_weeks.view.*

class DayOfWeekAdapter : BaseAdapter {
    var dayOfWeekList = ArrayList<DayOfWeekTitle>()
    var context: Context? = null

    constructor(context: Context, dayOfWeekList: ArrayList<DayOfWeekTitle>) : super() {
        this.context = context
        this.dayOfWeekList = dayOfWeekList
    }

    override fun getCount(): Int {
        return dayOfWeekList.size
    }

    override fun getItem(position: Int): Any {
        return dayOfWeekList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val title = this.dayOfWeekList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var titleView = inflator.inflate(R.layout.day_of_weeks, null)
        titleView.setBackgroundColor(title.color!!)
        titleView.tvTitle.text = title.name!!

        return titleView
    }
}
