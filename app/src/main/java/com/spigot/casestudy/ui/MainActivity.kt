package com.spigot.casestudy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.spigot.casestudy.adapter.PhotoAdapter
import com.spigot.casestudy.databinding.ActivityMainBinding
import com.spigot.casestudy.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var photoAdapter: PhotoAdapter

    private val photoViewModel by viewModels<PhotoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpRecyclerView()

        loadPhotoInAdapter()
    }

    private fun setUpRecyclerView(){
    photoAdapter = PhotoAdapter()
    binding.rvPhotoList.adapter = photoAdapter
    binding.rvPhotoList.layoutManager = LinearLayoutManager(this)

        photoAdapter.addLoadStateListener { loadState ->
        binding.progress.isVisible = loadState.refresh is LoadState.Loading
        }
    }

    private fun loadPhotoInAdapter() {
         lifecycleScope.launch {
            photoViewModel.getAllPhotos.collectLatest {
                photoAdapter.submitData(it)
            }
        }
    }
}