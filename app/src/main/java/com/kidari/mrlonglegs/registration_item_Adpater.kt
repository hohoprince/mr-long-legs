package com.kidari.mrlonglegs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class registrationitemAdapter(val items: List<registration_item_Member>) : RecyclerView.Adapter<registrationitemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.registration_history_item_layout, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.registration_item_name.text = item.registration_item_name
        holder.registration_item_registrationdate.text = item.registration_item_registrationdate
        holder.registration_item_location.text = item.registration_itme_location
        holder.registration_item_pay.text = item.registration_itme_pay
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var registration_item_name = itemview.findViewById<TextView>(R.id.registration_item_name)
        var registration_item_registrationdate = itemview.findViewById<TextView>(R.id.registration_item_registrationdate)
        var registration_item_location = itemview.findViewById<TextView>(R.id.registration_item_location)
        var registration_item_pay = itemview.findViewById<TextView>(R.id.registration_item_pay)
    }
}
