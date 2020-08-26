package com.sk.greate43.theprojectfactory.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sk.greate43.theprojectfactory.OnSelectedStateListener
import com.sk.greate43.theprojectfactory.R
import com.sk.greate43.theprojectfactory.ui.adapter.CountriesAdapter
import com.sk.greate43.theprojectfactory.viewmodel.CountriesViewModel
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    val TAG = MainFragment::class.java.simpleName
    private lateinit var adapter: CountriesAdapter
    private var callback: OnSelectedStateListener? = null
    fun setOnSelectedStateListener(callback: OnSelectedStateListener) {
        this.callback = callback
    }

    override fun onDetach() {
        super.onDetach()
        this.callback = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }


    private val countriesViewModel by viewModels<CountriesViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainRecyclerView.setHasFixedSize(true)
        val llm = LinearLayoutManager(context)
        llm.orientation = RecyclerView.VERTICAL

        mainRecyclerView.layoutManager = llm
        mainRecyclerView.itemAnimator = DefaultItemAnimator()

        adapter = CountriesAdapter(requireContext()){country ->

        }


        mainRecyclerView.adapter = adapter

        footer.findViewById<Button>(R.id.btnNext).setOnClickListener {
            val list = adapter.getSelectedCountries()
            if (list.size >= 3) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SelectedThingsFragment.newInstance(list))
                    .commitNow()
            } else {
                Snackbar.make(footer,"At Least Select 3 Items",Snackbar.LENGTH_SHORT).show()
            }
        }
        countriesViewModel.getCountries().observe(viewLifecycleOwner,
            { countries ->
                Log.d(TAG, "Country: ${countries[0].name}")
                adapter.setData(countries)
            })
    }

}