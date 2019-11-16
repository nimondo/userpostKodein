package com.example.mvvmkodein.ui.postdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmkodein.ui.post.PostViewModel

class PostDetailViewModelFactory(val postId: Int, val appContext: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostDetailViewModel(postId, appContext) as T
    }
}