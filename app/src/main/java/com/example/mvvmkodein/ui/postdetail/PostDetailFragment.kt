package com.example.mvvmkodein.ui.postdetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mvvmkodein.R
import com.example.mvvmkodein.databinding.FragmentPostDetailBinding
import com.example.mvvmkodein.ui.post.PostFragmentArgs
import com.example.mvvmkodein.ui.post.PostViewModel
import com.example.mvvmkodein.ui.post.PostViewModelFactory
import org.kodein.di.KodeinAware

/**
 * A simple [Fragment] subclass.
 */
class PostDetailFragment : Fragment() {
    private lateinit var binding: FragmentPostDetailBinding
    private lateinit var viewModel: PostDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_post_detail, container, false)
        val arguments = PostDetailFragmentArgs.fromBundle(arguments)
        val viewModelFactory = PostDetailViewModelFactory(arguments.myArg,requireContext())
        binding.postDetailList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PostDetailViewModel::class.java)
        binding.postDetailViewModel= viewModel
        binding.lifecycleOwner = this
        (activity as AppCompatActivity).supportActionBar?.title = "Post Detail"
        return binding.root
    }


}
