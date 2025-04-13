package com.trelltech.frontend.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import android.app.AlertDialog


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

        adapter = BoardAdapter(
            onBoardClick = { selectedBoard ->
                val action = BoardsFragmentDirections.actionBoardsToLists(selectedBoard.id)
                findNavController().navigate(action)
            },
            onDeleteClick = { selectedBoard ->
                val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_confirm_delete, null)
                AlertDialog.Builder(requireContext())
                    .setTitle("❌ Supprimer le board ?")
                    .setView(dialogView)
                    .setPositiveButton("Confirmer") { _, _ ->
                        com.trelltech.frontend.ui.Delete().deleteBoard(selectedBoard.id, "defaultUser") { success ->
                            requireActivity().runOnUiThread {
                                if (success) {
                                    Log.d("BoardsFragment", "🗑️ Board supprimé")
                                    refreshBoards()
                                } else {
                                    Log.e("BoardsFragment", "❌ Erreur suppression board")
                                }
                            }
                        }
                    }
                    .setNegativeButton("Annuler", null)
                    .show()
            },
            onEditClick = { selectedBoard ->
                val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_board, null)
                val nameInput = dialogView.findViewById<EditText>(R.id.editBoardName)
                val descInput = dialogView.findViewById<EditText>(R.id.editBoardDesc)

                nameInput.setText(selectedBoard.name)
                descInput.setText(selectedBoard.description)

                AlertDialog.Builder(requireContext())
                    .setTitle("✏️ Modifier le board")
                    .setView(dialogView)
                    .setPositiveButton("Enregistrer") { _, _ ->
                        val newName = nameInput.text.toString()
                        val newDesc = descInput.text.toString()

                        com.trelltech.frontend.ui.Put().updateBoard(selectedBoard.id, newName, newDesc) { success ->
                            requireActivity().runOnUiThread {
                                if (success) {
                                    Log.d("BoardsFragment", "📝 Board modifié")
                                    refreshBoards()
                                } else {
                                    Log.e("BoardsFragment", "❌ Erreur modification board")
                                }
                            }
                        }
                    }
                    .setNegativeButton("Annuler", null)
                    .show()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val fab = view.findViewById<View>(R.id.fabAddBoard)

        fab.setOnClickListener {
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_board, null)
            val nameInput = dialogView.findViewById<EditText>(R.id.editBoardName)
            val descInput = dialogView.findViewById<EditText>(R.id.editBoardDesc)

            AlertDialog.Builder(requireContext())
                .setTitle("➕ Nouveau board")
                .setView(dialogView)
                .setPositiveButton("Créer") { _, _ ->
                    val name = nameInput.text.toString()
                    val desc = descInput.text.toString()

                    com.trelltech.frontend.ui.Post().postBoard(name, desc) { success ->
                        requireActivity().runOnUiThread {
                            if (success) {
                                Log.d("BoardsFragment", "✅ Board créé")
                                // Refresh des boards (très simple pour l'instant)
                                findNavController().navigate(R.id.boardsFragment)
                            } else {
                                Log.e("BoardsFragment", "❌ Échec création board")
                            }
                        }
                    }
                }
                .setNegativeButton("Annuler", null)
                .show()
        }

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

    private fun refreshBoards() {
        getter.getBoards { jsonBoards ->
            val boardList = jsonBoards.map { boardJson ->
                Board(
                    id = boardJson.optString("id", ""),
                    name = boardJson.optString("name", "No name"),
                    creator = boardJson.optString("idMemberCreator", "Unknown"),
                    description = boardJson.optString("desc", "No description")
                )
            }
            requireActivity().runOnUiThread {
                adapter.submitList(boardList)
            }
        }
    }

}
