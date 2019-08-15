package jp.ann.kensaku.todorank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    //var myDataset: Array<String> = Array<String>(13, {i -> i.toString()})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //データの生成
        val itemList = mutableListOf<Item>()
        itemList.add(Item("やること1"))
        itemList.add(Item("やること2"))
        itemList.add(Item("やること3"))
        itemList.add(Item("やること4"))

        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerAdapter(itemList)

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply{
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }
    }
}
