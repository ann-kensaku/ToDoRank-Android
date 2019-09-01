package jp.ann.kensaku.todorank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.RecyclerView
import jp.ann.kensaku.todorank.databinding.ListItemBinding

class RecyclerAdapter(
    private val toDoList: List<Item>,
    private val onClick: (Item) -> Unit
) :
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {


    class RecyclerViewHolder(var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val checkBox: CheckBox = binding.root.findViewById(R.id.check_box)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)

        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val data = toDoList[position]
        holder.binding.setData(data)
        holder.itemView.setOnClickListener { onClick(data) }
        holder.checkBox.setOnClickListener(View.OnClickListener {
            //処理を追加
        })
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }
}