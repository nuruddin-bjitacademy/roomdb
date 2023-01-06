package com.graphicless.createaccountwithroomdatabase.repository

import androidx.lifecycle.LiveData
import com.graphicless.createaccountwithroomdatabase.model.Authentication
import com.graphicless.createaccountwithroomdatabase.model.Post
import com.graphicless.createaccountwithroomdatabase.model.User
import com.graphicless.createaccountwithroomdatabase.model.UserDao

class UserRepository(private val userDao: UserDao) {

    val readAllUser: LiveData<List<User>> = userDao.readAllUser()
    val getAllUserName: LiveData<List<String>> = userDao.getAllUserName()
    val getAllAuthentication: LiveData<List<Authentication>> = userDao.getAllAuthentication()
    val getAllPost: LiveData<List<Post>> = userDao.getAllPost()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun addAuth(authentication: Authentication){
        userDao.addAuth(authentication)
    }

    suspend fun addPost(post: Post){
        userDao.addPost(post)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

}