package com.example.mvvmkodein.di

import com.example.mvvmkodein.ui.user.UserViewModel
import com.example.mvvmkodein.ui.user.factory.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

val viewModelsModule = Kodein.Module("view_models_module") {
    bind<ViewModelFactory>() with singleton { ViewModelFactory(instance()) }
    bind<UserViewModel>() with provider { UserViewModel(instance()) }
//    bind<MoviesViewModel>() with provider { MoviesViewModel(instance()) }
//    bind<MovieDetailViewModel>() with provider { MovieDetailViewModel(instance()) }
}