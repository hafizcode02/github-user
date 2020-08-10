package com.hafizcode.githubuserapi.helper

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.AVATAR
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.COMPANY
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.FAVORITE
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.FOLLOWERS
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.FOLLOWING
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.LOCATION
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.NAME
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.REPOSITORY
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.TABLE_NAME
import com.hafizcode.githubuserapi.database.DatabaseContract.UserFavoriteColumns.Companion.USERNAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "final_submission"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME" +
                " ($USERNAME TEXT NOT NULL," +
                " $NAME TEXT NOT NULL," +
                " $AVATAR TEXT NOT NULL," +
                " $COMPANY TEXT NOT NULL," +
                " $LOCATION TEXT NOT NULL," +
                " $REPOSITORY TEXT NOT NULL," +
                " $FOLLOWERS TEXT NOT NULL," +
                " $FOLLOWING TEXT NOT NULL," +
                " $FAVORITE TEXT NOT NULL)"
    }

    @SuppressLint("SQLiteString")
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}