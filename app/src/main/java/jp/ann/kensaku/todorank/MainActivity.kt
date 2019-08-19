package jp.ann.kensaku.todorank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView


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
            val intent = Intent(applicationContext, ItemEditActivity::class.java)
            intent.putExtra("title", it.title)
            startActivity(intent)
        }

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply{
            setHasFixedSize(true)

            adapter = viewAdapter
        }

        viewAdapter.setOnItemClickListener(object: RecyclerAdapter.OnItemClickListener {

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onClick(view: View, data: Item) {
                val intent = Intent(applicationContext, ItemEditActivity::class.java)
                intent.putExtra("title", data.title)
                startActivity(intent)
            }

        })
    }
}
