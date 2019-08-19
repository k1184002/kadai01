package com.iwase.password

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

class ChangeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change)

        val edtSiteC = findViewById<EditText>(R.id.edtSiteC)
        val edtAccC = findViewById<EditText>(R.id.edtAccC)
        val edtPassC = findViewById<EditText>(R.id.edtPassC)

        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnChange = findViewById<Button>(R.id.btnChange)

        val site = intent.getStringExtra("site")
        edtSiteC.setText(site)

        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from passt where site ='${site}';",null)
        cursor.moveToFirst()
        val account = cursor.getString(cursor.getColumnIndex("account"))
        val pass = cursor.getString(cursor.getColumnIndex("pass"))

        edtAccC.setText(account)
        edtPassC.setText(pass)

        //パスワード生成ボタン
        val btn2 = findViewById(R.id.btn2) as Button
        btn2.setOnClickListener {
            val intent = Intent(this,RandomActivity::class.java)
            startActivity(intent)
        }

        //削除ボタン
        btnDelete.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setMessage("本当に削除しますか？")
                setPositiveButton("削除する", DialogInterface.OnClickListener { _, _ ->
                    edtSiteC.setText("")
                    edtSiteC.setText(site)

                    db?.delete("passt","site=?", arrayOf(site))
                    edtSiteC.setText("")
                    edtAccC.setText("")
                    edtPassC.setText("")
                    Toast.makeText(context,"削除しました。",LENGTH_SHORT).show()
                })
                setNegativeButton("キャンセル", null)
                show()
            }
        }

        //変更ボタン
        btnChange.setOnClickListener {
            var isValid = true
            if(edtSiteC.text.toString().isEmpty()){
                edtSiteC.error = "入力されていません"
                isValid = false
            }
            if(edtAccC.text.toString().isEmpty()){
                edtAccC.error = "入力されていません"
                isValid = false
            }
            if(edtPassC.text.toString().isEmpty()){
                edtPassC.error = "入力されていません"
                isValid = false
            }
            if(isValid) {
                    db.use {
                        val update = ContentValues().apply {
                            put("site", edtSiteC.text.toString())
                            put("account", edtAccC.text.toString())
                            put("pass", edtPassC.text.toString())
                        }
                        db.update("passt", update,"site=? and account=? and pass=?",
                                arrayOf(site,account,pass))
                    }
                    Toast.makeText(this, "変更しました。", LENGTH_SHORT).show()
            }else{ }
        }
    }
}