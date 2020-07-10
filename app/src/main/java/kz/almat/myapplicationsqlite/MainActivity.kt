package kz.almat.myapplicationsqlite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener  {

    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd.setOnClickListener(this)
        btnRead.setOnClickListener(this)
        btnClear.setOnClickListener(this)
        btnUpd.setOnClickListener(this)
        btnDel.setOnClickListener(this)

        dbHelper = DBHelper(this)
    }

    override fun onClick(v: View?) {

        var name = etName.text.toString()
        var email = etEmail.text.toString()
        var id = etID.text.toString()

        var database: SQLiteDatabase = dbHelper.writableDatabase

        var contentValues: ContentValues = ContentValues()

        when (v!!.id) {
            R.id.btnAdd -> {
                contentValues.put(DBHelper.KEY_NAME, name)
                contentValues.put(DBHelper.KEY_MAIL, email)

                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues)
            }
            R.id.btnRead -> {
                var cursor: Cursor = database.query(DBHelper.TABLE_CONTACTS, null,
                                            null, null, null,
                                            null, null)

                if (cursor.moveToFirst()) {
                    var idIndex = cursor.getColumnIndex(DBHelper.KEY_ID)
                    var nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME)
                    var emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL)
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                                    ", name = " + cursor.getString(nameIndex) +
                                                    ", email = " + cursor.getString(emailIndex))
                    } while (cursor.moveToNext())
                } else {
                    Log.d("mLog", "0 rows")
                }
                cursor.close()

            }
            R.id.btnClear -> {
                database.delete(DBHelper.TABLE_CONTACTS, null, null)
            }
            R.id.btnUpd -> {
                if (id.equals("")) {
                    return
                }
                contentValues.put(DBHelper.KEY_MAIL, email)
                contentValues.put(DBHelper.KEY_NAME, name)
                var updCount: Int = database.update(DBHelper.TABLE_CONTACTS, contentValues,
                                    DBHelper.KEY_ID + "= ?", arrayOf<String>(id))

                Log.d("mLog", "update rows count = " + updCount)
            }
            R.id.btnDel -> {
                if (id.equals("")) {
                    return
                }
                var delCount: Int = database.delete(DBHelper.TABLE_CONTACTS,
                                        DBHelper.KEY_ID + "= " + id, null)
                Log.d("mLog", "deleted rows count = " + delCount)
            }
        }
        dbHelper.close()
    }
}
