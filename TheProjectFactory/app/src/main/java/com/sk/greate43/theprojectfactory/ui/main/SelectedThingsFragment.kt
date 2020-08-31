package com.sk.greate43.theprojectfactory.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sk.greate43.theprojectfactory.R
import com.sk.greate43.theprojectfactory.service.model.Country
import com.sk.greate43.theprojectfactory.ui.adapter.CountriesAdapter
import kotlinx.android.synthetic.main.fragment_selected_things.*


private const val ARG_PARAM1 = "param1"


class SelectedThingsFragment : Fragment() {
    val TAG = SelectedThingsFragment::class.java.simpleName

    private val selectedList: ArrayList<Country> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            selectedList.addAll(it.getParcelableArrayList(ARG_PARAM1)!!)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_things, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedThingsRecyclerView.setHasFixedSize(true)
        val llm = LinearLayoutManager(context)
        llm.orientation = RecyclerView.VERTICAL

        selectedThingsRecyclerView.layoutManager = llm
        selectedThingsRecyclerView.itemAnimator = DefaultItemAnimator()

        val adapter = CountriesAdapter(selectedList) { country ->
            val text=choosenItemLayout.findViewById<TextView>(R.id.tvSelected)
            text.text= "${country.name}"
            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
            text.startAnimation(animation)
        }
        selectedThingsRecyclerView.adapter = adapter
        adapter.isMultiSelectionAllowed = false


        selectedFooter.findViewById<Button>(R.id.btnPrevious).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            ).replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        Log.d(TAG, "name ${selectedList?.get(0)?.name}")

//        selectedThingsRecyclerView.findViewHolderForAdapterPosition(randomSelection)?.itemView?.performClick()
        adapter.clearSelect()
        adapter.select((0 until selectedList.size).random())
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
        fun newInstance(selected: ArrayList<Country>) =
            SelectedThingsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PARAM1, selected)
                }
            }
    }
}