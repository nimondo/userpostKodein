package com.example.mvvmkodein.ui.user.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmkodein.MyApp
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.TT
import org.kodein.di.direct

class ViewModelFactory(appContext: Context) : ViewModelProvider.Factory, KodeinAware {
    override val kodein: Kodein = (appContext as MyApp).kodein // we can use `by kodein(appContext)` also
    override fun <T : ViewModel> create(modelClass: Class<T>): T = kodein.direct.Instance(TT(modelClass))
}