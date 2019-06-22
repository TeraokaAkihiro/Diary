package com.testapps.akey.diary

import java.text.SimpleDateFormat
import java.util.*

class DayBlock {
    var name: String = ""
    var date: Date? = null
    var dateString: String = ""

    constructor()

    constructor(year: Int, month: Int, day: Int) {
        var calendar = GregorianCalendar()
        calendar.set(year, month, day)
        this.name = calendar.get(Calendar.DATE).toString()
        this.date = calendar.time
        this.dateString = SimpleDateFormat(DATE_FORMAT).format(date)
    }
}