package com.example.mvvmkodein.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmkodein.data.db.entities.Post
import com.example.mvvmkodein.data.db.entities.PostDao
import com.example.mvvmkodein.data.db.entities.User
import com.example.mvvmkodein.data.db.entities.UserDao

@Database(entities = arrayOf(User::class, Post::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object {
        /**
         * Volatile means, this object is immediately visible to all threads.
         * If any thread needs this object instance then they will get the instance from memory
         * instead of getting it from cache.
         */
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "users_post.db"
            ).build()

    }

}