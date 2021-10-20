package com.example.gittrendingapi.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gittrendingapi.RepositoryViewModel
import com.example.gittrendingapi.repository.ItemRepository

class RepositoryViewModelFactory(
    val app: Application,
    val itemRepository: ItemRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RepositoryViewModel(app,itemRepository) as T
    }

}