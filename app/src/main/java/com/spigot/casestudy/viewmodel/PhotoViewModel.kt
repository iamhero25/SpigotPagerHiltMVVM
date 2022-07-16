package com.spigot.casestudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.spigot.casestudy.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class PhotoViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val getAllPhotos = repository.getAllPhotos()

}