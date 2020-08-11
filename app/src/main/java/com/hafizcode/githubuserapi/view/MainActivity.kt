package com.hafizcode.githubuserapi.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hafizcode.githubuserapi.R
import com.hafizcode.githubuserapi.adapter.ListDataUsersAdapter
import com.hafizcode.githubuserapi.model.DataUsers
import com.hafizcode.githubuserapi.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listDataUser: ArrayList<DataUsers> = ArrayList()
    private lateinit var listAdapter: ListDataUsersAdapter
    private lateinit var mainViewModel: MainViewModel

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listAdapter =
            ListDataUsersAdapter(listDataUser)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        searchData()
        viewConfig()
        runGetDataGit()
        configMainViewModel(listAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
                val move = Intent(this, FavoriteActivity::class.java)
                startActivity(move)
            }
            R.id.setting_menu -> {
                val move = Intent(this, SettingsActivity::class.java)
                startActivity(move)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun viewConfig() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        listAdapter.notifyDataSetChanged()
        recyclerView.adapter = listAdapter
    }

    private fun runGetDataGit() {
        mainViewModel.getDataGit(applicationContext)
        showLoading(true)
    }


    private fun configMainViewModel(adapter: ListDataUsersAdapter) {
        mainViewModel.getListUsers().observe(this, Observer { listUsers ->
            if (listUsers != null) {
                adapter.setData(listUsers)
                showLoading(false)
            }
        })
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            loadingProgress.visibility = View.VISIBLE
        } else {
            loadingProgress.visibility = View.INVISIBLE
        }
    }

    private fun searchData() {
        user_search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    listDataUser.clear()
                    viewConfig()
                    mainViewModel.getDataGitSearch(query, applicationContext)
                    showLoading(true)
                    configMainViewModel(listAdapter)
                } else {
                    return true
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
    }

}
