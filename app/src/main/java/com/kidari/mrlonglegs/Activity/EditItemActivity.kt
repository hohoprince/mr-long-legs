package com.kidari.mrlonglegs.Activity

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.kidari.mrlonglegs.DataClass.Errand
import com.kidari.mrlonglegs.R
import kotlinx.android.synthetic.main.activity_edit_item.*
import kotlinx.android.synthetic.main.fragment_registration.*

class EditItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)

        var sbrstate: Int = 0
        val id = intent.getStringExtra("id")
        var selectedItem: String? = ""
        val db = FirebaseFirestore.getInstance()

        val docRef = db.collection("심부름").document("$id")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    sbrstate = document["state"].toString().toInt()
                } else {
                    Log.d(TAG, "No such document")
                }

            }

        val spnFirst: Spinner = findViewById(R.id.edit_item_spnFirst)
        val spnSecond: Spinner = findViewById(R.id.edit_item_spnSecond)
        edit_item_firstNumberPicker.minValue = 1
        edit_item_firstNumberPicker.maxValue = 12
        edit_item_secondNumberPicker.minValue = 1
        edit_item_secondNumberPicker.maxValue = 31
        edit_item_thirdNumberPicker.minValue = 0
        edit_item_thirdNumberPicker.maxValue = 23
        edit_item_fourthNumberPicker.minValue = 0
        edit_item_fourthNumberPicker.maxValue = 59
        edit_item_fifthNumberPicker.minValue = 1
        edit_item_fifthNumberPicker.maxValue = 12
        edit_item_sixthNumberPicker.minValue = 1
        edit_item_sixthNumberPicker.maxValue = 31

        let {
            ArrayAdapter.createFromResource(
                it,
                R.array.spinner_region,
                android.R.layout.simple_spinner_item
            )
                .also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    edit_item_spnFirst.adapter = arrayAdapter
                }

        }

        edit_item_spnFirst.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedItem = p0?.getItemAtPosition(p2).toString()
                if (selectedItem == "서울특별시") {
                    selectedRegion(R.array.spinner_region_seoul.toString())
                    return
                } else if (selectedItem == "부산광역시") {
                    selectedRegion(R.array.spinner_region_busan.toString())
                } else if (selectedItem == "대구광역시") {
                    selectedRegion(R.array.spinner_region_daegu.toString())
                } else if (selectedItem == "인천광역시") {
                    selectedRegion(R.array.spinner_region_incheon.toString())
                } else if (selectedItem == "광주광역시") {
                    selectedRegion(R.array.spinner_region_gwangju.toString())
                } else if (selectedItem == "대전광역시") {
                    selectedRegion(R.array.spinner_region_daejeon.toString())
                } else if (selectedItem == "울산광역시") {
                    selectedRegion(R.array.spinner_region_ulsan.toString())
                } else if (selectedItem == "세종특별자치시") {
                    selectedRegion(R.array.spinner_region_sejong.toString())
                } else if (selectedItem == "경기도") {
                    selectedRegion(R.array.spinner_region_gyeonggi.toString())
                } else if (selectedItem == "강원도") {
                    selectedRegion(R.array.spinner_region_gangwon.toString())
                } else if (selectedItem == "충청북도") {
                    selectedRegion(R.array.spinner_region_chung_buk.toString())
                } else if (selectedItem == "충청남도") {
                    selectedRegion(R.array.spinner_region_chung_nam.toString())
                } else if (selectedItem == "전라북도") {
                    selectedRegion(R.array.spinner_region_jeon_buk.toString())
                } else if (selectedItem == "전라남도") {
                    selectedRegion(R.array.spinner_region_jeon_nam.toString())
                } else if (selectedItem == "경상북도") {
                    selectedRegion(R.array.spinner_region_gyeong_buk.toString())
                } else if (selectedItem == "경상남도") {
                    selectedRegion(R.array.spinner_region_gyeong_nam.toString())
                } else if (selectedItem == "제주특별자치도") {
                    selectedRegion(R.array.spinner_region_jeju.toString())
                }
                return
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")

            }
        }
        btn_edit_item_regist.setOnClickListener {
            //수정하기 버튼이 눌렸을때 동작
            val database = FirebaseDatabase.getInstance()
            var phonenumber: String = edit_item_edit_phoneNum.text.toString()
            var title: String = edit_item_edit_title.text.toString()
            var content: String = edit_item_registration_edit_content.text.toString()
            var dateMonth: String = edit_item_firstNumberPicker.value.toString()
            var dateDay: String = edit_item_secondNumberPicker.value.toString()
            var dateHour: String = edit_item_thirdNumberPicker.value.toString()
            var dateMinute: String = edit_item_fourthNumberPicker.value.toString()
            var dueDateMonth: String = edit_item_fifthNumberPicker.value.toString()
            var dueDateDay: String = edit_item_sixthNumberPicker.value.toString()
            var city: String = edit_item_spnFirst.selectedItem.toString()
            var county: String = edit_item_spnSecond.selectedItem.toString()
            var firstAddress: String = edit_item_address.text.toString()
            var secondAddress: String = edit_item_detailAddress.text.toString()
            var payment: String = edit_item_payment.text.toString() + "원"

            var selectedRadioBtn: String = ""
            if (edit_item_radioBtn_first.isChecked) {
                selectedRadioBtn = edit_item_radioBtn_first.text.toString()
            } else if (edit_item_radioBtn_second.isChecked) {
                selectedRadioBtn = edit_item_radioBtn_second.text.toString()
            } else if (edit_item_radioBtn_third.isChecked) {
                selectedRadioBtn = edit_item_radioBtn_third.text.toString()
            }
            // 등록화면에서 입력한 값들을 저장하기 위한 변수

            var selectedCategory: String = ""
            if (edit_item_categoryOne.isChecked) {
                selectedCategory = edit_item_categoryOne.text.toString()
            } else if (edit_item_categoryTwo.isChecked) {
                selectedCategory = edit_item_categoryTwo.text.toString()
            } else if (edit_item_categoryThree.isChecked) {
                selectedCategory = edit_item_categoryThree.text.toString()
            } else if (edit_item_categoryFour.isChecked) {
                selectedCategory = edit_item_categoryFour.text.toString()
            } else if (edit_item_categoryFive.isChecked) {
                selectedCategory = edit_item_categoryFive.text.toString()
            } else if (edit_item_categorySix.isChecked) {
                selectedCategory = edit_item_categorySix.text.toString()
            } else if (edit_item_categorySeven.isChecked) {
                selectedCategory = edit_item_categorySeven.text.toString()
            } else if (edit_item_categoryEight.isChecked) {
                selectedCategory = edit_item_categoryEight.text.toString()
            } else if (edit_item_categoryEtc.isChecked) {
                selectedCategory = edit_item_categoryEtc.text.toString()
            }

            var date: String = dateMonth + "월" + dateDay + "일" + dateHour + "시" + dateMinute + "분"
            var dueDate: String = dueDateMonth + "월" + dueDateDay + "일"
            var location: String = "$city $county"
            var address: String = "$firstAddress $secondAddress"

            val user = FirebaseAuth.getInstance().currentUser
            val token: String = FirebaseInstanceId.getInstance().token.toString()
            user?.let {
                val tempErrand = Errand(
                    user.displayName, user.email, phonenumber,
                    user.photoUrl.toString(), location, address, payment, date, dueDate,
                    content, title, selectedRadioBtn, selectedCategory, "", sbrstate, "",token
                )
                db.collection("심부름").document("$id").set(tempErrand)
            }
            // 데이터베이스 심부름에 연결
            Toast.makeText(this, "수정되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectedRegion(region: String) {
        let {
            ArrayAdapter.createFromResource(
                it,
                region.toInt(),
                android.R.layout.simple_spinner_item
            )
                .also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    edit_item_spnSecond.adapter = arrayAdapter
                }

        }
    }

    val REQUEST_IMAGE_CAPTURE = 1

}