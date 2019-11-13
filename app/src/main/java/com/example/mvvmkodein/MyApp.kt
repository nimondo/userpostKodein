package com.example.mvvmkodein

import android.app.Application
import com.example.mvvmkodein.data.db.AppDatabase
import com.example.mvvmkodein.data.network.JsonApi
import com.example.mvvmkodein.di.apiModule
import com.example.mvvmkodein.di.viewModelsModule
import com.example.mvvmkodein.ui.user.UserViewModel
import com.example.mvvmkodein.utils.BASE_URL
import io.reactivex.schedulers.Schedulers
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class MyApp : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidXModule(this@MyApp))
        import(apiModule)
        import(viewModelsModule)
        bind() from singleton { AppDatabase(instance()) }

    }
}