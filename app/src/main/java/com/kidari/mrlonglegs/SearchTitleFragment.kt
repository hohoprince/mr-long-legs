package com.kidari.mrlonglegs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_search_title.*

class SearchTitleFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    val items = ArrayList<SearchItemMember>()
    var stringitems = ""
    val recyclerAdapter = SearchItemAdapter(items)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_search_title, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchTitleRecyclerView.layoutManager =
            LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)

        recyclerAdapter.setItemClickListener(object : SearchItemAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, DetailsActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        search_title_btn.setOnClickListener {
            val title = search_title_edit.text.toString()
            loadStringData(title)
            if(title == ""){
                loadData()
            }
        }


        searchTitleRecyclerView.adapter = recyclerAdapter
    }

    //여기서 스트링 구별
    fun loadData() {
        db.collection("심부름")
            .get()
            .addOnSuccessListener { result ->
                items.clear()
                for (document in result) {
                    items.add(toListItemMember(document))
                    recyclerAdapter.notifyDataSetChanged()
                    Log.d(TAG, "${document.id} => ${document.data["name"]}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun loadStringData(string: String) {
        db.collection("심부름")
            .get()
            .addOnSuccessListener { result ->
                items.clear()
                for (document in result) {
                    stringitems = document.data["title"].toString()
                    if(stringitems.contains(string)){
                        items.add(toListItemMember(document))
                        recyclerAdapter.notifyDataSetChanged()
                        Log.d(TAG, "${document.id} => ${document.data["name"]}")
                    }
                    else{
                        Log.d(TAG, "${document.id} => ${document.data["name"]}")
                    }
                    Log.d(TAG, "${document.id} => ${document.data["name"]}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun toListItemMember(document: QueryDocumentSnapshot): SearchItemMember {
        return SearchItemMember(document["title"].toString(), document["regDay"].toString(),
            document["payment"].toString(), document["location"].toString())
    }

}