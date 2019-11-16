package com.example.mvvmkodein.data.network

import com.example.mvvmkodein.data.db.entities.Post
import com.example.mvvmkodein.data.db.entities.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface JsonApi {
    /**
     * Get the list of the pots from the API
     */
    @GET("/users")
    fun getUsers(): Observable<List<User>>
    @GET("/posts")
    fun getPosts(): Observable<List<Post>>
//    @GET("/posts")
//    fun getPosts(
//        @Path("userId") userId: Int
//    ): Observable<List<Post>>>


}