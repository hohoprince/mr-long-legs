package com.kidari.mrlonglegs.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.firepush.Fire
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.firestore.FirebaseFirestore
import com.kidari.mrlonglegs.*
import com.kidari.mrlonglegs.DataClass.User
import com.kidari.mrlonglegs.Fragment.ListFragment
import com.kidari.mrlonglegs.Fragment.ProfileFragment
import com.kidari.mrlonglegs.Fragment.RegistrationFragment
import com.kidari.mrlonglegs.Fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

const val RC_SIGN_IN = 111
const val TAG = "dbdb"

class MainActivity : AppCompatActivity() {

    val listFragment = ListFragment()
    val registrationFragment =
        RegistrationFragment()
    val searchFragment = SearchFragment()
    val profileFragment = ProfileFragment()
    var curFragment: Fragment = listFragment
    var googleUser: FirebaseUser? = null
    lateinit var user: User
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fire.init("AAAA-YDmyfs:APA91bFcoenc3m0ZBonb1L1iwTAySuECZviM9FD_1xiC8ehvhYVCnIjoJjl_h2mhGfEPM4cA1za2o0bL-Uc7mJBKRbQngc4ENyn83tXjQyzjL70m5NSVGNoZG1QOSXoW3iouKgwOwHWz")

        // firebase 인증
        startLogin()


        // 화면에 프래그먼트 추가
        supportFragmentManager.beginTransaction()
            .add(R.id.container, listFragment)
            .add(R.id.container, registrationFragment)
            .hide(registrationFragment)
            .add(R.id.container, searchFragment)
            .hide(searchFragment)
            .add(R.id.container, profileFragment)
            .hide(profileFragment).commit()
        supportActionBar?.title = "목록"

        // 하단 탭 클릭시 화면 전환
        bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_list -> {
                    if (curFragment !== listFragment) {
                        supportFragmentManager.beginTransaction()
                            .show(listFragment)
                            .hide(curFragment).commit()
                        curFragment = listFragment
                        supportActionBar?.title = "목록"
                    }
                    true
                }
                R.id.tab_registration -> {

                    if (curFragment !== registrationFragment) {
                        supportFragmentManager.beginTransaction()
                            .show(registrationFragment)
                            .hide(curFragment).commit()
                        curFragment = registrationFragment
                        supportActionBar?.title = "등록"
                    }
                    true
                }
                R.id.tab_search -> {
                    if (curFragment !== searchFragment) {
                        supportFragmentManager.beginTransaction()
                            .show(searchFragment)
                            .hide(curFragment).commit()
                        curFragment = searchFragment
                        supportActionBar?.title = "검색"
                    }
                    true
                }
                R.id.tab_profile -> {
                    if (curFragment !== profileFragment) {
                        supportFragmentManager.beginTransaction()
                            .show(profileFragment)
                            .hide(curFragment).commit()
                        curFragment = profileFragment
                        supportActionBar?.title = "프로필"
                    }
                    true
                }
                else -> false
            }
        })

    }

    // firebase 로그인 시작
    fun startLogin() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
// Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                googleUser = FirebaseAuth.getInstance().currentUser
                googleUser?.let {
                    val docRef = db.collection("사용자")
                        .document("${googleUser?.email}")
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                // db에 사용자 있음
                                val data = document.data
                                if (data != null) {
                                    user = User(
                                        name = data?.get("name") as String,
                                        email = data?.get("email") as String,
                                        phoneNumber = data?.get("phoneNumber") as String,
                                        supporter = data?.get("supporter") as Boolean,
                                        pushtoken = FirebaseInstanceId.getInstance().token.toString()
                                    )
                                } else {
                                    // db에 사용자 없음
                                    user = User(
                                        name = googleUser?.displayName.toString(),
                                        email = googleUser?.email.toString(),
                                        phoneNumber = googleUser?.phoneNumber.toString(),
                                        supporter = false,
                                        pushtoken = FirebaseInstanceId.getInstance().token.toString()
                                    )
                                    // 파이어베이스에 사용자 추가
                                    db.collection("사용자").document("${user.email}").set(user)
                                }
                            } else {
                                Log.d(TAG, "No such document")
                            }
                            Log.d(TAG, "사용자 로그인 $user")
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "get failed with ", exception)
                        }
                    profileFragment.setUI()
                }

            } else {

            }
        }
    }
}