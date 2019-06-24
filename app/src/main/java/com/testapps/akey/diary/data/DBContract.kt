package com.testapps.akey.diary.data

import android.provider.BaseColumns

object DBContract {
    class DiaryEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "diary"
            const val COLUMN_DATE = "date"
            const val COLUMN_TEXT = "text"
        }
    }
}
