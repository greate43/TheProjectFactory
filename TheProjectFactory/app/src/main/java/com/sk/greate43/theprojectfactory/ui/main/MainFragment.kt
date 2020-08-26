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
import com.sk.greate43.theprojectfactory.MyApplication
import com.sk.greate43.theprojectfactory.R
import com.sk.greate43.theprojectfactory.ui.adapter.CountriesAdapter
import com.sk.greate43.theprojectfactory.viewmodel.CountriesViewModel
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    val TAG = MainFragment::class.java.simpleName
    private lateinit var adapter: CountriesAdapter

    override fun onDetach() {
        super.onDetach()
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

        adapter = CountriesAdapter(requireContext()) { _ ->

        }
        adapter.isMultiSelectionAllowed = true

        mainRecyclerView.adapter = adapter

        footer.findViewById<Button>(R.id.btnNext).setOnClickListener {
            val list = adapter.getSelectedCountries()
            if (list.size >= 3) {
                requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                ).replace(R.id.container, SelectedThingsFragment.newInstance(list))
                    .commitNow()
            } else {
                showSnackBar("Select At Least Select 3 Items")
            }
        }
        queryCountries()

        mainSwipeRefreshLayout.setOnRefreshListener {
            queryCountries()
        }
    }

    private fun showSnackBar(string: String) {
        Snackbar.make(footer, string, Snackbar.LENGTH_SHORT).show()
    }

    private fun queryCountries() {
        if (!MyApplication.getInstance()?.hasNetwork()!!) {
            showSnackBar("No Internet")
        } else {
            countriesViewModel.getCountries().observe(viewLifecycleOwner,
                { countries ->
                    Log.d(TAG, "Country: ${countries[0].name}")
                    adapter.setData(countries)
                })
        }
        stopRefresh()
    }

    private fun stopRefresh() {
        if (mainSwipeRefreshLayout.isRefreshing) {
            mainSwipeRefreshLayout.isRefreshing = false
        }
    }
}