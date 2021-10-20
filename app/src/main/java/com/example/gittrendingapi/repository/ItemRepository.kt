package com.example.gittrendingapi.repository

import com.example.gittrendingapi.api.RetrofitInstance
import com.example.gittrendingapi.db.ItemDatabase
import com.example.gittrendingapi.models.Item

class ItemRepository(val db:ItemDatabase) {

    suspend fun getRepository()=
        RetrofitInstance.api.getRepository()


    suspend fun insert(item:Item)=db.getItemDao().insert(item)

    fun getAll()=db.getItemDao().getAll()

    fun getByFork()=db.getItemDao().getAllInFork()

    fun getByWatcher()=db.getItemDao().getAllInWatcher()

    suspend fun deleteAll()=db.getItemDao().deleteAll()

}