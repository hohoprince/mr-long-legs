package com.kidari.mrlonglegs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import kotlinx.android.synthetic.main.fragment_search_title.*

class SearchTitleFragment : Fragment() {

    val items = ArrayList<ListItemMember>()
    val recyclerAdapter = ListItemAdapter(items)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_title, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView.setOnSearchClickListener {
            val title = searchView.query.toString()
            // db에서 title 검색
        }
        searchTitleRecyclerView.adapter = recyclerAdapter
    }

}