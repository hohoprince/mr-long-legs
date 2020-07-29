package com.kidari.mrlonglegs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerview_profile.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<registration_item_Member>()


        registration_item_button.setOnClickListener {
            list.clear()
            list.add(
            registration_item_Member(
                "제목 기본값",
                "등록일 기본값",
                "비용 기본값",
                "위치 기본값"
            )
            )
            val recyclerAdapter = registrationitemAdapter(list)
            recyclerview_profile.adapter = recyclerAdapter
        }
        did_item_button.setOnClickListener {
            list.clear()
            list.add(
                registration_item_Member(
                    "제목 기본값",
                    "등록일 기본값",
                    "비용 기본값",
                    "위치 기본값"
                )
            )
            val recyclerAdapter = registrationitemAdapter(list)
            recyclerview_profile.adapter = recyclerAdapter
        }
    }
}
