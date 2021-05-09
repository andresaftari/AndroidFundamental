package com.andresaftari.apiexample.ui.home

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andresaftari.apiexample.R
import com.andresaftari.apiexample.databinding.FragmentHomeBinding
import com.andresaftari.apiexample.ui.detail.DetailActivity
import com.andresaftari.apiexample.ui.search.SearchActivity
import com.andresaftari.apiexample.utils.MainAdapter
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {
    private lateinit var mainAdapter: MainAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isConnectionAvailable()) {
            Snackbar.make(
                requireView(),
                "No internet connection!",
                Snackbar.LENGTH_SHORT
            ).setAction("RETRY") { fetchUserList() }.show()

            with(binding.tvNoData) {
                visibility = View.VISIBLE
                text = getString(R.string.text_error)
            }
        } else fetchUserList()

        binding.fabSearch.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    SearchActivity::class.java
                )
            )
        }
    }

    private fun fetchUserList() = with(viewModel) {
        getUserList()
        user.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                mainAdapter = MainAdapter(it) { _, item ->
                    startActivity(
                        Intent(
                            requireContext(),
                            DetailActivity::class.java
                        ).putExtra("EXTRA_DETAIL", item.login)
                    )
                }
            }

            with(binding.rvMain) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = mainAdapter
            }

            binding.pbHome.visibility = View.GONE
        })
    }

    private fun isConnectionAvailable() = (requireContext()
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            ).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }
}