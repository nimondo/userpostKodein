package com.example.mvvmkodein.ui.postdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkodein.data.db.entities.Post

class PostDetailGetViewModel: ViewModel() {
    private val postTitle = MutableLiveData<String>()
    private val postBody = MutableLiveData<String>()

    fun bind(post: Post){
        postTitle.value = post.title
        postBody.value = post.body
    }

    fun getPostDetailTitle():MutableLiveData<String>{
        return postTitle
    }

    fun getPostDetailBody():MutableLiveData<String>{
        return postBody
    }
}