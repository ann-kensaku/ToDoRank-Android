package jp.ann.kensaku.todorank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

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

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)

        viewAdapter = RecyclerAdapter(itemList) {
            MaterialDialog(this).show {
                title(text = "todoの編集")
                input(prefill = it.title)
                positiveButton(text = "OK")
            }
        }

        binding.recyclerView.apply {
            setHasFixedSize(true)

            adapter = viewAdapter
        }


        binding.floatingActionButton.setOnClickListener{
            MaterialDialog(this).show {
                title(text = "todoの追加")
                input(hint = "Title")
                positiveButton(text = "OK")
            }
        }
    }
}
