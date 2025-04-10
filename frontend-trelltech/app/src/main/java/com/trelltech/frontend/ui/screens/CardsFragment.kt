package com.trelltech.frontend.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trelltech.frontend.R
import com.trelltech.frontend.data.models.Card
import com.trelltech.frontend.ui.Get
import com.trelltech.frontend.ui.components.CardAdapter
import org.json.JSONObject

class CardsFragment : Fragment() {

    private val args: CardsFragmentArgs by navArgs()
    private lateinit var adapter: CardAdapter
    private val getter = Get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerCards)
        adapter = CardAdapter { card ->
            Log.d("CardsFragment", "Clicked card: ${card.name}")
            // You can navigate or show card detail here
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        Log.d("CardsFragment", "Fetching cards for listId: ${args.listId}")

        getter.getCards(args.listId) { jsonCards ->
            val cards = mutableListOf<Card>()

            for (i in 0 until jsonCards.size) {
                val cardJson: JSONObject = jsonCards[i]

                val memberList = mutableListOf<String>()
                val memberArray = cardJson.optJSONArray("idMembers")
                if (memberArray != null) {
                    for (m in 0 until memberArray.length()) {
                        memberList.add(memberArray.getString(m))
                    }
                }

                val card = Card(
                    id = cardJson.optString("id", ""),
                    idList = cardJson.optString("idList", ""),
                    name = cardJson.optString("name", "No title"),
                    desc = cardJson.optString("desc", "No description"),
                    memberList = memberList
                )

                cards.add(card)
                Log.d("CardsFragment", "🃏 Card: ${card.name} | Members: $memberList")
            }

            requireActivity().runOnUiThread {
                adapter.submitList(cards)
            }
        }
    }
}
