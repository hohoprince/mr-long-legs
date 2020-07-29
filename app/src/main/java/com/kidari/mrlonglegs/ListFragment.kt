package com.kidari.mrlonglegs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

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
        recyclerview_list.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<ListItemMember>()
        list.clear()
        list.add(
            ListItemMember(
                "제목 기본값",
                "등록일 기본값",
                "비용 기본값",
                "위치 기본값"
            )
        )
        val recyclerAdapter = ListItemAdapter(list)
        recyclerview_list.adapter = recyclerAdapter

    }
}