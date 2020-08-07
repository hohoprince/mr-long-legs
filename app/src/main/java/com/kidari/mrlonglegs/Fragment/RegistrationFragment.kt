package com.kidari.mrlonglegs.Fragment

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.kidari.mrlonglegs.Activity.MainActivity
import com.kidari.mrlonglegs.DataClass.Errand
import com.kidari.mrlonglegs.R
import kotlinx.android.synthetic.main.fragment_registration.*

@Suppress("UNREACHABLE_CODE")
class RegistrationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var selectedItem: String? = ""
        val db = FirebaseFirestore.getInstance()

        val spnFirst: Spinner = view.findViewById(R.id.spnFirst)
        val spnSecond: Spinner = view.findViewById(R.id.spnSecond)
        firstNumberPicker.minValue = 1
        firstNumberPicker.maxValue = 12
        secondNumberPicker.minValue = 1
        secondNumberPicker.maxValue = 31
        thirdNumberPicker.minValue = 0
        thirdNumberPicker.maxValue = 23
        fourthNumberPicker.minValue = 0
        fourthNumberPicker.maxValue = 59
        fifthNumberPicker.minValue = 1
        fifthNumberPicker.maxValue = 12
        sixthNumberPicker.minValue = 1
        sixthNumberPicker.maxValue = 31

        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.spinner_region,
                android.R.layout.simple_spinner_item
            )
                .also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spnFirst.adapter = arrayAdapter
                }

        }

        spnFirst.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        registration_button_regist.setOnClickListener {
            //요청하기 버튼이 눌렸을때 동작
            val database = FirebaseDatabase.getInstance()
            var phonenumber: String = registration_edit_phoneNum.text.toString()
            var title: String = registration_edit_title.text.toString()
            var content : String = registration_edit_content.text.toString()
            var dateMonth : String = firstNumberPicker.value.toString()
            var dateDay : String = secondNumberPicker.value.toString()
            var dateHour : String = thirdNumberPicker.value.toString()
            var dateMinute : String = fourthNumberPicker.value.toString()
            var dueDateMonth : String = fifthNumberPicker.value.toString()
            var dueDateDay : String = sixthNumberPicker.value.toString()
            var city : String = spnFirst.selectedItem.toString()
            var county : String = spnSecond.selectedItem.toString()
            var firstAddress : String = registration_edit_address.text.toString()
            var secondAddress : String = registration_edit_detailAddress.text.toString()
            var payment : String = edit_payment.text.toString() + "원"

            var selectedRadioBtn : String = ""
            if (radioBtn_first.isChecked){
                selectedRadioBtn = radioBtn_first.text.toString()
            }else if(radioBtn_second.isChecked){
                selectedRadioBtn = radioBtn_second.text.toString()
            }else if(radioBtn_third.isChecked){
                selectedRadioBtn = radioBtn_third.text.toString()
            }
            // 등록화면에서 입력한 값들을 저장하기 위한 변수

            var selectedCategory : String = ""
            if (categoryOne.isChecked){
                selectedCategory = categoryOne.text.toString()
            }else if(categoryTwo.isChecked){
                selectedCategory = categoryTwo.text.toString()
            }else if(categoryThree.isChecked){
                selectedCategory = categoryThree.text.toString()
            }else if(categoryFour.isChecked){
                selectedCategory = categoryFour.text.toString()
            }else if(categoryFive.isChecked){
                selectedCategory = categoryFive.text.toString()
            }else if(categorySix.isChecked){
                selectedCategory = categorySix.text.toString()
            }else if(categorySeven.isChecked){
                selectedCategory = categorySeven.text.toString()
            }else if(categoryEight.isChecked){
                selectedCategory = categoryEight.text.toString()
            }else if(categoryEtc.isChecked){
                selectedCategory = categoryEtc.text.toString()
            }

            var date : String = dateMonth + "월" + dateDay + "일" + dateHour + "시" +dateMinute + "분"
            var dueDate :String = dueDateMonth + "월" + dueDateDay + "일"
            var location : String = "$city $county"
            var address :String = "$firstAddress $secondAddress"

            val user = (activity as MainActivity).googleUser
            val token:String = FirebaseInstanceId.getInstance().token.toString()
            user?.let {
                val tempErrand = Errand(
                    user.displayName, user.email, phonenumber,
                    user.photoUrl.toString(), location, address, payment, date, dueDate,
                    content, title, selectedRadioBtn, selectedCategory, "","수행전",token
                )
                db.collection("심부름").document().set(tempErrand)
            }
            // 데이터베이스 심부름에 연결
            Toast.makeText(getActivity(), "등록되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectedRegion(region: String) {
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                region.toInt(),
                android.R.layout.simple_spinner_item
            )
                .also { arrayAdapter ->
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spnSecond.adapter = arrayAdapter
                }

        }
    }

    val REQUEST_IMAGE_CAPTURE = 1

    // 카메라 권한 요청
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            context as Activity, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ), REQUEST_IMAGE_CAPTURE
        )
    }

    // 카메라 권한 체크
    private fun checkPersmission(): Boolean {
        return (context?.let {
            ContextCompat.checkSelfPermission(
                it,
                android.Manifest.permission.CAMERA
            )
        } ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            context!!,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    // 권한요청 결과
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(
                "TAG",
                "Permission: " + permissions[0] + "was " + grantResults[0] + "카메라 허가 받음 예이^^"
            )
        } else {
            Log.d("TAG", "카메라 허가 못받음 ㅠ 젠장!!")
        }
    }
}