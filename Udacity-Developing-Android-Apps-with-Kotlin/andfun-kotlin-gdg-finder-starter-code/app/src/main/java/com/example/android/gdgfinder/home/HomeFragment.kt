package com.example.android.gdgfinder.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.gdgfinder.R
import com.example.android.gdgfinder.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val binding = HomeFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

        viewModel.navigateToSearch.observe(viewLifecycleOwner, { navigate ->
            if(navigate) {
                val navController = findNavController()
                navController.navigate(R.id.action_homeFragment_to_gdgListFragment)
                viewModel.onNavigatedToSearch()
            }
        })

        return view
    }
}
