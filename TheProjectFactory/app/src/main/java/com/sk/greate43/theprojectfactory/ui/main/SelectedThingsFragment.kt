package com.sk.greate43.theprojectfactory.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sk.greate43.theprojectfactory.OnSelectedStateListener
import com.sk.greate43.theprojectfactory.R
import com.sk.greate43.theprojectfactory.service.model.Countries
import com.sk.greate43.theprojectfactory.ui.adapter.CountriesAdapter
import kotlinx.android.synthetic.main.fragment_selected_things.*
import kotlin.random.Random


private const val ARG_PARAM1 = "param1"


class SelectedThingsFragment : Fragment() {
    private var selectedList: ArrayList<Countries>? = null
    private var callback: OnSelectedStateListener? = null
    private var randomSelection = 0
    val TAG = SelectedThingsFragment::class.java.simpleName
    fun setOnSelectedStateListener(callback: OnSelectedStateListener) {
        this.callback = callback
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedList = it.getParcelableArrayList(ARG_PARAM1)
            randomSelection = (0..it.size()).random()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_things, container, false)
    }

    lateinit var adapter: CountriesAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        selectedThingsRecyclerView.setHasFixedSize(true)
        val llm = LinearLayoutManager(context)
        llm.orientation = RecyclerView.VERTICAL

        selectedThingsRecyclerView.layoutManager = llm
        selectedThingsRecyclerView.itemAnimator = DefaultItemAnimator()

        adapter = CountriesAdapter(requireContext()) { country ->
            tvSelected.text = "Chosen Country :\n${country.name}"
        }
        adapter.isMultiSelectionAllowed = false


        selectedThingsRecyclerView.adapter = adapter

        selectedFooter.findViewById<Button>(R.id.btnPrevious).setOnClickListener {

        }
        Log.d(TAG, "name ${selectedList?.get(0)?.name}")

        selectedList?.let { adapter.setData(it) }
//        selectedThingsRecyclerView.findViewHolderForAdapterPosition(randomSelection)?.itemView?.performClick()
        adapter.setPreSelectedItem(randomSelection)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param selected Parameter 1.
         * @return A new instance of fragment SelectedThingsFragment.
         */
        @JvmStatic
        fun newInstance(selected: ArrayList<Countries>) =
            SelectedThingsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PARAM1, selected)
                }
            }
    }
}