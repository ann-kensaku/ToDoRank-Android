package jp.ann.kensaku.todorank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    private val toDoList: List<Item>,
    private val onClick: (Item) -> Unit):
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>(){


    class RecyclerViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.title_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val data = toDoList[position]
        holder.titleTextView.text = toDoList[position].title
        holder.itemView.setOnClickListener {onClick(data)}
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }
}