package com.example.mvvmkodein.ui.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkodein.data.db.entities.Post
import com.example.mvvmkodein.data.db.entities.User

class PostGetViewModel: ViewModel() {
    private val postTitle = MutableLiveData<String>()
    private val postAll= MutableLiveData<Post>()

    fun bind(post: Post){
        postTitle.value = post.title
        postAll.value = post
    }

    fun getPostTitle():MutableLiveData<String>{
        return postTitle
    }

    fun getPostAll():MutableLiveData<Post>{
        return postAll
    }
}