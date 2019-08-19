package com.iwase.password

import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT


class InsertActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        val edtSite = findViewById(R.id.edtSite) as EditText
        val edtAcc = findViewById(R.id.edtAcc) as EditText
        val edtPass = findViewById(R.id.edtPass) as EditText

        val dbhelper = DBHelper(this)
        val db = dbhelper.writableDatabase

        //パスワード生成ボタン
        val btn = findViewById(R.id.btn) as Button
        btn.setOnClickListener {
            val intent = Intent(this,RandomActivity::class.java)
            startActivity(intent)
       }

        //保存ボタン
        val btnInsert = findViewById(R.id.btnInsert) as Button
        btnInsert.setOnClickListener {
            val dbhelper = DBHelper(this)
            val db = dbhelper.writableDatabase
            var isValid = true
            if(edtSite.text.toString().isEmpty()){
                edtSite.error = "入力されていません"
                isValid = false
            }
            if(edtAcc.text.toString().isEmpty()){
                edtAcc.error = "入力されていません"
                isValid = false
            }
            if(edtPass.text.toString().isEmpty()){
                edtPass.error = "入力されていません"
                isValid = false
            }
            if(isValid){
                if (getCount(this,edtSite.text.toString()) == 0) {

                    db.use {
                        val record = ContentValues().apply {
                            put("site", edtSite.text.toString())
                            put("account", edtAcc.text.toString())
                            put("pass", edtPass.text.toString())
                        }
                        db.insert("passt", null, record)
                    }
                    db.close()
                    edtSite.setText("")
                    edtAcc.setText("")
                    edtPass.setText("")
                    Toast.makeText(this, "登録しました", LENGTH_SHORT).show()
                }else{
                    edtSite.error = "既に登録されています"
                }
            }
        }
    }
}

