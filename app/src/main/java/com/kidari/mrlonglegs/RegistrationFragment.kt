package com.kidari.mrlonglegs

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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

        spnFirst.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0?.getItemAtPosition(p2).toString()
                if(selectedItem == "서울특별시"){ selectedRegion(R.array.spinner_region_seoul.toString()) }
                else if(selectedItem == "부산광역시"){selectedRegion(R.array.spinner_region_busan.toString())}
                else if(selectedItem == "대구광역시"){selectedRegion(R.array.spinner_region_daegu.toString())}
                else if(selectedItem == "인천광역시"){selectedRegion(R.array.spinner_region_incheon.toString())}
                else if(selectedItem == "광주광역시"){selectedRegion(R.array.spinner_region_gwangju.toString())}
                else if(selectedItem == "대전광역시"){selectedRegion(R.array.spinner_region_daejeon.toString())}
                else if(selectedItem == "울산광역시"){selectedRegion(R.array.spinner_region_ulsan.toString())}
                else if(selectedItem == "세종특별자치시"){selectedRegion(R.array.spinner_region_sejong.toString())}
                else if(selectedItem == "경기도"){selectedRegion(R.array.spinner_region_gyeonggi.toString())}
                else if(selectedItem == "강원도"){selectedRegion(R.array.spinner_region_gangwon.toString())}
                else if(selectedItem == "충청북도"){selectedRegion(R.array.spinner_region_chung_buk.toString())}
                else if(selectedItem == "충청남도"){selectedRegion(R.array.spinner_region_chung_nam.toString())}
                else if(selectedItem == "전라북도"){selectedRegion(R.array.spinner_region_jeon_buk.toString())}
                else if(selectedItem == "전라남도"){selectedRegion(R.array.spinner_region_jeon_nam.toString())}
                else if(selectedItem == "경상북도"){selectedRegion(R.array.spinner_region_gyeong_buk.toString())}
                else if(selectedItem == "경상남도"){selectedRegion(R.array.spinner_region_gyeong_nam.toString())}
                else if(selectedItem == "제주특별자치도"){selectedRegion(R.array.spinner_region_jeju.toString())}

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")

            }
        }
    }

    private fun selectedRegion(region:String){
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

    fun settingPermission(){

    }
}