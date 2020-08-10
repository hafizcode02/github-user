package com.hafizcode.githubuserapi.view

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hafizcode.githubuserapi.R
import com.hafizcode.githubuserapi.adapter.ListDataFavouriteAdapter
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.CONTENT_URI
import com.hafizcode.githubuserapi.helper.MappingHelper
import com.hafizcode.githubuserapi.helper.QueryHelper
import com.hafizcode.githubuserapi.model.DataFavorite
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: ListDataFavouriteAdapter
    private lateinit var dbHelper: QueryHelper

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        dbHelper = QueryHelper.getInstance(applicationContext)
        dbHelper.open()

        recyclerViewFav.layoutManager = LinearLayoutManager(this)
        recyclerViewFav.setHasFixedSize(true)
        adapter = ListDataFavouriteAdapter(this)
        recyclerViewFav.adapter = adapter

        val handleThread = HandlerThread("DataObserver")
        handleThread.start()
        val handler = Handler(handleThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadListFavourite()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadListFavourite()
        } else {
            val list = savedInstanceState.getParcelableArrayList<DataFavorite>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavourite = list
            }
        }

        if (supportActionBar != null) {
            supportActionBar?.title = getString(R.string.title_menu_favorite)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavourite)
    }

    private fun loadListFavourite() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val deferredFav = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favData = deferredFav.await()
            progressbar.visibility = View.INVISIBLE
            if (favData.size > 0) {
                adapter.listFavourite = favData
            } else {
                adapter.listFavourite = ArrayList()
                showSnack()
            }
        }
    }

    private fun showSnack() {
        Snackbar.make(recyclerViewFav, "Data Is Null", Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        loadListFavourite()
    }
}