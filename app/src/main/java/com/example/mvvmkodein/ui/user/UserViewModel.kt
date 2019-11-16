package com.example.mvvmkodein.ui.user

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.mvvmkodein.R
import com.example.mvvmkodein.data.db.AppDatabase
import com.example.mvvmkodein.data.db.entities.User
import com.example.mvvmkodein.data.db.entities.UserDao
import com.example.mvvmkodein.data.network.JsonApi
import io.reactivex.Observable
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
        private val appDatabase: AppDatabase by instance()
        val db = Room.databaseBuilder(appContext.applicationContext, AppDatabase::class.java, "users").build()
    // The current _word
//    lateinit var postApi: JsonApi

    private val userDao: UserDao= db.userDao()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { getUsers() }
    private lateinit var subscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val adapter = UserListener{ userId ->
        Toast.makeText(appContext, "${userId}", Toast.LENGTH_LONG).show()
        onUserClicked(userId)
    }
    val userListAdapter: UserListAdapter = UserListAdapter(adapter)
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word
    private val _navigateToUserPost = MutableLiveData<Int>()
    val navigateToUserPost
        get() = _navigateToUserPost

    init {
        Log.i("UserViewModel", "UserViewModel created!")
        _word.value = "Set the Users API Response here!"
        getUsers()
    }
    private fun getUsers() {
        subscription = Observable.fromCallable { userDao.all }
            .concatMap {
                    dbUserList ->
                if(dbUserList.isEmpty())
                    jsonApi.getUsers().concatMap {
                            apiUserList -> userDao.insertAll(*apiUserList.toTypedArray())
                        Observable.just(apiUserList)
                    }
                else
                    Observable.just(dbUserList)
            }
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
    fun onUserClicked(id: Int) {
        _navigateToUserPost.value = id
    }
    fun onUserNavigated() {
        _navigateToUserPost.value = null
    }
    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
        Log.i("UserViewModel", "UserViewModel destroyed!")
    }
}