package jp.ann.kensaku.todorank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val toDoList: List<Item>):
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>(){

    class RecyclerViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.title_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.RecyclerViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.titleTextView.text = toDoList[position].title
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }
}