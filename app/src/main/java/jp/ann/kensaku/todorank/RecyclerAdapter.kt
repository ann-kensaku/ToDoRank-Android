package jp.ann.kensaku.todorank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    private val toDoList: List<Item>,
    private val onClick: (Item) -> Unit
) :
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {


    class RecyclerViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.title_text)
        val checkBox: CheckBox = view.findViewById(R.id.check_box)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val data = toDoList[position]
        holder.titleTextView.text = toDoList[position].title
        holder.itemView.setOnClickListener { onClick(data) }
        holder.checkBox.setOnClickListener(View.OnClickListener {
            //処理を追加
        })
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }
}