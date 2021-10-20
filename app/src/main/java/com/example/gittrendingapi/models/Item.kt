package com.example.gittrendingapi.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "items")
data class Item(
    @PrimaryKey()
    @SerializedName("id")
    val id:Int,
    @SerializedName("description")
    val description: String?,
    @SerializedName("forks_count")
    val forks_count: Int,
    @SerializedName("full_name")
    val full_name: String,
    @SerializedName("language")
    val language: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("watchers_count")
    val watchers_count: Int,
    var expanded:Boolean=false
):Serializable