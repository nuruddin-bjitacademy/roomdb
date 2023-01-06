package com.graphicless.createaccountwithroomdatabase.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Insert()
    suspend fun addAuth(authentication: Authentication)

    @Insert()
    suspend fun addPost(post: Post)

    @Query("SELECT userName FROM auth_table")
    fun getAllUserName(): LiveData<List<String>>

    @Query("SELECT * FROM auth_table")
    fun getAllAuthentication(): LiveData<List<Authentication>>

    @Query("SELECT * FROM user_table")
    fun readAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM post_table")
    fun getAllPost(): LiveData<List<Post>>



}