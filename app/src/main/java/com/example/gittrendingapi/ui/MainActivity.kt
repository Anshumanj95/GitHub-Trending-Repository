package com.example.gittrendingapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.gittrendingapi.R
import com.example.gittrendingapi.db.ItemDatabase
import com.example.gittrendingapi.repository.ItemRepository
import com.example.gittrendingapi.ui.viewmodel.RepositoryViewModel
import com.example.gittrendingapi.ui.viewmodel.RepositoryViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: RepositoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository=ItemRepository(ItemDatabase(this))
        val viewModelProviderFactory= RepositoryViewModelFactory(application,repository)
        viewModel=ViewModelProvider(this,viewModelProviderFactory).get(RepositoryViewModel::class.java)


    }
}