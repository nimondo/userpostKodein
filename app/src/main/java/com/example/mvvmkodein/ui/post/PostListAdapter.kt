package com.example.mvvmkodein.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkodein.R
import com.example.mvvmkodein.data.db.entities.Post
import com.example.mvvmkodein.databinding.ItemPostBinding

class PostListAdapter(val clickListener: PostListener): RecyclerView.Adapter<PostListAdapter.ViewHolder>() {
    private lateinit var postList:List<Post>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListAdapter.ViewHolder {
        val binding: ItemPostBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostListAdapter.ViewHolder, position: Int) {
        holder.bind(postList[position]!!, clickListener)
    }

    override fun getItemCount(): Int {
        return if(::postList.isInitialized) postList.size else 0
    }

    fun updatePostList(postList:List<Post>){
        this.postList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemPostBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = PostGetViewModel()

        fun bind(
            post: Post,
            clickListener: PostListener
        ){
            viewModel.bind(post)
            binding.viewModel = viewModel
            binding.clickListener = clickListener
        }
    }
}
class PostListener(val clickListener: (postId: Int) -> Unit){
    fun onClick(post: Post) =  clickListener(post.id)
}