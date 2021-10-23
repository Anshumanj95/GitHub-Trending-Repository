package com.example.gittrendingapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gittrendingapi.databinding.RepoAdapterBinding
import com.example.gittrendingapi.models.Item
import com.example.gittrendingapi.util.GlideApp


class RepoAdapter:
    RecyclerView.Adapter<RepoAdapter.ItemViewHolder>() {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
    val differ= AsyncListDiffer(this, DiffCallback)
    class ItemViewHolder (private var binding: RepoAdapterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Item){
            val expanded=item.expanded
            binding.apply {
                details.visibility=if (expanded)View.VISIBLE else View.GONE
                language.text=item.language
                fork.text=item.forks_count.toString()
                watcher.text=item.watchers_count.toString()
                description.text=item.description
                fullName.text=item.full_name
                name.text=item.name
                GlideApp.with(itemView.context).load(item.owner.avatar_url).into(image)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(RepoAdapterBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current=differ.currentList[position]
        holder.bind(current)
        holder.itemView.setOnClickListener {
            val expanded=current.expanded
            current.expanded=(!expanded)
            notifyItemChanged(position)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}

