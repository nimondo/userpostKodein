package com.example.mvvmkodein.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmkodein.data.db.entities.User

class UserRetrieveViewModel: ViewModel() {
    private val userName = MutableLiveData<String>()
    private val userEmail = MutableLiveData<String>()
    private val userAll= MutableLiveData<User>()

    fun bind(user:User){
        userName.value = user.name
        userEmail.value = user.email
        userAll.value = user
    }

    fun getUserName():MutableLiveData<String>{
        return userName
    }

    fun getUserMail():MutableLiveData<String>{
        return userEmail
    }
    fun getUserAll():MutableLiveData<User>{
        return userAll
    }
}