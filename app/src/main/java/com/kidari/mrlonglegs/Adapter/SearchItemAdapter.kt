package com.kidari.mrlonglegs.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kidari.mrlonglegs.DataClass.SearchItemMember
import com.kidari.mrlonglegs.R

class SearchItemAdapter(val items: List<SearchItemMember>) : RecyclerView.Adapter<SearchItemAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_title_item_layout, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.title_search.text = item.Search_item_title
        holder.registraion_search.text = item.Search_item_registrationdate
        holder.location_search.text = item.Search_item_location
        holder.payment_serach.text = item.Search_item_pay

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var title_search = itemview.findViewById<TextView>(R.id.search_title_title)
        var registraion_search = itemview.findViewById<TextView>(R.id.search_title_registrationDate)
        var location_search = itemview.findViewById<TextView>(R.id.search_title_location)
        var payment_serach = itemview.findViewById<TextView>(R.id.search_title_payment)
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }
}

