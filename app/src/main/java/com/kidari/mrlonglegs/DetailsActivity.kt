package com.kidari.mrlonglegs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        btn_proposal.setOnClickListener {
            Toast.makeText(this, "신청되었습니다", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}