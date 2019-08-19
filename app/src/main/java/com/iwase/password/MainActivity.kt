package com.iwase.password

import android.content.*
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_SHORT

class MainActivity : AppCompatActivity(), AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //新規ボタン
        val btnNew = findViewById(R.id.btnNew) as Button
        btnNew.setOnClickListener {
            val intent = Intent(this,InsertActivity::class.java)
            startActivity(intent)
        }
        listShow()
    }

    //遷移先から戻ってきた時用
    override fun onStart(){
        super.onStart()
        listShow()
    }

    //クリック
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
        val txtSite =view?.findViewById<TextView>(R.id.txtSite)
        val intent = Intent(this,ChangeActivity::class.java)
        intent.putExtra("site",txtSite?.text.toString())
        startActivity(intent)
    }

    //長押し
    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
        val database = DBHelper(this).readableDatabase
        val item = parent?.getItemAtPosition(position).toString()
        var clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val cursor = database.rawQuery("select pass from passt where site = '${item}';", null)
        cursor.moveToFirst()
        val pass = cursor.getString(cursor.getColumnIndex("pass"))
        var clip = ClipData.newPlainText("password", pass)
        clipboard.primaryClip = clip
        Toast.makeText(this, "パスワードをコピーしました。", LENGTH_SHORT).show()
        return true
    }

    //リスト表示
    fun listShow(){
        val sites = queryPassTable(this)
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = ArrayAdapter<String>(this, R.layout.list_row, R.id.txtSite, sites)
        listView.setOnItemClickListener(this)
        listView.setOnItemLongClickListener(this)
    }
}