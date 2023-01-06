package com.graphicless.createaccountwithroomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.graphicless.createaccountwithroomdatabase.model.Authentication
import com.graphicless.createaccountwithroomdatabase.model.Post
import com.graphicless.createaccountwithroomdatabase.model.User
import com.graphicless.createaccountwithroomdatabase.model.UserDao

@Database(entities = [User::class, Authentication::class, Post::class], version = 6, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {


    abstract fun getDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }

        }
    }
}