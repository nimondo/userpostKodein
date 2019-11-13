package com.example.mvvmkodein.ui.user

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkodein.R
import com.example.mvvmkodein.data.db.entities.User
import com.example.mvvmkodein.data.network.JsonApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import retrofit2.Retrofit

class UserViewModel(appContext: Context): ViewModel(),KodeinAware{
        override val kodein by kodein(appContext)
        private val jsonApi: JsonApi by instance()
    // The current _word
//    lateinit var postApi: JsonApi

    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { getUsers() }
    private lateinit var subscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    private val _word = MutableLiveData<String>()
    val userListAdapter: UserListAdapter = UserListAdapter()
    val word: LiveData<String>
        get() = _word
    init {
        Log.i("UserViewModel", "UserViewModel created!")
        _word.value = "Set the Users API Response here!"
        getUsers()
    }
    private fun getUsers() {
        subscription = jsonApi.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveUserListStart() }
            .doOnTerminate { onRetrieveUserListFinish() }
            .subscribe(
                { result-> onRetrieveUserListSuccess(result) },
                { e->onRetrieveUserListError(e) }
            )

    }
    private fun onRetrieveUserListStart(){
        loadingVisibility.value = View.VISIBLE
        _word.value = "Set the Users API Response here start!"
        errorMessage.value = null
    }

    private fun onRetrieveUserListFinish(){
        loadingVisibility.value = View.GONE
        _word.value = "Set the Users API Response here finish!"
    }

    private fun onRetrieveUserListSuccess(userList:List<User>){
        Log.i("UserViewModel", userList.toString())
        userListAdapter.updateUserList(userList)
    }

    private fun onRetrieveUserListError(e:Throwable){
        Log.i("UserViewModel", e.toString())
        errorMessage.value = R.string.post_error
    }
    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
        Log.i("UserViewModel", "UserViewModel destroyed!")
    }
}