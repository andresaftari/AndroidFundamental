package com.andresaftari.apiexample.ui.follows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andresaftari.apiexample.databinding.FragmentFollowersBinding
import com.andresaftari.apiexample.ui.detail.DetailActivity
import com.andresaftari.apiexample.utils.FollowsAdapter

class FollowersFragment : Fragment() {
    private lateinit var followerAdapter: FollowsAdapter
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var viewModel: FollowViewModel
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(FollowViewModel::class.java)
        binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)

        if (requireActivity().intent.hasExtra("EXTRA_DETAIL")) {
            username = requireActivity().intent.getStringExtra("EXTRA_DETAIL")!!
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        with(viewModel) {
            getFollowers(username)
            follower.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    followerAdapter = FollowsAdapter(it) { _, item ->
                        startActivity(
                            Intent(requireContext(), DetailActivity::class.java).putExtra(
                                "EXTRA_DETAIL",
                                item.login
                            )
                        )
                    }

                    with(binding.rvFollowers) {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = followerAdapter
                    }
                }
            }
        }
        showLoading(false)
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.pbFollowers.visibility = View.VISIBLE
        else binding.pbFollowers.visibility = View.GONE
    }
}