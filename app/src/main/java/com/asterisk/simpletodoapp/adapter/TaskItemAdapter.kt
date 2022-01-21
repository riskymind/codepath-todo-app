package com.asterisk.simpletodoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asterisk.simpletodoapp.other.OnClickListener
import com.asterisk.simpletodoapp.other.OnLongClickListener

class TaskItemAdapter(
    private val listOfItems: List<String>,
    val longClickListener: OnLongClickListener,
    val clickListener: OnClickListener
) :
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val layout = LayoutInflater.from(context)
        val taskView = layout.inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(taskView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curItem = listOfItems[position]
        holder.bind(curItem)
    }

    override fun getItemCount(): Int = listOfItems.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(android.R.id.text1)

        fun bind(curItem: String) {
            textView.text = curItem
        }

        init {
            textView.setOnLongClickListener {
                longClickListener.onItemLongClick(adapterPosition)
                true
            }

            textView.setOnClickListener {
                clickListener.onItemClicked(adapterPosition)
            }
        }
    }
}