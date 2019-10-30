package jp.ann.kensaku.todorank

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import jp.ann.kensaku.todorank.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: RecyclerAdapter
    private lateinit var todoViewModel: TodoViewModel
    private var low = 0
    private var high = 0
    private var middle = 0
    private var targettext = "title"
    private var change_rank = 0
    private var itemcount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        viewAdapter = RecyclerAdapter(
            onClick = {
                MaterialDialog(this).show {
                    title(text = "todoの編集")
                    input(prefill = it.title) { dialog, text ->
                        it.title = text.toString()
                        todoViewModel.update(it)
                    }
                    positiveButton(text = "OK")
                }
            },
            onUpdate = { todoViewModel.update(it) },
            onDelete = { todoViewModel.delete(it) }
        )

        binding.recyclerView.apply {
            setHasFixedSize(true)

            adapter = viewAdapter
        }

        todoViewModel.allTodos.observe(this, Observer { todos ->
            todos?.let {
                viewAdapter.setTodos(it)
            }
        })

        binding.floatingActionButton.setOnClickListener {
            MaterialDialog(this).show {
                title(text = "todoの追加")
                input(hint = "Title") { dialog, text ->
                    itemcount = viewAdapter.itemCount
                    val newItem = Item(0, text.toString(), false, 1)
                    high = 0
                    low = itemcount+1
                    change_rank = itemcount + 1
                    targettext = newItem.title
                    todoViewModel.add(this@MainActivity, newItem, high, low)
                }
                positiveButton(text = "OK")
            }
        }
    }

    // TODO: ロジック分離のために大幅書き換え必要
    //比較結果を受け取る
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        val newItem = Item(0, targettext, false, low)
        if (resultCode == Activity.RESULT_OK &&
            requestCode == RESULT_RANK_ACTIVITY && intent != null
        ) {
            val rest = intent.extras?.getInt("ANSWER", 1)
            //より重要ならば
            if (rest == 1) {
                low = (low + high) / 2
                change_rank = (low + high) / 2
                todoViewModel.add(this@MainActivity, newItem, high, low)
            } else {
                high = (low + high) / 2
                change_rank = (low + high) / 2
                todoViewModel.add(this@MainActivity, newItem, high, low)
            }
        }
    }

    companion object {
        private const val RESULT_RANK_ACTIVITY = 1000
    }
}
