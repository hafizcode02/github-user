package com.hafizcode.githubuserapi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hafizcode.githubuserapi.R
import com.hafizcode.githubuserapi.model.DataUsers
import com.hafizcode.githubuserapi.view.DetailActivity
import kotlinx.android.synthetic.main.item_user.view.*

class ListDataUsersAdapter(private val listDataUsersGithub: ArrayList<DataUsers>) :
    RecyclerView.Adapter<ListDataUsersAdapter.ListDataHolder>() {

    fun setData(items: ArrayList<DataUsers>) {
        listDataUsersGithub.clear()
        listDataUsersGithub.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataUsers: DataUsers) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataUsers.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .error(R.drawable.ic_baseline_face_24)
                    .into(avatar)

                fullName.text = dataUsers.name
                username.text = itemView.context.getString(R.string.username, dataUsers.username)
                count_repository.text =
                    itemView.context.getString(R.string.repository, dataUsers.repository)
                count_followers.text =
                    itemView.context.getString(R.string.follower, dataUsers.followers)
                count_following.text =
                    itemView.context.getString(R.string.followings, dataUsers.following)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        return ListDataHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listDataUsersGithub.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listDataUsersGithub[position])

        val data = listDataUsersGithub[position]
        holder.itemView.setOnClickListener {
            val dataUserIntent = DataUsers(
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
            mIntent.putExtra(DetailActivity.EXTRA_DETAIL, dataUserIntent)
            it.context.startActivity(mIntent)
        }
    }


}