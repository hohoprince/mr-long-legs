package com.kidari.mrlonglegs.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kidari.mrlonglegs.DataClass.CategoryMember
import com.kidari.mrlonglegs.DataClass.SearchItemMember
import com.kidari.mrlonglegs.R

class CategoryListAdapter(val items: List<CategoryMember>) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categoty_list_item_layout, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.title_search.text = item.Category_item_title
        holder.registraion_search.text = item.Category_item_registrationdate
        holder.location_search.text = item.Category_item_location
        holder.payment_serach.text = item.Category_item_pay

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var title_search = itemview.findViewById<TextView>(R.id.category_list_title)
        var registraion_search = itemview.findViewById<TextView>(R.id.category_list_registration)
        var location_search = itemview.findViewById<TextView>(R.id.category_list_location)
        var payment_serach = itemview.findViewById<TextView>(R.id.categoty_list_payment)
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }
}

