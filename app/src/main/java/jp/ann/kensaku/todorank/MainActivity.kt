package jp.ann.kensaku.todorank

import android.app.Activity
import android.content.Intent
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
    private val RESULT_RUNKACTIVITY = 1000
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
        }, {
            todoViewModel.update(it)
        }, {
            itemcount = viewAdapter.itemCount
            //削除する項目のランクを取得
            val delete_rank = it.rank
            todoViewModel.delete(it)
            for(i in delete_rank..itemcount) {

                val temp_item = todoViewModel.allTodos.value?.get(i-1)
                if (temp_item != null) {
                    temp_item.rank = temp_item.rank - 1
                    todoViewModel.update(temp_item)
                }
            }
        })



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
                    //初めての追加ならば、そのままリストに加える
                    if (itemcount == 0) {
                        todoViewModel.insert(newItem)
                    } else {
                        val intent = Intent(applicationContext, RankActivity::class.java)
                        targettext = newItem.title
                        high = 0
                        low = itemcount + 1
                        //　これ以降のものはランクを変える必要がある
                        change_rank = itemcount + 1

                        middle = ((low + high) / 2) as Int
                        //比較対象のタイトルを取り出す
                        val comparetext = todoViewModel.allTodos.value?.get(middle-1)?.title

                        intent.putExtra("NEWITEM", targettext)
                        intent.putExtra("COMPAREITEM", comparetext)
                        //startActivity(intent)
                        startActivityForResult(intent, RESULT_RUNKACTIVITY)
                    }
                    positiveButton(text = "OK")
                }
            }
        }
    }

    //比較結果を受け取る
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if(resultCode == Activity.RESULT_OK &&
                requestCode == RESULT_RUNKACTIVITY && intent != null) {
            val rest = intent.extras?.getInt("ANSWER", 1)
            //より重要ならば
            if(rest == 1) {

                low = middle
                change_rank = middle
            } else {
                high = middle
            }
            if (low - high > 1) {

                middle = ((low + high) / 2) as Int

                val comparetext = todoViewModel.allTodos.value?.get(middle-1)?.title
                val intent = Intent(applicationContext, RankActivity::class.java)

                intent.putExtra("NEWITEM", targettext)
                intent.putExtra("COMPAREITEM", comparetext)
                //startActivity(intent)
                startActivityForResult(intent, RESULT_RUNKACTIVITY)

            } else {
                val newItem = Item(0,targettext, false, low)
                for(i in low..itemcount) {

                    val temp_item = todoViewModel.allTodos.value?.get(i-1)
                    if (temp_item != null) {
                        temp_item.rank = temp_item.rank + 1
                        todoViewModel.update(temp_item)
                    }
                }
                //新しい項目を追加する
                todoViewModel.insert(newItem)
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
