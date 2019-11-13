package com.example.mvvmkodein.di

import com.example.mvvmkodein.data.network.JsonApi
import com.example.mvvmkodein.databinding.ActivityMainBinding.bind
import com.example.mvvmkodein.utils.BASE_URL
import io.reactivex.schedulers.Schedulers
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val apiModule = Kodein.Module {
    bind<Retrofit>() with singleton {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .baseUrl(BASE_URL)
            .build()

    }
    bind<JsonApi>() with singleton {
        val retrofit: Retrofit = instance()
        retrofit.create(JsonApi::class.java)
    }
}