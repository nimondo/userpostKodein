package com.example.mvvmkodein.ui.postdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkodein.R
import com.example.mvvmkodein.data.db.entities.Post
import com.example.mvvmkodein.databinding.ItemPostDetailBinding

class PostDetailAdapter: RecyclerView.Adapter<PostDetailAdapter.ViewHolder>() {
    private lateinit var postList:List<Post>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostDetailAdapter.ViewHolder {
        val binding: ItemPostDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post_detail, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostDetailAdapter.ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int {
        return if(::postList.isInitialized) postList.size else 0
    }

    fun updatePostList(postList:List<Post>){
        this.postList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemPostDetailBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = PostDetailGetViewModel()

        fun bind(post:Post){
            viewModel.bind(post)
            binding.viewModel = viewModel
        }
    }
}