package com.hafizcode.githubuserapi.view

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hafizcode.githubuserapi.R
import com.hafizcode.githubuserapi.adapter.ViewPagerDetailAdapter
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.AVATAR
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.COMPANY
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.CONTENT_URI
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.FAVORITE
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.FOLLOWERS
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.FOLLOWING
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.LOCATION
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.NAME
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.REPOSITORY
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.USERNAME
import com.hafizcode.githubuserapi.helper.QueryHelper
import com.hafizcode.githubuserapi.model.DataUsers
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    private lateinit var dbHelper: QueryHelper
    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = QueryHelper.getInstance(applicationContext)
        dbHelper.open()

        val usernameVal = intent.getParcelableExtra(EXTRA_DETAIL) as DataUsers
        val cursor: Cursor = dbHelper.queryByUsername(usernameVal.username.toString())
        if (cursor.moveToNext()) {
            statusFavorite = true
            setStatusFavorite(true)
        }

        if (supportActionBar != null) {
            supportActionBar?.title = getString(R.string.detail_user)
        }

        setData()
        fab_favorite.setOnClickListener(this)
        viewPagerConfig()
    }

    private fun setStatusFavorite(status: Boolean) {
        if (status) {
            fab_favorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            fab_favorite.setImageResource(R.drawable.ic_baseline_unfavorite_24)
        }
    }

    private fun viewPagerConfig() {
        val viewPagerDetail =
            ViewPagerDetailAdapter(
                this,
                supportFragmentManager
            )
        viewpager.adapter = viewPagerDetail
        tabs.setupWithViewPager(viewpager)
        supportActionBar?.elevation = 0f
    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra(EXTRA_DETAIL) as DataUsers
        Glide.with(this)
            .load(dataUser.avatar)
            .apply(RequestOptions().override(150, 130))
            .into(avatars)
        fullName.text = dataUser.name
        username.text = getString(R.string.username, dataUser.username)
        company.text = dataUser.company
        location.text = dataUser.location
        following.text = dataUser.following
        followers.text = dataUser.followers
        repositories.text = dataUser.repository
    }

    override fun onClick(v: View?) {
        val data = intent.getParcelableExtra(EXTRA_DETAIL) as DataUsers
        when (v?.id) {
            R.id.fab_favorite -> {
                if (statusFavorite) {
                    val idUser = data.username.toString()
                    dbHelper.deleteById(idUser)
                    Toast.makeText(this, "Data Deleted from Favorite", Toast.LENGTH_SHORT).show()
                    setStatusFavorite(false)
                    statusFavorite = true
                } else {

                    val values = ContentValues()
                    values.put(USERNAME, data.username)
                    values.put(NAME, data.name)
                    values.put(AVATAR, data.avatar)
                    values.put(COMPANY, data.company)
                    values.put(LOCATION, data.location)
                    values.put(REPOSITORY, data.repository)
                    values.put(FOLLOWERS, data.followers)
                    values.put(FOLLOWING, data.following)
                    values.put(FAVORITE, "isFav")

                    statusFavorite = false
                    contentResolver.insert(CONTENT_URI, values)
                    Toast.makeText(this, "Data Added to Favorite", Toast.LENGTH_SHORT).show()
                    setStatusFavorite(true)
                }
            }
        }
    }

}
