package com.kidari.mrlonglegs.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kidari.mrlonglegs.Activity.CategoryActivity
import com.kidari.mrlonglegs.R
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btn_one.setOnClickListener {
               val intent = Intent(context, CategoryActivity::class.java)
                intent.putExtra("category", "청소")
            startActivity(intent)
        }
        btn_two.setOnClickListener {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category", "반려동물")
            startActivity(intent)
        }
        btn_three.setOnClickListener {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category", "딜리버리")
            startActivity(intent)
        }
        btn_four.setOnClickListener {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category", "벌레")
            startActivity(intent)
        }
        btn_five.setOnClickListener {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category", "육아")
            startActivity(intent)
        }
        btn_six.setOnClickListener {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category", "수리")
            startActivity(intent)
        }
        btn_seven.setOnClickListener {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category", "간호")
            startActivity(intent)
        }
        btn_eight.setOnClickListener {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category", "봉사")
            startActivity(intent)
        }
        btn_nine.setOnClickListener {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category", "기타")
            startActivity(intent)
        }
    }
}