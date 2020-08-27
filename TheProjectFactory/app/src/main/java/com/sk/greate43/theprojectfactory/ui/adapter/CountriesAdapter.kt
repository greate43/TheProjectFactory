package com.sk.greate43.theprojectfactory.ui.adapter


//Keep code clean, remove unused stuff:
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sk.greate43.theprojectfactory.R
import com.sk.greate43.theprojectfactory.service.model.Country

//Reference your data, don't create it in adapter
class CountriesAdapter(private val data: List<Country>, val callback: (Country) -> Unit = {}) : //Keep code clean, setting empty brackets here makes arg optional
    RecyclerView.Adapter<CountriesAdapter.CountriesHolder>() {
    //    var selectedItemPos = -1
//    var lastItemSelectedPos = -1
    //Selected items should form part of the data model, because you are using unrelated fields,
    //you get a bug where the recycled items ALSO have checked marks
    //private val mContext = context // NEVER hold context anywhere in Android, always pass by reference

    //private var selectedItem: ArrayList<Countries> = ArrayList()//Two arrays with same data? That's a bit costly

    var isMultiSelectionAllowed = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesHolder {
        return CountriesHolder(
            LayoutInflater
                .from(parent.context)//See, there is always context in inflated views
                .inflate(R.layout.list_countries, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CountriesHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            if (isMultiSelectionAllowed) {
                select(position)
            } else {
                clearSelect()
                select(position)
            }
            //no need for all of this:
//            if (!selectedItem.contains(data[position])) {
//                selectedItem.add(data[position])
//                holder.countryName.setCompoundDrawablesRelativeWithIntrinsicBounds(
//                    0,
//                    0,
//                    R.drawable.selected,
//                    0
//                )
//            } else if (selectedItem.isNotEmpty() && selectedItem.contains(data[position])) {
//                selectedItem.remove(data[position])
//                holder.countryName.setCompoundDrawablesRelativeWithIntrinsicBounds(
//                    0,
//                    0,
//                    0,
//                    0
//                )
//            }
        }

    }

    fun select(position: Int){
        data[position].isSelected = !data[position].isSelected
        callback(data[position])
        notifyItemChanged(position)
    }

    fun clearSelect(){
        data.forEachIndexed { i, _ ->  data[i].isSelected = false}
        notifyDataSetChanged()
    }

    //Don't need to make adapters this complex
//    private fun singleItemSelection(position: Int) {
//        selectedItemPos = position
//        lastItemSelectedPos = if (lastItemSelectedPos == -1)
//            selectedItemPos
//        else {
//            notifyItemChanged(lastItemSelectedPos)
//            selectedItemPos
//        }
//        notifyItemChanged(selectedItemPos)
//    }
//
//
//    fun getSelectedCountries(): ArrayList<Countries> {
//        return selectedItem
//    }
//
//    fun setPreSelectedItem(selected: Int) {
//        callback(data[selected])
//        singleItemSelection(selected)
//    }
//
//    // this is will set the data to the list view
//    fun setData(newData: ArrayList<Countries>) {
//        data = newData
//        notifyDataSetChanged()
//    }

    inner class CountriesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val countryName: TextView =
            itemView.findViewById(R.id.tvCountryName)//It's safer tho type the view than infer it
        //Add your selector views in the view, less processing needed when done in the adapter code:
        private val selector: ImageView = itemView.findViewById(R.id.tvCountrySelected)

        fun bind(country: Country) {//Don't need to pass context, views always have context
            countryName.text = country.name
            selector.visibility = if (country.isSelected) View.VISIBLE else View.INVISIBLE
        }
    }
}