package com.trelltech.frontend.ui.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trelltech.frontend.R
import com.trelltech.frontend.data.models.Board

class BoardAdapter(
    private val onBoardClick: (Board) -> Unit,
    private val onDeleteClick: (Board) -> Unit
) : ListAdapter<Board, BoardAdapter.BoardViewHolder>(DIFF) {

    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(board: Board) {
            itemView.findViewById<TextView>(R.id.title).text = board.name
            itemView.findViewById<TextView>(R.id.creator).text = board.creator
            itemView.findViewById<TextView>(R.id.description).text = board.description

            itemView.setOnClickListener {
                onBoardClick(board)
            }

            itemView.findViewById<ImageView>(R.id.btnDeleteBoard).setOnClickListener {
                onDeleteClick(board)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_board, parent, false)
        return BoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Board>() {
            override fun areItemsTheSame(oldItem: Board, newItem: Board) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Board, newItem: Board) = oldItem == newItem
        }
    }
}
