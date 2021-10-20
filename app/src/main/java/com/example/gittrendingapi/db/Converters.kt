package com.example.gittrendingapi.db

import androidx.room.TypeConverter
import com.example.gittrendingapi.models.License
import com.example.gittrendingapi.models.Owner
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromOwner(owner: Owner):String{
        return owner.avatar_url
    }
    @TypeConverter
    fun toString(name:String):Owner{
        return Owner(name)
    }
}