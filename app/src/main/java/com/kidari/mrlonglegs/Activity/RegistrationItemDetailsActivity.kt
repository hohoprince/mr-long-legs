package com.kidari.mrlonglegs.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.firepush.Fire
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kidari.mrlonglegs.Adapter.RegistrationItemAdapter
import com.kidari.mrlonglegs.DataClass.RegistrationItemMember
import com.kidari.mrlonglegs.Fragment.ProfileFragment
import com.kidari.mrlonglegs.R
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.ivDetailsProfile
import kotlinx.android.synthetic.main.activity_details.progressBar
import kotlinx.android.synthetic.main.activity_details.tvCategory
import kotlinx.android.synthetic.main.activity_details.tvContent
import kotlinx.android.synthetic.main.activity_details.tvEmerg
import kotlinx.android.synthetic.main.activity_details.tvLocation
import kotlinx.android.synthetic.main.activity_details.tvName
import kotlinx.android.synthetic.main.activity_details.tvPayment
import kotlinx.android.synthetic.main.activity_details.tvTitle
import kotlinx.android.synthetic.main.activity_did_item_details.*
import kotlinx.android.synthetic.main.activity_registration_details.*

class RegistrationItemDetailsActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var sbremail: String
    lateinit var sbrtoken: String
    lateinit var sbrtitle: String
    lateinit var supporterInfo: String
    lateinit var supporterEmail: String
    var sbrstate: Int = 0
    val list = ArrayList<RegistrationItemMember>()
    val recyclerAdapter = RegistrationItemAdapter(list)
    val registrationItemDetailsEnd : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_details)
        val id = intent.getStringExtra("id")
        loadData(id!!)
        Log.d("태그", "상태전")
        Log.d("d123","$sbrstate")

        btn_edit.setOnClickListener {
            if (supporterInfo == "") {
                val intent = Intent(this, EditItemActivity::class.java).apply {
                    putExtra("id", id)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "이미 수행중인 심부름은 수정이 불가능합니다.", Toast.LENGTH_SHORT).show()
            }
        }
        btn_delete.setOnClickListener {
            if (supporterInfo == "") {
                db.collection("심부름").document("$id")
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(this, "삭제되었습니다", Toast.LENGTH_SHORT).show()
                        setResult(Activity.RESULT_OK)
                        finish()
                    }

            } else {
                Toast.makeText(this, "이미 수행중인 심부름은 삭제가 불가능합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun loadData(id: String) {

        val docRef = db.collection("심부름").document("$id")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    tvName.text = document["name"].toString()
                    tvCategory.text = document["category"].toString()
                    tvContent.text = document["content"].toString()
                    tvLocation.text = document["location"].toString()
                    val emergState = document["urgencyDegree"].toString()
                    tvEmerg.text = emergState
                    when (emergState) {
                        "급해요" -> ivState2.setImageResource(R.drawable.ic_emerg_state_red_24)
                        "보통이에요" -> ivState2.setImageResource(R.drawable.ic_emerg_state_yellow_24)
                        "널널해요" -> ivState2.setImageResource(R.drawable.ic_emerg_state_green_24)
                    }
                    ivState2.visibility = View.VISIBLE
                    tvTitle.text = document["title"].toString()
                    tvPayment.text = document["payment"].toString()
                    supporterEmail = document["supporter"].toString()
                    supporter_email.text = supporterEmail
                    supporter_name.text = document["supporterName"].toString()
                    supporterInfo = document["supporter"].toString()
                    val state = document["state"].toString().toInt()
                    if (state != 0) { // 수행 전이 아니면 서포터 정보 보여줌
                        loadSupporterData(supporterEmail)
                    }
                    sbremail = document["email"].toString()
                    sbrtitle = document["title"].toString()
                    sbrtoken = document["token"].toString()
                    sbrstate = document["state"].toString().toInt()
                    if(sbrstate >= 1){
                        btn_delete.isEnabled = false
                        btn_edit.isEnabled = false
                    }
                    val uri = Uri.parse(document["photoUrl"].toString())
                    Glide.with(this).load(uri).into(ivDetailsProfile)
                    progressBar.visibility = View.GONE
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    fun loadSupporterData(email: String) {
        val docRef = db.collection("사용자")
            .document("$email")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    tvComplete.text = document["comCount"].toString()
                    tvGiveup.text = document["giveUpCount"].toString()
                    supporterInfoLayout.visibility = View.VISIBLE
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
}
