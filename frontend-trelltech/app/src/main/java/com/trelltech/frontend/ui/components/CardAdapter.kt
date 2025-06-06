package com.trelltech.frontend.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.trelltech.frontend.R
import com.trelltech.frontend.data.models.Card

class CardAdapter(
    private val onClick: (Card) -> Unit,
    private val onDelete: (Card) -> Unit,
    private val onEdit: (Card) -> Unit
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private val items = mutableListOf<Card>()

    fun submitList(newItems: List<Card>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.cardTitle)
        private val desc = itemView.findViewById<TextView>(R.id.cardDesc)
        private val deleteIcon = itemView.findViewById<ImageView>(R.id.deleteCardIcon)
        private val editIcon = itemView.findViewById<ImageView>(R.id.editCardIcon)

        fun bind(card: Card) {
            name.text = card.name
            desc.text = card.desc

            itemView.setOnClickListener {
                onClick(card)
            }

            deleteIcon.setOnClickListener {
                onDelete(card)
            }

            editIcon.setOnClickListener {
                onEdit(card)
            }
        }
    }
}
