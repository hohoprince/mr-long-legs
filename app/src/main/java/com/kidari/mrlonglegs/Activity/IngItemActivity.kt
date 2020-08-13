package com.kidari.mrlonglegs.Activity

import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.firepush.Fire
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
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
import kotlinx.android.synthetic.main.activity_ing_item.*

class IngItemActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var sbremail: String
    lateinit var sbrtoken: String
    lateinit var sbrtitle: String
    var sbrstate: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ing_item)
        val id = intent.getStringExtra("id")
        loadData(id!!)

        btn_complete.setOnClickListener {
            Fire.create()
                .setTitle("키다리아저씨")
                .setBody("$sbrtitle" + "  심부름이 완료되었습니다")
                .setCallback { pushCallback, exception ->
                    //get response here
                }
                .toIds("$sbrtoken").push()
            Toast.makeText(this, "완료되었습니다", Toast.LENGTH_SHORT).show()
            updateErrandState(id)
            updateSupporterOfErrand(id)
            countComplete() // 완료 횟수 증가
            setResult(Activity.RESULT_OK)
            finish()
        }

        btn_giveup.setOnClickListener {
            Fire.create()
                .setTitle("키다리아저씨")
                .setBody("서포터가" + "$sbrtitle" + "심부름을 포기했습니다")
                .setCallback { pushCallback, exception ->
                    //get response here
                }
                .toIds("$sbrtoken").push()
            Toast.makeText(this, "포기되었습니다", Toast.LENGTH_SHORT).show()
            updategiveupErrandState(id)
            updategiveupSupporterOfErrand(id)
            countGiveup() // 포기 횟수 증가
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    // 심부름에 서포터 정보 입력
    fun updateSupporterOfErrand(id: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val sfDocRef = db.collection("심부름").document("$id")

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)

            // Note: this could be done without a transaction
            //       by updating the population using FieldValue.increment()
            transaction.update(sfDocRef, "supporter", user?.email)

            // Success
            null
        }.addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }
    }

    fun countGiveup() {
        val googleUser = FirebaseAuth.getInstance().currentUser
        val email = googleUser?.email!!
        val washingtonRef = db.collection("사용자").document("$email")
        washingtonRef.update("giveUpCount", FieldValue.increment(1))
    }

    fun countComplete() {
        val googleUser = FirebaseAuth.getInstance().currentUser
        val email = googleUser?.email!!
        val washingtonRef = db.collection("사용자").document("$email")
        washingtonRef.update("comCount", FieldValue.increment(1))
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
                        "급해요" -> ivState3.setImageResource(R.drawable.ic_emerg_state_red_24)
                        "보통이에요" -> ivState3.setImageResource(R.drawable.ic_emerg_state_yellow_24)
                        "널널해요" -> ivState3.setImageResource(R.drawable.ic_emerg_state_green_24)
                    }
                    ivState3.visibility = View.VISIBLE
                    tvTitle.text = document["title"].toString()
                    tvPayment.text = document["payment"].toString()
                    sbremail = document["email"].toString()
                    sbrtitle = document["title"].toString()
                    sbrtoken = document["token"].toString()
                    sbrstate = document["state"].toString().toInt()
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

    fun updategiveupSupporterOfErrand(id: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val sfDocRef = db.collection("심부름").document("$id")

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)

            // Note: this could be done without a transaction
            //       by updating the population using FieldValue.increment()
            transaction.update(sfDocRef, "supporterName", "없음")
            transaction.update(sfDocRef, "supporter", "")

            // Success
            null
        }.addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }
    }

    // 심부름에 서포터 정보 입력
    fun updategiveupErrandState(id: String) {
        val sfDocRef = db.collection("심부름").document("$id")

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)

            // Note: this could be done without a transaction
            //       by updating the population using FieldValue.increment()
            transaction.update(sfDocRef, "state", 0)

            // Success
            null
        }.addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }
    }

    fun updateErrandState(id: String) {
        val sfDocRef = db.collection("심부름").document("$id")

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)

            // Note: this could be done without a transaction
            //       by updating the population using FieldValue.increment()
            transaction.update(sfDocRef, "state", 2)

            // Success
            null
        }.addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }
    }
}