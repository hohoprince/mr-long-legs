package com.kidari.mrlonglegs

import android.app.LauncherActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    val list = ArrayList<ListItemMember>()
    val recyclerAdapter = ListItemAdapter(list)
    val TAG = "DBTest"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerview_list.layoutManager =
            LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        refreshButton.setOnClickListener {
            loadData()
        }
        recyclerview_list.adapter = recyclerAdapter
    }

    fun loadData() {
        list.clear()
        db.collection("심부름")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    list.add(toListItemMember(document))
                    recyclerAdapter.notifyDataSetChanged()
                    Log.d(TAG, "${document.id} => ${document.data["name"]}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }


    }

    fun toListItemMember(document: QueryDocumentSnapshot): ListItemMember {
        val item = ListItemMember(document["name"].toString(), document["regDate"].toString(),
            document["payment"].toString(), document["location"].toString())
        return item
    }
}