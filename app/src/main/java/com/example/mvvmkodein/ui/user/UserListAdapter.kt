package com.example.mvvmkodein.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkodein.R
import com.example.mvvmkodein.data.db.entities.User
import com.example.mvvmkodein.databinding.ItemUserBinding

class UserListAdapter(val clickListener: UserListener): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private lateinit var userList:List<User>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.ViewHolder {
        val binding: ItemUserBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_user, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListAdapter.ViewHolder, position: Int) {
        holder.bind(userList[position]!!, clickListener)
    }

    override fun getItemCount(): Int {
        return if(::userList.isInitialized) userList.size else 0
    }

    fun updateUserList(postList:List<User>){
        this.userList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root){
        private val viewModel = UserRetrieveViewModel()
        fun bind(
            user: User,
            clickListener: UserListener
        ){
            viewModel.bind(user)
            binding.viewModel = viewModel
            binding.clickListener = clickListener
        }
    }
}
class UserListener(val clickListener: (userId: Int) -> Unit){
    fun onClick(user:User) =  clickListener(user.id)
}