package com.kidari.mrlonglegs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.android.synthetic.main.activity_registration_list.*
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

        recyclerAdapter.setItemClickListener(object : ListItemAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                val item = list[position]
                val intent = Intent(context, DetailsActivity::class.java).apply {
                    putExtra("id", item.list_item_id)
                }
                startActivity(intent)
            }
        })
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
        db.collection("심부름")
            .get()
            .addOnSuccessListener { result ->
                list.clear()
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
        return ListItemMember(document.id, document["title"].toString(), document["regDay"].toString(),
            document["payment"].toString(), document["location"].toString())
    }
}