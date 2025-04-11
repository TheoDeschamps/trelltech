package com.trelltech.frontend.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trelltech.frontend.R
import com.trelltech.frontend.data.models.Lists
import com.trelltech.frontend.ui.Get
import com.trelltech.frontend.ui.components.ListAdapter
import org.json.JSONObject

class ListsFragment : Fragment() {

    private lateinit var spinner: Spinner
    private lateinit var allBoards: List<JSONObject>
    private lateinit var adapter: ListAdapter
    private val getter = Get()
    private val args: ListsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerLists)
        spinner = view.findViewById(R.id.boardSelector)

        adapter = ListAdapter { selectedList ->
            val action = ListsFragmentDirections.actionListsToCards(
                selectedList.id,
                selectedList.idBoard
            )
            findNavController().navigate(action)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        loadBoardsAndSetupSpinner()
    }

    private fun loadBoardsAndSetupSpinner() {
        getter.getBoards { boards ->
            allBoards = boards
            val boardNames = boards.map { it.optString("name", "Unnamed") }

            requireActivity().runOnUiThread {
                val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, boardNames)
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = spinnerAdapter

                val selectedIndex = boards.indexOfFirst { it.optString("id") == args.boardId }
                if (selectedIndex >= 0) {
                    spinner.setSelection(selectedIndex)
                    loadListsForBoard(boards[selectedIndex].optString("id"))
                }

                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val selectedBoardId = allBoards[position].optString("id")
                        loadListsForBoard(selectedBoardId)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }
    }

    private fun loadListsForBoard(boardId: String) {
        getter.getLists(boardId) { jsonLists ->
            val listModels = jsonLists.map { listJson ->
                Lists(
                    id = listJson.optString("id", ""),
                    name = listJson.optString("name", "Unnamed"),
                    idBoard = listJson.optString("idBoard", ""),
                    closed = listJson.optBoolean("closed", false)
                )
            }

            requireActivity().runOnUiThread {
                adapter.submitList(listModels)
            }
        }
    }
}
