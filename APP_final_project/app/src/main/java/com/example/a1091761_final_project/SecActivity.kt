package com.example.a1091761_final_project

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class  SecActivity : AppCompatActivity() {

    private var angle = 0f
    private lateinit var btn_finish:Button

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //識別返回對象及執行結果
        if (requestCode == 0 && resultCode == RESULT_OK) {
            //取得影像並顯示於ImageView
            val image = data?.extras?.get("data") ?: return
            findViewById<ImageView>(R.id.photo).setImageBitmap(image as Bitmap)
        }
    }

    private fun page_fourth()
    {
        val b = Bundle()
        b.putString("Key1", findViewById<EditText>(R.id.user_id2).text.toString())
        val intent_fourth = Intent(this, FourthActivity::class.java)
        intent_fourth.putExtras(b)
        startActivityForResult(intent_fourth, 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec)

        findViewById<Button>(R.id.btn_photo).setOnClickListener {
            //建立一個要進行影像獲取的Intent物件
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            //用 try-catch 避免例外發生，若產生則顯示 Toast
            try {
                startActivityForResult(intent, 0)
            } catch (e:ActivityNotFoundException) {
                Toast.makeText(this, "此裝置無相機應用程式", Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<Button>(R.id.btn_rotate).setOnClickListener {
            angle += 90f    //原本角度再加上 90 度
            findViewById<ImageView>(R.id.photo).rotation = angle    //使 ImageView 旋轉
        }


        btn_finish = findViewById(R.id.finish)
        //點擊登入進入第二個畫面
        btn_finish.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("請注意")
                .setMessage("資料送出後無法更改")
                .setNegativeButton("取消") { dialog, which ->
                    Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show()}
                .setPositiveButton("送出") { dialog, which -> page_fourth()}.show()

        }
    }

}