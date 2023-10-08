package com.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.response.FollowersAndFollowingItem
import com.example.mysubmission1.databinding.ItemUserBinding

class FollowersandFollowingAdapter : ListAdapter<FollowersAndFollowingItem, FollowersandFollowingAdapter.MyViewHolder>(DIFF_CALLBACK){
    class MyViewHolder (private val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: FollowersAndFollowingItem) {
            fun ImageView.loadImage(url: String) {
                Glide.with(this.context)
                    .load(url)
                    .centerCrop()
                    .into(this)
            }
            binding.apply {
                tvItemUsername.text = user.login
                userPict.loadImage(
                    url = user.avatarUrl
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowersAndFollowingItem>(){
            override fun areItemsTheSame(
                oldItem: FollowersAndFollowingItem,
                newItem: FollowersAndFollowingItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FollowersAndFollowingItem,
                newItem: FollowersAndFollowingItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}