package jp.ann.kensaku.todorank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import jp.ann.kensaku.todorank.databinding.ListItemBinding

class RecyclerAdapter(
    private val toDoList: List<Item>,
    private val onClick: (Item) -> Unit
) :
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {


    class RecyclerViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)

        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val data = toDoList[position]
        holder.binding.data = data
        holder.itemView.setOnClickListener { onClick(data) }
        holder.binding.checkBox.setOnClickListener(View.OnClickListener {
            //処理を追加
        })
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }
}