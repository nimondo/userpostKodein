package com.example.mvvmkodein.data.db.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDao {
    @Query("SELECT * FROM post WHERE userId = :userId")
    fun getUserPosts(userId: Int): Array<Post>

    @Insert
    fun insertAll(vararg posts: Post)
}