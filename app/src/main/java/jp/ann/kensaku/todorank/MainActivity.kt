package jp.ann.kensaku.todorank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import jp.ann.kensaku.todorank.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: RecyclerAdapter
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        //データの生成
        //val itemList = mutableListOf<Item>()
        //itemList.add(Item("やること1"))
        //itemList.add(Item("やること2"))
        //itemList.add(Item("やること3"))
        //itemList.add(Item("やること4"))

        viewAdapter = RecyclerAdapter() {
            MaterialDialog(this).show {
                title(text = "todoの編集")
                input(prefill = it.title) { dialog, text ->

                }
                positiveButton(text = "OK")
            }
        }

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        binding.recyclerView.apply {
            setHasFixedSize(true)

            adapter = viewAdapter
        }

        todoViewModel.allTodos.observe(this, Observer {todos ->
            todos?.let {
                viewAdapter.setTodos(it)
            }
        })

        binding.floatingActionButton.setOnClickListener {
            MaterialDialog(this).show {
                title(text = "todoの追加")
                input(hint = "Title")
                positiveButton(text = "OK")
            }
        }
    }
}
