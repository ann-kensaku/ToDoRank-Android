package jp.ann.kensaku.todorank

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //データの生成
        val itemList = mutableListOf<Item>()
        itemList.add(Item("やること1"))
        itemList.add(Item("やること2"))
        itemList.add(Item("やること3"))
        itemList.add(Item("やること4"))

        viewAdapter = RecyclerAdapter(itemList) {
            MaterialDialog(this).show {
                title(text = "todoの編集")
                input(prefill = it.title)
                positiveButton(text = "OK")
            }
        }

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply{
            setHasFixedSize(true)

            adapter = viewAdapter
        }
    }
}
