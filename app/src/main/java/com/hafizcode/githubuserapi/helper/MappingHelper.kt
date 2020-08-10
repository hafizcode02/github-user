package com.hafizcode.githubuserapi.helper

import android.database.Cursor
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.AVATAR
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.COMPANY
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.FAVORITE
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.FOLLOWERS
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.FOLLOWING
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.LOCATION
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.NAME
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.REPOSITORY
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.USERNAME
import com.hafizcode.githubuserapi.model.DataFavorite

object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<DataFavorite> {
        val favList = ArrayList<DataFavorite>()

        cursor?.apply {
            while (moveToNext()) {
                val username =
                    getString(getColumnIndexOrThrow(USERNAME))
                val name =
                    getString(getColumnIndexOrThrow(NAME))
                val avatar =
                    getString(getColumnIndexOrThrow(AVATAR))
                val company =
                    getString(getColumnIndexOrThrow(COMPANY))
                val location =
                    getString(getColumnIndexOrThrow(LOCATION))
                val repository =
                    getString(getColumnIndexOrThrow(REPOSITORY))
                val followers =
                    getString(getColumnIndexOrThrow(FOLLOWERS))
                val following =
                    getString(getColumnIndexOrThrow(FOLLOWING))
                val isFav =
                    getString(getColumnIndexOrThrow(FAVORITE))

                favList.add(
                    DataFavorite(
                        username,
                        name,
                        avatar,
                        company,
                        location,
                        repository,
                        followers,
                        following,
                        isFav
                    )
                )
            }
        }
        return favList
    }

}