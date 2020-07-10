package kz.almat.myapplicationsqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(
    context: Context?
) : SQLiteOpenHelper(context, "My_DataBase", null, 1) {

    companion object {

        val KEY_ID = "_id"
        val KEY_NAME = "name"
        val KEY_MAIL = "mail"
        val TABLE_CONTACTS = "contacts"

    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID +
                " integer primary key," + KEY_NAME + " text," + KEY_MAIL + " text" + ")")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists " + TABLE_CONTACTS)
        onCreate(db)
    }
}