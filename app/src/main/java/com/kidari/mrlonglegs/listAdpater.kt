package com.kidari.mrlonglegs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemberAdapter(val items: List<Member>) : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.list_name.text = item.list_name
        holder.list_ClosingDate.text = item.list_ClosingDate
        holder.list_location.text = item.list_location
        holder.list_pay.text = item.list_pay
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var list_name = itemview.findViewById<TextView>(R.id.list_name)
        var list_ClosingDate = itemview.findViewById<TextView>(R.id.list_ClosingDate)
        var list_location = itemview.findViewById<TextView>(R.id.list_location)
        var list_pay = itemview.findViewById<TextView>(R.id.list_pay)
    }
}
