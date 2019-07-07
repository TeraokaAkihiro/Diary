package com.testapps.akey.diary.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.testapps.akey.diary.R
import com.testapps.akey.diary.model.DayBlock
import kotlinx.android.synthetic.main.day_block.view.*

class DayBlockAdapter : BaseAdapter {
    var dayBlockList = ArrayList<DayBlock>()
    var context: Context? = null

    constructor(context: Context, foodsList: ArrayList<DayBlock>) : super() {
        this.context = context
        this.dayBlockList = foodsList
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
}
