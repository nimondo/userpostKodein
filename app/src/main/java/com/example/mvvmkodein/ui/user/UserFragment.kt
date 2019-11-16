package com.example.mvvmkodein.ui.user


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mvvmkodein.R
import com.example.mvvmkodein.databinding.FragmentUserBinding
import com.example.mvvmkodein.ui.user.factory.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.x.kodein

/**
 * A simple [Fragment] subclass.
 */
class UserFragment : Fragment(),KodeinAware {
    override val kodein by kodein()
    private val factory: ViewModelFactory by instance()
    private lateinit var binding: FragmentUserBinding
    private lateinit var viewModel: UserViewModel
    private var errorSnackbar: Snackbar? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user,
            container,
            false
        )
        binding.userList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        val adapter = UserListAdapter(UserListener{ userId ->
//            Toast.makeText(context, "${userId}", Toast.LENGTH_LONG).show()
//        })
//        binding.userList.adapter = adapter
        viewModel = ViewModelProviders.of(this,factory).get(UserViewModel::class.java)
        binding.userViewModel = viewModel
        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.lifecycleOwner = this
        viewModel.navigateToUserPost.observe(this, Observer { user ->
            user?.let {

                this.findNavController().navigate(

                    UserFragmentDirections
                        .actionUserFragmentToPostFragment(user))
                viewModel.onUserNavigated()
            }
        })
        return binding.root
    }
    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }


}
