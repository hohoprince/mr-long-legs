package com.kidari.mrlonglegs.Fragment

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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.kidari.mrlonglegs.Activity.*
import com.kidari.mrlonglegs.Adapter.ListItemAdapter
import com.kidari.mrlonglegs.Adapter.RegistrationItemAdapter
import com.kidari.mrlonglegs.DataClass.RegistrationItemMember
import com.kidari.mrlonglegs.R
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
                        // Main Activity 재시작
                        startActivity(
                            Intent(context, MainActivity::class.java)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        )
                    }
            }
        }



        // 내 심부름
        recyclerview_profile.adapter = recyclerAdapter
        registration_item_button.setOnClickListener {
            recyclerview_profile.visibility = View.INVISIBLE
            progressBar4.visibility = View.VISIBLE
            loadMyRegErrand()

            recyclerview_profile.adapter = recyclerAdapter
            recyclerAdapter.setItemClickListener(object :
                RegistrationItemAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {
                    val item = list[position]
                    val intent = Intent(context, RegistrationItemDetailsActivity::class.java).apply {
                        putExtra("id", item.registration_item_id)
                    }
                    startActivity(intent)
                }
            })
        }

        // 내가 한 심부름
        did_item_button.setOnClickListener {
            recyclerview_profile.visibility = View.INVISIBLE
            progressBar4.visibility = View.VISIBLE
            loadMyDidErrand()
            recyclerview_profile.adapter = recyclerAdapter
            recyclerAdapter.setItemClickListener(object :
                RegistrationItemAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {
                    val item = list[position]
                    val intent = Intent(context, DidItemDetailsActivity::class.java).apply {
                        putExtra("id", item.registration_item_id)
                    }
                    startActivity(intent)
                }
            })
        }

        // 내가 하고 있는 심부름
        ing_item_button.setOnClickListener {
            recyclerview_profile.visibility = View.INVISIBLE
            progressBar4.visibility = View.VISIBLE
            loadMyIngErrand()
            recyclerview_profile.adapter = recyclerAdapter
            recyclerAdapter.setItemClickListener(object :
                RegistrationItemAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {
                    val item = list[position]
                    val intent = Intent(context, IngItemActivity::class.java).apply {
                        putExtra("id", item.registration_item_id)
                    }
                    startActivity(intent)
                }
            })
            loadMyIngErrand()
        }



    }

    fun loadMyRegErrand() {
        val Ref = db.collection("심부름")
        Ref.whereEqualTo("email", (activity as MainActivity).user.email)
            .get()
            .addOnSuccessListener { documents ->
                list.clear()
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    list.add(toItem(document))
                }
                recyclerAdapter.notifyDataSetChanged()
                recyclerview_profile.visibility = View.VISIBLE
                progressBar4.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun loadMyDidErrand() {
        val Ref = db.collection("심부름")
        Ref.whereEqualTo("supporter", (activity as MainActivity).user.email).whereEqualTo("state", 2)
            .get()
            .addOnSuccessListener { documents ->
                list.clear()
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    list.add(toItem(document))
                }
                recyclerAdapter.notifyDataSetChanged()
                recyclerview_profile.visibility = View.VISIBLE
                progressBar4.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
    fun loadMyIngErrand() {
        val Ref = db.collection("심부름")
        Ref.whereEqualTo("supporter", (activity as MainActivity).user.email).whereEqualTo("state", 1)
            .get()
            .addOnSuccessListener { documents ->
                list.clear()
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    list.add(toItem(document))
                }
                recyclerAdapter.notifyDataSetChanged()
                recyclerview_profile.visibility = View.VISIBLE
                progressBar4.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun toItem(document: QueryDocumentSnapshot): RegistrationItemMember {
        return RegistrationItemMember(
            document.id,
            document.data["title"] as String,
            document.data["regDay"] as String,
            document.data["payment"] as String,
            document.data["location"] as String,
            document["state"].toString().toInt()
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