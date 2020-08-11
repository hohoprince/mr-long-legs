package com.kidari.mrlonglegs.Activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.kidari.mrlonglegs.Adapter.CategoryListAdapter
import com.kidari.mrlonglegs.Adapter.ListItemAdapter
import com.kidari.mrlonglegs.DataClass.CategoryMember
import com.kidari.mrlonglegs.DataClass.ListItemMember
import com.kidari.mrlonglegs.R
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.fragment_list.*

class CategoryActivity: AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val list = ArrayList<CategoryMember>()
    val recyclerAdapter = CategoryListAdapter(list)
    var _category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val category = intent.getStringExtra("category")
        category_name.text = category

        if (category != null) {
            loadData(category)
        }
        categoryRecyclerView2.adapter = recyclerAdapter

        categoryRecyclerView2.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        recyclerAdapter.setItemClickListener(object :
            CategoryListAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val item = list[position]
                val intent = Intent(this@CategoryActivity , DetailsActivity::class.java)
                intent.putExtra("id", item.Category_item_id)
                startActivity(intent)
            }
        })
    }

    fun loadData(string: String) {
        db.collection("심부름")
            .get()
            .addOnSuccessListener { result ->
                list.clear()
                for (document in result) {
                    _category = document.data["category"].toString()
                    if (_category.contains(string)) {
                        list.add(toListItemMember(document))
                        recyclerAdapter.notifyDataSetChanged()
                        Log.d(TAG, "${document.id} => ${document.data["name"]}")
                    } else {
                        Log.d(TAG, "${document.id} => ${document.data["name"]}")
                    }
                    Log.d(TAG, "${document.id} => ${document.data["name"]}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun toListItemMember(document: QueryDocumentSnapshot): CategoryMember {
        return CategoryMember(
            document.id, document["title"].toString(), document["regDay"].toString(),
            document["payment"].toString(), document["location"].toString()
        )
    }
}