package com.andresaftari.apiexample.ui.follows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andresaftari.apiexample.databinding.FragmentFollowingsBinding
import com.andresaftari.apiexample.ui.detail.DetailActivity
import com.andresaftari.apiexample.utils.FollowsAdapter

class FollowingsFragment : Fragment() {
    private lateinit var followingAdapter: FollowsAdapter
    private lateinit var binding: FragmentFollowingsBinding
    private lateinit var viewModel: FollowViewModel
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(FollowViewModel::class.java)
        binding = FragmentFollowingsBinding.inflate(layoutInflater, container, false)

        if (requireActivity().intent.hasExtra("EXTRA_DETAIL")) {
            username = requireActivity().intent.getStringExtra("EXTRA_DETAIL")!!
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        with(viewModel) {
            getFollowings(username)
            following.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    followingAdapter = FollowsAdapter(it) { _, item ->
                        startActivity(
                            Intent(requireContext(), DetailActivity::class.java).putExtra(
                                "EXTRA_DETAIL",
                                item.login
                            )
                        )
                    }

                    with(binding.rvFollowing) {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = followingAdapter
                    }
                }
            }
        }
        showLoading(false)
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.pbFollowing.visibility = View.VISIBLE
        else binding.pbFollowing.visibility = View.GONE
    }
}