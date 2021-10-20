package com.example.gittrendingapi.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gittrendingapi.models.Item

@Database(entities = [Item::class],version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class ItemDatabase:RoomDatabase() {

    abstract fun getItemDao():ItemDao

    companion object{
        @Volatile
        private var instance:ItemDatabase?=null
        private val Lock=Any()

        operator fun invoke(context: Context)= instance?: synchronized(Lock){
            instance?:createDatabse(context).also{ instance=it}
        }
        private fun createDatabse(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,ItemDatabase::class.java,
                "item_db.db"
            ).build()
    }

}