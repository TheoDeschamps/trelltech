package com.trelltech.frontend.ui.screens

import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trelltech.frontend.R
import com.trelltech.frontend.ui.components.BoardAdapter
import com.trelltech.frontend.data.models.Board

class BoardsFragments : Fragment() {

    private lateinit var adapter: BoardAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerBoards)

        adapter = BoardAdapter { selectedBoard ->
            // action quand on clique sur une board (ex: navigation)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Simule des boards (à remplacer par l’API plus tard)
        val mockBoards = listOf(
            Board("01", "eat caca", "Alice", "short desc"),
            Board("02", "eat pipi", "mark", "short desc"),
        )

        adapter.submitList(mockBoards)
    }
}
