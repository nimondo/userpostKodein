package com.example.mvvmkodein.ui.postdetail

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.mvvmkodein.R
import com.example.mvvmkodein.data.db.AppDatabase
import com.example.mvvmkodein.data.db.entities.Post
import com.example.mvvmkodein.data.db.entities.PostDao
import com.example.mvvmkodein.ui.post.PostListAdapter
import com.example.mvvmkodein.ui.post.PostViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class PostDetailViewModel(postid: Int, appContext: Context): ViewModel(), KodeinAware {
    override val kodein by kodein(appContext)
    private val appDatabase: AppDatabase by instance()
    val db = Room.databaseBuilder(appContext.applicationContext, AppDatabase::class.java, "users").build()

    private val postDao: PostDao = db.postDao()
    val postId : Int = postid
    val postListAdapter: PostDetailAdapter = PostDetailAdapter()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    private lateinit var subscription: Disposable
    init {
        Log.i("PostViewModel", "PostViewModel created!"+postId)
        loadPostDetail()
    }
    fun loadPostDetail(){
        subscription = Observable.fromCallable { postDao.getPostDetail(postId)}
            .concatMap {
                    dbPostList ->  Observable.just(dbPostList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListDetailStart() }
            .doOnTerminate { onRetrievePostListDetailFinish() }
            .subscribe(
                // Add result
                { result -> onRetrievePostListDetailSuccess(result) },
                { onRetrievePostListDetailError() }
            )
    }

    private fun onRetrievePostListDetailStart(){
        errorMessage.value = null
    }

    private fun onRetrievePostListDetailFinish(){
    }

    private fun onRetrievePostListDetailSuccess(postList:List<Post>){
        postListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListDetailError(){
        errorMessage.value = R.string.post_error
    }
    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
        Log.i("PostViewModel", "PostViewModel destroyed!")
    }
}