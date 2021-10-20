package com.example.gittrendingapi.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gittrendingapi.models.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item):Long

    @Query("select * from items")
    fun getAll():LiveData<List<Item>>

    @Query("select * from items order by forks_count asc")
    fun getAllInFork():LiveData<List<Item>>

    @Query("select * from items order by watchers_count asc")
    fun getAllInWatcher():LiveData<List<Item>>

    @Query("delete from items")
    suspend fun deleteAll()
}