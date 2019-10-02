package jp.ann.kensaku.todorank

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        setSupportActionBar(binding.toolbar)
        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        viewAdapter = RecyclerAdapter({
            MaterialDialog(this).show {
                title(text = "todoの編集")
                input(prefill = it.title) { dialog, text ->
                    it.title = text.toString()
                    todoViewModel.update(it)
                }
                positiveButton(text = "OK")
            }
        },  {todoViewModel.update(it)
        },  {todoViewModel.delete(it)
        })



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
                input(hint = "Title") { dialog, text ->
                    todoViewModel.insert(Item(0, text.toString(), false))
                }
                positiveButton(text = "OK")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_done -> {
                MaterialDialog(this).show {
                    title(text = "チェック済み項目の削除")
                    positiveButton(text = "削除") {
                        todoViewModel.deleteDone(true)
                    }
                }
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
