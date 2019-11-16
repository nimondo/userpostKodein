package com.example.mvvmkodein.ui.post

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.mvvmkodein.R
import com.example.mvvmkodein.data.db.AppDatabase
import com.example.mvvmkodein.data.db.entities.Post
import com.example.mvvmkodein.data.db.entities.PostDao
import com.example.mvvmkodein.data.network.JsonApi
import com.example.mvvmkodein.ui.user.UserListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class PostViewModel(userid: Int, appContext: Context): ViewModel(), KodeinAware {
    override val kodein by kodein(appContext)
    private val jsonApi: JsonApi by instance()
//    private val appDatabase: AppDatabase by instance()
//    val db = Room.databaseBuilder(appContext.applicationContext, AppDatabase::class.java, "users").build()
//
//    private val postDao: PostDao = db.postDao()
    val postListAdapter: PostListAdapter = PostListAdapter()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    private lateinit var subscription: Disposable
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    init {
        Log.i("PostViewModel", "PostViewModel created!")
        _word.value = "Set the Users API Response here!"+userid
        loadPosts()
    }
    private fun loadPosts(){
        subscription =   jsonApi.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result -> onRetrievePostListSuccess(result) },
                { onRetrievePostListError() }
            )
    }

    private fun onRetrievePostListStart(){
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrievePostListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList:List<Post>){
        postListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError(){
        errorMessage.value = R.string.posts_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
        Log.i("PostViewModel", "PostViewModel destroyed!")
    }
}