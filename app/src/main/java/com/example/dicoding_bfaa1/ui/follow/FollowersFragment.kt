package com.example.dicoding_bfaa1.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicoding_bfaa1.data.response.ItemsItem
import com.example.dicoding_bfaa1.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var viewModel: FollowersViewModel

    private var position: Int = 0
    private var username: String? = ""

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "current_user"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersViewModel::class.java)

        showRecyclerView()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1) {
            viewModel.followersList.observe(viewLifecycleOwner) {
                setFollowersData(it)
            }
            viewModel.getFollowers(username!!)
        } else {
            viewModel.followingList.observe(viewLifecycleOwner) {
                setFollowersData(it)
            }
            viewModel.getFollowing(username!!)
        }
    }

    private fun showRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar3.visibility = View.VISIBLE
        } else {
            binding.progressBar3.visibility = View.GONE
        }
    }

    private fun setFollowersData(user: List<ItemsItem>) {
        val adapter = FollowersAdapter()
        adapter.submitList(user)
        binding.recyclerView.adapter = adapter
    }
}