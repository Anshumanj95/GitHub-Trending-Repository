package com.example.gittrendingapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import com.example.gittrendingapi.db.ItemDatabase
import com.example.gittrendingapi.repository.ItemRepository
import com.example.gittrendingapi.util.RepositoryViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: RepositoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setSupportActionBar(findViewById(R.id.toolbar))

        val repository=ItemRepository(ItemDatabase(this))
        val viewModelProviderFactory=RepositoryViewModelFactory(application,repository)
        viewModel=ViewModelProvider(this,viewModelProviderFactory).get(RepositoryViewModel::class.java)


    }
}