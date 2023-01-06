package com.graphicless.createaccountwithroomdatabase.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graphicless.createaccountwithroomdatabase.model.User
import com.graphicless.createaccountwithroomdatabase.model.UserDao
import com.graphicless.createaccountwithroomdatabase.database.UserDatabase
import com.graphicless.createaccountwithroomdatabase.model.Authentication
import com.graphicless.createaccountwithroomdatabase.model.Post
import com.graphicless.createaccountwithroomdatabase.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val auth: MutableLiveData<Authentication>? = null
    var allUser: LiveData<List<User>>
    var allUserName: LiveData<List<String>>
    var allAuthentication: LiveData<List<Authentication>>
    var allPost: LiveData<List<Post>>
     var loggedIn: MutableLiveData<Boolean>? = null


    private val repository: UserRepository
    private var  userDao: UserDao

    init {
        userDao = UserDatabase.getDatabase(application).getDao()
        repository = UserRepository(userDao)
        allUser = repository.readAllUser
        allUserName = repository.getAllUserName
        allAuthentication = repository.getAllAuthentication
        allPost = repository.getAllPost
        loggedIn?.value = false
    }

    fun addUser(user:User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun addAuth(authentication: Authentication){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAuth(authentication)
        }
    }

    fun addPost(post:Post){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPost(post)
        }
    }

    fun updateUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUser(user)
        }
    }


//    fun login(username: String, password: String): MutableLiveData<Boolean>? {
//        val credential = Authentication(username, password)
//        loggedIn?.value = allAuthentication.value?.contains(credential)
//        Log.d("HomeFragment", "login: ${allAuthentication.value}")
//        if(loggedIn?.value == true)
//            auth?.value = credential
//        return loggedIn

//    }

//    fun login(username: String, password: String):LiveData<Boolean>{
//        return true
//
//    }


}