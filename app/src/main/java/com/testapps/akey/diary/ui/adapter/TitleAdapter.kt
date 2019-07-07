package com.testapps.akey.diary.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.testapps.akey.diary.R
import com.testapps.akey.diary.model.DayOfWeekTitle
import kotlinx.android.synthetic.main.day_of_weeks.view.*

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
        val title = this.titleList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var titleView = inflator.inflate(R.layout.day_of_weeks, null)
        titleView.setBackgroundColor(title.color!!)
        titleView.tvTitle.text = title.name!!

        return titleView
    }
}
