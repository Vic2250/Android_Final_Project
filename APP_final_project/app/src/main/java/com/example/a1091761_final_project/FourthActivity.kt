package com.example.a1091761_final_project

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.ArrayAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class FourthActivity : AppCompatActivity() {

    private lateinit var sp1: Spinner
    private lateinit var drink_num: TextView
    private lateinit var listView: ListView
    private lateinit var food1: RadioButton
    private lateinit var food2: RadioButton
    private lateinit var food3: RadioButton
    private lateinit var finish2: Button

    private lateinit var tv_progress: TextView
    private lateinit var progressBar2: ProgressBar
    private lateinit var ll_progress:LinearLayout

    private lateinit var selectDrink: String
    private lateinit var film_select: String


    private fun running()
    {
        tv_progress = findViewById(R.id.tv_progress)
        progressBar2 = findViewById(R.id.progressBar2)
        ll_progress = findViewById(R.id.ll_progress)

        //初始化進度條
        progressBar2.progress = 0
        tv_progress.text = "0%"
        //顯示進度條
        ll_progress.visibility = View.VISIBLE
        GlobalScope.launch ( Dispatchers.Main ) {
            var progress = 0
            //建立迴圈執行一百次共延長五秒
            while (progress < 100) {
                //執行續延遲0.2秒後執行
                delay(50)
                //執行進度更新
                progressBar2.progress = progress
                tv_progress.text = "${progress}%"
                //計數加一
                progress++
            }
            ll_progress.visibility = View.GONE
            next_page()
        }
    }

    private fun next_page()
    {
        val b = Bundle()
        val intent_fifth = Intent(this, FifthActivity::class.java)
        intent_fifth.putExtras(b)
        startActivityForResult(intent_fifth, 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        setupViews()
    }

    //第四個頁面內容
    private fun setupViews() {

        intent?.extras?.let {
            findViewById<TextView>(R.id.thirA_text).text = "${it.getString("Key1")}"
        }

        drink_num = findViewById(R.id.drink_text2)
        food1 = findViewById(R.id.food1)
        food2 = findViewById(R.id.food2)
        food3 = findViewById(R.id.food3)

        setupSpinner()
        setupListView()
        setupSubmitButton()
    }

    //製作電影Spinner
    private fun setupSpinner() {
        sp1 = findViewById(R.id.sp)
        val adapter = ArrayAdapter.createFromResource(this, R.array.film_name, android.R.layout.simple_list_item_1)


        sp1.adapter = adapter
        sp1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val s = sp1.selectedItem.toString()
                film_select = s
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    //製作飲料表
    private fun setupListView() {
        val array = resources.obtainTypedArray(R.array.image_list)
        val item = ArrayList<Item>()
        val names = arrayOf("可樂", "珍珠奶茶", "柳橙汁", "檸檬茶")
        for (i in 0 until array.length()) {
            val photo = array.getResourceId(i, 0)
            val name = names[i]
            item.add(Item(photo, name))
        }

        array.recycle()

        listView = findViewById(R.id.drink_list)
        listView.adapter = MyAdapter(this, item, R.layout.adapter_horizontal)
        listView.setOnItemClickListener { parent, view, position, id ->
            val drink = names[position]
            drink_num.text = "${drink}"
            selectDrink = drink
        }
    }

    private fun setupSubmitButton() {

        finish2 = findViewById(R.id.finish2)

        var details = ""

        val food_name = when {
            food1.isChecked -> "爆米花"
            food2.isChecked -> "熱狗堡"
            else -> "吉拿棒"
        }
        details += "購買食物：${food_name}\n\n"




        finish2.setOnClickListener {
            //建立AlertDialog 物件

            AlertDialog.Builder(this)
                .setTitle("請再次確認訂單內容")
                .setMessage("電影名稱：" + film_select + "\n\n" + details + "購買飲料：" + selectDrink)
                .setNegativeButton("取消") { dialog, which ->
                    Toast.makeText(this, "再次確認", Toast.LENGTH_SHORT).show()}
                .setPositiveButton("確定") { dialog, which -> running()}.show()

        }
    }
}

