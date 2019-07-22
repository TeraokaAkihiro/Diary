package com.testapps.akey.diary.model

import com.testapps.akey.diary.ui.INTENT_DATE_FORMAT

class DayBlock {
    var name: String = ""
    var date: DateTime? = null

    constructor()

    constructor(year: Int, month: Int, day: Int) {
        this.date = DateTime()
        this.date!!.setDateTime(year, month, day)
        this.name = this.date?.day.toString()
    }

    fun getIntentString(): String? {
        return date?.toString(INTENT_DATE_FORMAT)
    }
}
