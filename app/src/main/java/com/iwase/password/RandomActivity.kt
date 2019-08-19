package com.iwase.password

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.widget.Toast.LENGTH_SHORT

class RandomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random)

        var number = 8
        val txtNumber = findViewById<TextView>(R.id.txtNumber)
        txtNumber.setText("8文字")

        val seekbar = findViewById<SeekBar>(R.id.seekbar)
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                    seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                number = progress + 4
                txtNumber.setText(number.toString() + "文字")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        val txtDisplay = findViewById<TextView>(R.id.txtDisplay)
        var password =""

        val btnGene = findViewById<Button>(R.id.btnGene)
        btnGene.setOnClickListener {
            password = generateID(number)
            txtDisplay.setText(password)
        }

        //生成したパスワードをコピー
        val btnCopy = findViewById<Button>(R.id.btnCopy)
        btnCopy.setOnClickListener {
            copy(password)
            Toast.makeText(this, "パスワードをコピーしました", LENGTH_SHORT).show()
        }
    }

    //ランダムパスワード生成
    fun generateID(size: Int): String {
        val swtLarge = findViewById<Switch>(R.id.swtLarge)
        val swtNum = findViewById<Switch>(R.id.swtNum)
        val swtSymbol = findViewById<Switch>(R.id.swtSymbol)
        var source = "abcdefghijklmnopqrstuvwxyz"

        if(swtLarge.isChecked){
            source = source + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        }else{}

        if(swtNum.isChecked){
            source = source + "0123456789"
        }else{}

        if(swtSymbol.isChecked){
            source = source + "!@#$%"
        }
        return (source).map { it }.shuffled().subList(0, size).joinToString("")
    }

    //コピー
    fun copy(text: String){
        var clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var clip = ClipData.newPlainText("password",text)
        clipboard.primaryClip = clip
    }
}


