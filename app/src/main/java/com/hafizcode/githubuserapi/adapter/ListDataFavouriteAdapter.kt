package com.hafizcode.githubuserapi.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hafizcode.githubuserapi.R
import com.hafizcode.githubuserapi.model.DataFavorite
import com.hafizcode.githubuserapi.model.DataUsers
import com.hafizcode.githubuserapi.view.DetailActivity
import kotlinx.android.synthetic.main.item_user.view.*

class ListDataFavouriteAdapter(private val activity: Activity) :
    RecyclerView.Adapter<ListDataFavouriteAdapter.ListViewHolder>() {

    var listFavourite = ArrayList<DataFavorite>()
        set(listFavourite) {
            if (listFavourite.size > 0) {
                this.listFavourite.clear()
            }
            this.listFavourite.addAll(listFavourite)
            notifyDataSetChanged()
        }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: DataFavorite) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(favorite.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .error(R.drawable.ic_baseline_face_24)
                    .into(avatar)

                fullName.text = favorite.name
                username.text = itemView.context.getString(R.string.username, favorite.username)
                count_repository.text =
                    itemView.context.getString(R.string.repository, favorite.repository)
                count_following.text =
                    itemView.context.getString(R.string.followings, favorite.following)
                count_followers.text =
                    itemView.context.getString(R.string.follower, favorite.followers)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listFavourite.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFavourite[position])

        val data = listFavourite[position]
        holder.itemView.setOnClickListener {
            val dataUser = DataUsers(
                data.username,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.followers,
                data.following
            )
            val mIntent = Intent(it.context, DetailActivity::class.java)
            mIntent.putExtra(DetailActivity.EXTRA_DETAIL, dataUser)
            it.context.startActivity(mIntent)
        }
    }
}