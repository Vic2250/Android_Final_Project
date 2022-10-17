package com.example.a1091761_final_project

import android.content.ClipData
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(context: Context, data: ArrayList<Item>, private val layout: Int) : ArrayAdapter<Item>(context, layout, data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //依據傳入的 Layout 建立畫面
        val view = View.inflate(parent.context, layout, null)
        //依據 position 取得對應的資料內容
        val item = getItem(position) ?: return view
        //將圖片指派給 ImageView 呈現
        val img_photo = view.findViewById<ImageView>(R.id.img_photo)
        img_photo.setImageResource(item.photo)
        val tv_msg = view.findViewById<TextView>(R.id.tv_msg)
        tv_msg.text = item.name
        //回傳此項目的畫面
        return view
    }
}

data class Item(
    val photo: Int,
    val name: String,
)