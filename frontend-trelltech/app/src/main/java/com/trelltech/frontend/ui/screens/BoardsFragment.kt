package com.trelltech.frontend.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trelltech.frontend.R
import com.trelltech.frontend.ui.components.BoardAdapter
import com.trelltech.frontend.data.models.Board
import org.json.JSONObject
import com.trelltech.frontend.ui.Get
import com.trelltech.frontend.data.models.Lists

class BoardsFragment : Fragment() {

    private lateinit var adapter: BoardAdapter
    private val getter = Get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_boards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerBoards)

        adapter = BoardAdapter { selectedBoard ->
            val action = BoardsFragmentDirections.actionBoardsToLists(selectedBoard.id)
            findNavController().navigate(action)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        getter.getBoards { jsonBoards ->
            val boardList = mutableListOf<Board>()
            for (i in 0 until jsonBoards.size) {
                val boardJson: JSONObject = jsonBoards[i]

                val board = Board(
                    id = boardJson.optString("id", ""),
                    name = boardJson.optString("name", "No name"),
                    creator = boardJson.optString("idMemberCreator", "Unknown"),
                    description = boardJson.optString("desc", "No description")
                )
                boardList.add(board)

                getter.getLists(board.id) { jsonLists ->
                    for (j in 0 until jsonLists.size) {
                        val listJson = jsonLists[j]

                        val listObj = Lists(
                            id = listJson.optString("id", ""),
                            name = listJson.optString("name", "Unnamed list"),
                            idBoard = listJson.optString("idBoard", ""),
                            closed = listJson.optBoolean("closed", false)
                        )

                        Log.d("BoardsFragment", "📋 List: ${listObj.name}")

                        getter.getCards(listObj.id) { jsonCards ->
                            for (k in 0 until jsonCards.size) {
                                val cardJson = jsonCards[k]

                                val memberList = mutableListOf<String>()
                                val memberArray = cardJson.optJSONArray("idMembers")
                                if (memberArray != null) {
                                    for (m in 0 until memberArray.length()) {
                                        memberList.add(memberArray.getString(m))
                                    }
                                }

                                val cardObj = com.trelltech.frontend.data.models.Card(
                                    id = cardJson.optString("id", ""),
                                    idList = cardJson.optString("idList", ""),
                                    name = cardJson.optString("name", "Unnamed card"),
                                    desc = cardJson.optString("desc", ""),
                                    memberList = memberList
                                )

                                Log.d("BoardsFragment", "🃏 Card: ${cardObj.name}")
                            }
                        }
                    }
                }
            }

            requireActivity().runOnUiThread {
                Log.d("BoardsFragments", "Board list: $boardList")
                adapter.submitList(boardList)
            }
        }
    }
}
