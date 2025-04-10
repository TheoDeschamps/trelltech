package com.trelltech.frontend.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trelltech.frontend.R
import com.trelltech.frontend.data.models.Lists
import com.trelltech.frontend.ui.Get
import com.trelltech.frontend.ui.components.ListAdapter
import com.trelltech.frontend.ui.screens.ListsFragmentArgs.Companion.fromBundle
import org.json.JSONObject


class ListsFragment : Fragment() {

    private val args: ListsFragmentArgs by navArgs()
    private lateinit var adapter: ListAdapter
    private val getter = Get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerLists)
        adapter = ListAdapter { selectedList ->
            val action = ListsFragmentDirections.actionListsToCards(selectedList.id)
            findNavController().navigate(action)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        Log.d("ListsFragment", "Fetching lists for boardId: ${args.boardId}")

        getter.getLists(args.boardId) { jsonLists ->
            val listModels = mutableListOf<Lists>()
            for (i in 0 until jsonLists.size) {
                val listJson: JSONObject = jsonLists[i]

                val listModel = Lists(
                    id = listJson.optString("id", ""),
                    name = listJson.optString("name", "Unnamed"),
                    idBoard = listJson.optString("idBoard", ""),
                    closed = listJson.optBoolean("closed", false)
                )

                listModels.add(listModel)
            }

            requireActivity().runOnUiThread {
                adapter.submitList(listModels)
                Log.d("ListsFragment", "Loaded ${listModels.size} lists")
            }
        }
    }
}
