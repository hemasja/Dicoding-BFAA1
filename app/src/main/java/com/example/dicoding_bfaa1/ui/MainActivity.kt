package com.example.dicoding_bfaa1.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicoding_bfaa1.data.response.ItemsItem
import com.example.dicoding_bfaa1.databinding.ActivityMainBinding
import com.example.dicoding_bfaa1.ui.detail.UserDetailActivity
import com.example.dicoding_bfaa1.ui.home.MainViewModel
import com.example.dicoding_bfaa1.ui.home.UserAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()

        setSearchBar()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.userList.observe(this) { users ->
            setUsersData(users)
        }


    }

    fun setSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    viewModel.findGithubUsers(searchView.text.toString())
                    false
                }
        }
    }

    fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
    }

    private fun setUsersData(users: List<ItemsItem>) {
        val adapter = UserAdapter(users)
        adapter.submitList(users)
        binding.rvUsers.adapter = adapter

        adapter.setOnClickedItemCallback(object : UserAdapter.OnClickedItemCallback {
            override fun onClickedItem(data: ItemsItem) {
                displaySelectedUser(data)
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun displaySelectedUser(data: ItemsItem) {
        val movingUsingParcelableIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
        movingUsingParcelableIntent.putExtra(UserDetailActivity.USER_EXTRA, data)
        startActivity(movingUsingParcelableIntent)
    }
}