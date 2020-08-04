package com.kidari.mrlonglegs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    val list = ArrayList<RegistrationItemMember>()
    val recyclerAdapter = RegistrationItemAdapter(list)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerview_profile.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 로그아웃 버튼
        logoutButton.setOnClickListener {
            context?.let { it ->
                AuthUI.getInstance()
                    .signOut(it)
                    .addOnCompleteListener {
                        Log.d("dddd", "로그아웃 성공")
                        (activity as MainActivity).startLogin()
                    }
            }
        }

        recyclerview_profile.adapter = recyclerAdapter
        registration_item_button.setOnClickListener {
            loadMyRegErrand()
        }

        did_item_button.setOnClickListener {
            list.clear()
            list.add(
                RegistrationItemMember(
                    "제목 기본값",
                    "등록일 기본값",
                    "비용 기본값",
                    "위치 기본값"
                )
            )
        }

        // 서포터 등록 버튼
        regSupporterButton.setOnClickListener {
            val intent = Intent(context, AddSupportActivity::class.java)
            startActivity(intent)
        }
    }

    fun loadMyRegErrand() {
        db.collection("심부름")
            .whereEqualTo("email", (activity as MainActivity).user.email)
            .get()
            .addOnSuccessListener { documents ->
                list.clear()
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    list.add(toItem(document))
                }
                recyclerAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun toItem(document: QueryDocumentSnapshot): RegistrationItemMember {
        return RegistrationItemMember(
            document.data["title"] as String,
            document.data["regDay"] as String,
            document.data["payment"] as String,
            document.data["location"] as String
        )
    }

    fun setUI() {
        val googleUser = (activity as MainActivity).googleUser
        tvName.text = googleUser?.displayName
        tvEmail.text = googleUser?.email
        Glide.with(this).load(googleUser?.photoUrl).into(ivProfile)
        Log.d("dddd", "${googleUser?.uid}")
    }
}
