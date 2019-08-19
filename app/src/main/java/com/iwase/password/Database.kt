package com.iwase.password

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "PDB",null,1){


    override fun onCreate(db: SQLiteDatabase?){
        db?.execSQL("CREATE TABLE passt(" + "site TEXT PRIMARY KEY," + "account TEXT NOT NULL,"+"pass TEXT NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}

//全件のサイト名を取り出す
fun queryPassTable(context: Context): List<String>{
    val database = DBHelper(context).readableDatabase
    val cursor = database.query(
            "passt",null,null,null,null,null,null)

    val passt = mutableListOf<String>()
    cursor.use{
        while(cursor.moveToNext()){
            val site = cursor.getString(cursor.getColumnIndex("site"))
            passt.add(site)
        }
    }
    database.close()
    return passt
}

//サイト名が存在するかチェック
fun getCount(context: Context,site: String): Int {
    var c: Cursor? = null
    val db = DBHelper(context).readableDatabase
    try {
        val query = "select count(*) from passt where site = ?"
        c = db.rawQuery(query, arrayOf(site))
        return if (c!!.moveToFirst()) {
            c!!.getInt(0)
        } else 0
    } finally {
        if (c != null) {
            c!!.close()
        }
        if (db != null) {
            db.close()
        }
    }
}