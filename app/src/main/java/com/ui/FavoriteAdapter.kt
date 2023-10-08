package com.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.response.ItemsItem
import com.example.mysubmission1.databinding.ItemUserBinding

class FavoriteAdapter (private val list: MutableList<ItemsItem> = mutableListOf()) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>()  {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    fun setList(data: MutableList<ItemsItem>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }


    inner class FavoriteViewHolder( val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .into(userPict)
                tvItemUsername.text = user.login
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder((view))
    }


    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val user = list[position]
        holder.bind(user)
    }

    override fun getItemCount() = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: ItemsItem)
    }
}
