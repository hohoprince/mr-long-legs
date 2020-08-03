package com.kidari.mrlonglegs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

const val RC_SIGN_IN = 111

class MainActivity : AppCompatActivity() {

    val listFragment = ListFragment()
    val registrationFragment = RegistrationFragment()
    val searchFragment = SearchFragment()
    val profileFragment = ProfileFragment()
    var curFragment: Fragment = listFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

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
            RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                profileFragment.user = user
                profileFragment.setUI()
            } else {

            }
        }
    }
}