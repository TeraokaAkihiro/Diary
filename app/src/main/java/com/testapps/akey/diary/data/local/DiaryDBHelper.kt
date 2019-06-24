package com.testapps.akey.diary.data.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.testapps.akey.diary.data.DBContract
import com.testapps.akey.diary.data.model.DiaryModel

const val TAG = "DiaryDBHelper"

class DiaryDBHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertDiary(diary: DiaryModel): Boolean {
        val db = writableDatabase
        val value = ContentValues()
        value.put(DBContract.DiaryEntry.COLUMN_DATE, diary.date)
        value.put(DBContract.DiaryEntry.COLUMN_TEXT, diary.text)

        var rows = db.insert(DBContract.DiaryEntry.TABLE_NAME, null, value)

        Log.d(TAG, "insert date:" + diary.date + ", text:" + diary.text + ", rows:" + rows)
        return 0 < rows
    }

    @Throws(SQLiteConstraintException::class)
    fun updateDiary(diary: DiaryModel): Boolean {
        val db = writableDatabase
        val value = ContentValues()
        value.put(DBContract.DiaryEntry.COLUMN_TEXT, diary.text)

        val whereClause = "${DBContract.DiaryEntry.COLUMN_DATE}=?"
        val whereArgs = arrayOf(diary.date)

        var rows = db.update(DBContract.DiaryEntry.TABLE_NAME, value, whereClause, whereArgs)

        Log.d(TAG, "update date:" + diary.date + ", text:" + diary.text + ", rows:" + rows)
        return 0 < rows
    }

    fun readDiary(date: String): String {
        val db = writableDatabase
        var cursor: Cursor?
        try {
            cursor = db.rawQuery(
                "SELECT * FROM " + DBContract.DiaryEntry.TABLE_NAME +
                        " WHERE " + DBContract.DiaryEntry.COLUMN_DATE + "=" + date, null
            )
        } catch (e: SQLiteException) {
            return ""
        }

        var text: String = ""
        if (cursor!!.moveToFirst()) {
            text = cursor.getString(cursor.getColumnIndex(DBContract.DiaryEntry.COLUMN_TEXT))
        }
        cursor.close()
        return text
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Diary.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.DiaryEntry.TABLE_NAME + " (" +
                    DBContract.DiaryEntry.COLUMN_DATE + " INT PRIMARY KEY," +
                    DBContract.DiaryEntry.COLUMN_TEXT + " TEXT)"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.DiaryEntry.TABLE_NAME
    }
}
