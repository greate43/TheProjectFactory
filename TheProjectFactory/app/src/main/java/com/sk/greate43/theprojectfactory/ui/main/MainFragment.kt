package com.sk.greate43.theprojectfactory.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sk.greate43.theprojectfactory.MyApplication
import com.sk.greate43.theprojectfactory.R
import com.sk.greate43.theprojectfactory.service.model.Country
import com.sk.greate43.theprojectfactory.ui.adapter.CountriesAdapter
import com.sk.greate43.theprojectfactory.viewmodel.CountriesViewModel
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    val TAG = MainFragment::class.java.simpleName
//    private lateinit var adapter: CountriesAdapter
//Less fields, less potential leaks

    companion object {
        fun newInstance() = MainFragment()
    }

    private val countriesViewModel by viewModels<CountriesViewModel>()
    private val countriesForView = ArrayList<Country>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainRecyclerView.setHasFixedSize(true)
        val llm = LinearLayoutManager(context)
        llm.orientation = RecyclerView.VERTICAL

        mainRecyclerView.layoutManager = llm
        mainRecyclerView.itemAnimator = DefaultItemAnimator()

        val adapter = CountriesAdapter(countriesForView)//Reference for views
        mainRecyclerView.adapter = adapter


        footer.findViewById<Button>(R.id.btnNext).setOnClickListener {
            if (countriesForView.size >= 3) {
                //oops forgot to pass selected only :D
                val selected = countriesForView.filter { it.isSelected } as ArrayList<Country>
                requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                ).replace(R.id.container, SelectedThingsFragment.newInstance(selected))
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

    private fun queryCountries() : Boolean{
        mainSwipeRefreshLayout.isRefreshing = true
        if (!MyApplication.getInstance()?.hasNetwork()!!) {
            showSnackBar("No Internet")
            return false
        } else {
            countriesViewModel.getCountries().observe(viewLifecycleOwner, Observer<List<Country>> {
                countriesForView.clear()
                countriesForView.addAll(it)
                mainRecyclerView.adapter?.notifyDataSetChanged()
            })
        }
        stopRefresh()
        return true
    }

    private fun stopRefresh() {
        if (mainSwipeRefreshLayout.isRefreshing) {
            mainSwipeRefreshLayout.isRefreshing = false
        }
    }
}