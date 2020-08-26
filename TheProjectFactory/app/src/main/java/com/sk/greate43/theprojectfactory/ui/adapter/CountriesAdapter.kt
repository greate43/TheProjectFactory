package com.sk.greate43.theprojectfactory.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sk.greate43.theprojectfactory.R
import com.sk.greate43.theprojectfactory.service.model.Countries


class CountriesAdapter(context: Context, val callback: (Countries) -> Unit) :
    RecyclerView.Adapter<CountriesAdapter.CountriesHolder>() {
    var selectedItemPos = -1
    var lastItemSelectedPos = -1
    private val mContext = context
    private var data = ArrayList<Countries>()
    private var selectedItem: ArrayList<Countries> = ArrayList()
    var isMultiSelectionAllowed = true
    private var TAG = CountriesAdapter::class.java.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesHolder {
        return CountriesHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_countries, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CountriesHolder, position: Int) {
        holder.bind(context = mContext, countries = data[position])
        if(position == selectedItemPos)
            holder.countryName.visibility = View.GONE
        else
            holder.countryName.visibility = View.VISIBLE

        holder.itemView.setOnClickListener {
            callback(data[position])
            if (isMultiSelectionAllowed) {
                if (!selectedItem.contains(data[position])) {
                    selectedItem.add(data[position])
                    holder.countryName.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.selected,
                        0
                    )
                } else if (selectedItem.isNotEmpty() && selectedItem.contains(data[position])) {
                    selectedItem.remove(data[position])
                    holder.countryName.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,
                        0,
                        0,
                        0
                    )
                }
            } else {

                selectedItemPos = position
                if(lastItemSelectedPos == -1)
                    lastItemSelectedPos = selectedItemPos
                else {
                    notifyItemChanged(lastItemSelectedPos)
                    lastItemSelectedPos = selectedItemPos
                }
                notifyItemChanged(selectedItemPos)
            }
        }

    }


    fun getSelectedCountries(): ArrayList<Countries> {
        return selectedItem
    }


    // this is will set the data to the list view
    fun setData(newData: ArrayList<Countries>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class CountriesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryName = itemView.findViewById<TextView>(R.id.tvCountryName)
        fun bind(context: Context, countries: Countries) {
            countryName.text = countries.name
        }
    }
}