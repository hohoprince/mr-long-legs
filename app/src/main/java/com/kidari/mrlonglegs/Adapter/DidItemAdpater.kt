package com.kidari.mrlonglegs.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kidari.mrlonglegs.DataClass.DidItemMember
import com.kidari.mrlonglegs.R

class DidItemAdapter(val items: List<DidItemMember>) : RecyclerView.Adapter<DidItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.did_history_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.did_item_name.text = item.did_item_name
        holder.did_item_registrationdate.text = item.did_item_registrationdate
        holder.did_item_location.text = item.did_item_location
        holder.did_item_pay.text = item.did_item_pay
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var did_item_name = itemview.findViewById<TextView>(R.id.did_item_name)
        var did_item_registrationdate = itemview.findViewById<TextView>(R.id.did_item_registrationdate)
        var did_item_location = itemview.findViewById<TextView>(R.id.did_item_location)
        var did_item_pay = itemview.findViewById<TextView>(R.id.did_item_pay)
    }
}
