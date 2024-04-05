package com.example.dicoding_bfaa1.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicoding_bfaa1.R
import com.example.dicoding_bfaa1.data.response.ItemsItem
import com.example.dicoding_bfaa1.databinding.ActivityMainBinding
import com.example.dicoding_bfaa1.ui.detail.UserDetailActivity
import com.example.dicoding_bfaa1.ui.favorite.FavoriteUserActivity
import com.example.dicoding_bfaa1.ui.home.MainViewModel
import com.example.dicoding_bfaa1.ui.home.UserAdapter
import com.example.dicoding_bfaa1.ui.setting.SettingActivity
import com.example.dicoding_bfaa1.ui.setting.SettingPreferences
import com.example.dicoding_bfaa1.ui.setting.datastore
import com.example.dicoding_bfaa1.viewmodelfactory.ThemeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = SettingPreferences.getInstance(application.datastore)
        viewModel = ViewModelProvider(this@MainActivity, ThemeViewModelFactory(preferences)).get(MainViewModel::class.java)

        changeTheme(viewModel)

        setRecyclerView()

        setSearchBar(binding)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.userList.observe(this) { users ->
            setUsersData(users)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings_action -> {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.favorite_action -> {
                val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setSearchBar(binding: ActivityMainBinding) {
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
        movingUsingParcelableIntent.putExtra(UserDetailActivity.USER_EXTRA, data.login)
        startActivity(movingUsingParcelableIntent)
    }

    private fun changeTheme(viewModel: MainViewModel) {
        viewModel.getThemeSetting().observe(this@MainActivity) {isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    fun goToFavorite(view: View) {
        val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
        startActivity(intent)
    }
    fun goToSetting(view: View) {
        val intent = Intent(this@MainActivity, SettingActivity::class.java)
        startActivity(intent)
    }
}