package com.example.mvvmkodein.ui.post


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mvvmkodein.R
import com.example.mvvmkodein.databinding.FragmentPostBinding
import com.example.mvvmkodein.ui.user.UserFragmentDirections
import com.example.mvvmkodein.ui.user.factory.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinContext
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

/**
 * A simple [Fragment] subclass.
 */
class PostFragment : Fragment() , KodeinAware {
    override val kodein by kodein()
    private val factory: ViewModelFactory by instance()
    private lateinit var mbinding: FragmentPostBinding
    private lateinit var viewModel: PostViewModel
    private var errorSnackbar: Snackbar? = null
    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int>
        get() = _userId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_post,
            container,
            false
        )
        val arguments = PostFragmentArgs.fromBundle(arguments)
        val viewModelFactory = PostViewModelFactory(arguments.myArgs,requireContext())
        mbinding.userPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PostViewModel::class.java)
        mbinding.postViewModel = viewModel
        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        mbinding.lifecycleOwner = this
        viewModel.navigateToPostDetail.observe(this, Observer { user ->
            user?.let {

                this.findNavController().navigate(

                    PostFragmentDirections
                        .actionPostFragmentToPostDetailFragment(user))
                viewModel.onPostNavigated()
            }
        })
        (activity as AppCompatActivity).supportActionBar?.title = "User Post List"
        return mbinding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
////        val tv: TextView = view.findViewById(R.id.post_text)
////        val amount = "hi"+ PostFragmentArgs.fromBundle(arguments).myArgs
////        tv.text = amount.toString()
////    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(mbinding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }

}
