package com.hafizcode.githubuserapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hafizcode.githubuserapi.R
import com.hafizcode.githubuserapi.model.DataFollowers
import kotlinx.android.synthetic.main.item_user.view.*

class ListDataFollowerAdapter(private val listDataFollower: ArrayList<DataFollowers>) :
    RecyclerView.Adapter<ListDataFollowerAdapter.ListDataHolder>() {

    fun setData(items: ArrayList<DataFollowers>) {
        listDataFollower.clear()
        listDataFollower.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataFollowers: DataFollowers) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataFollowers.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .error(R.drawable.ic_baseline_face_24)
                    .into(avatar)

                fullName.text = dataFollowers.name
                username.text =
                    itemView.context.getString(R.string.username, dataFollowers.username)
                count_repository.text =
                    itemView.context.getString(R.string.repository, dataFollowers.repository)
                count_followers.text =
                    itemView.context.getString(R.string.follower, dataFollowers.followers)
                count_following.text =
                    itemView.context.getString(R.string.follower, dataFollowers.following)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        return ListDataHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listDataFollower.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listDataFollower[position])
    }
}