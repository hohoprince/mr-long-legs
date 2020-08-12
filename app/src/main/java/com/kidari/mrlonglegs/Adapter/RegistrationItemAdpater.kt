package com.kidari.mrlonglegs.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kidari.mrlonglegs.DataClass.RegistrationItemMember
import com.kidari.mrlonglegs.R

class RegistrationItemAdapter(val items: List<RegistrationItemMember>) : RecyclerView.Adapter<RegistrationItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.registration_history_item_layout, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.registration_item_title.text = item.registration_item_title
        holder.registration_item_registrationdate.text = item.registration_item_registrationdate
        holder.registration_item_location.text = item.registration_itme_location
        holder.registration_item_pay.text = item.registration_itme_pay
        holder.list_item_progress.text = when (item.list_item_progress) {
            0 -> "수행 전"
            1 -> "수행 중"
            2 -> "수행 완료"
            else -> ""
        }
        // 심부름 진행도 이미지 변경
        for ((i, imageView) in holder.list_item_progImages.withIndex()) {
            if (i <= item.list_item_progress) {
                imageView.setImageResource(R.drawable.ic_prog_state_blue_24)
            }
        }

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var registration_item_title = itemview.findViewById<TextView>(R.id.registration_item_name)
        var registration_item_registrationdate = itemview.findViewById<TextView>(R.id.registration_item_registrationdate)
        var registration_item_location = itemview.findViewById<TextView>(R.id.registration_item_location)
        var registration_item_pay = itemview.findViewById<TextView>(R.id.registration_item_pay)
        var list_item_progress = itemview.findViewById<TextView>(R.id.tvProgress)
        var list_item_progImages = arrayListOf<ImageView>(
            itemview.findViewById<ImageView>(R.id.ivProgress1),
            itemview.findViewById<ImageView>(R.id.ivProgress2),
            itemview.findViewById<ImageView>(R.id.ivProgress3))
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }
}
