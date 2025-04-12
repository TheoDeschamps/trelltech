package com.trelltech.frontend.ui.screens

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trelltech.frontend.R
import com.trelltech.frontend.data.models.Card
import com.trelltech.frontend.data.models.Lists
import com.trelltech.frontend.ui.Get
import com.trelltech.frontend.ui.components.CardAdapter
import org.json.JSONObject
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import com.trelltech.frontend.ui.Post

class CardsFragment : Fragment() {

    private lateinit var spinner: Spinner
    private lateinit var allLists: List<Lists>
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
        spinner = view.findViewById(R.id.listSelector)

        val fab = view.findViewById<View>(R.id.fabAddCard)

        fab.setOnClickListener {
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_card, null)
            val nameInput = dialogView.findViewById<EditText>(R.id.editCardName)
            val descInput = dialogView.findViewById<EditText>(R.id.editCardDesc)

            AlertDialog.Builder(requireContext())
                .setTitle("➕ Nouvelle carte")
                .setView(dialogView)
                .setPositiveButton("Créer") { _, _ ->
                    val name = nameInput.text.toString()
                    val desc = descInput.text.toString()
                    val listId = allLists[spinner.selectedItemPosition].id

                    Post().postCard(listId, name, desc) { success ->
                        requireActivity().runOnUiThread {
                            if (success) {
                                loadCardsForList(listId)
                            } else {
                                Log.e("CardsFragment", "Erreur lors de la création de la carte")
                            }
                        }
                    }
                }
                .setNegativeButton("Annuler", null)
                .show()
        }



        getter.getLists(args.boardId) { jsonLists ->
            allLists = jsonLists.map { i ->

                Lists(
                    id = i.optString("id"),
                    name = i.optString("name"),
                    idBoard = i.optString("idBoard"),
                    closed = i.optBoolean("closed")
                )
            }

            val listNames = allLists.map { it.name }

            requireActivity().runOnUiThread {
                val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listNames)
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = spinnerAdapter

                val selectedIndex = allLists.indexOfFirst { it.id == args.listId }
                if (selectedIndex >= 0) {
                    spinner.setSelection(selectedIndex)
                    loadCardsForList(allLists[selectedIndex].id)
                }

                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val selectedListId = allLists[position].id
                        loadCardsForList(selectedListId)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }


        }

        adapter = CardAdapter(
            onClick = { card ->
                Log.d("CardsFragment", "Clicked card: ${card.name}")
            },
            onDelete = { card ->
                AlertDialog.Builder(requireContext())
                    .setTitle("❌ Supprimer la carte")
                    .setMessage("Confirmer la suppression de « ${card.name} » ?")
                    .setPositiveButton("Supprimer") { _, _ ->
                        com.trelltech.frontend.ui.Delete().deleteCard(card.id) { success ->
                            requireActivity().runOnUiThread {
                                if (success) {
                                    Log.d("CardsFragment", "✅ Carte supprimée")
                                    loadCardsForList(card.idList)
                                } else {
                                    Log.e("CardsFragment", "❌ Échec suppression carte")
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


        }

    private fun loadCardsForList(listId: String) {
        getter.getCards(listId) { jsonCards ->
            val cards = mutableListOf<Card>()

            for (i in 0 until jsonCards.size) {
                val memberArray = jsonCards[i].optJSONArray("idMembers")
                val members = mutableListOf<String>()
                val cardJson: JSONObject = jsonCards[i]
                if (memberArray != null) {
                    for (m in 0 until memberArray.length()) {
                        members.add(memberArray.getString(m))
                    }
                }

                val card = Card(
                    id = cardJson.optString("id"),
                    idList = cardJson.optString("idList"),
                    name = cardJson.optString("name"),
                    desc = cardJson.optString("desc"),
                    memberList = members
                )

                cards.add(card)

            }
            requireActivity().runOnUiThread {
                adapter.submitList(cards)
            }

        }
    }
}
