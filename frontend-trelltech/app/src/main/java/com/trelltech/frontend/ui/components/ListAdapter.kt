package com.trelltech.frontend.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.trelltech.frontend.R
import com.trelltech.frontend.data.models.Lists

class ListAdapter(
    private val onClick: (Lists) -> Unit,
    private val onDelete: (Lists) -> Unit,
    private val onEdit: (Lists) -> Unit
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private val items = mutableListOf<Lists>()

    fun submitList(newItems: List<Lists>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onClick(item) }
        holder.deleteIcon.setOnClickListener { onDelete(item) }
        holder.editIcon.setOnClickListener { onEdit(item) }
    }

    override fun getItemCount(): Int = items.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.listTitle)
        private val status = itemView.findViewById<TextView>(R.id.listStatus)
        val deleteIcon = itemView.findViewById<ImageView>(R.id.deleteListIcon)
        val editIcon = itemView.findViewById<ImageView>(R.id.editListIcon)

        fun bind(list: Lists) {
            title.text = list.name
            status.text = if (list.closed) "Status: Closed" else "Status: Open"
        }
    }
}
