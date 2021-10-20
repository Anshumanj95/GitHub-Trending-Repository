package com.example.gittrendingapi

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gittrendingapi.models.ApiResponse
import com.example.gittrendingapi.models.Item
import com.example.gittrendingapi.repository.ItemRepository
import com.example.gittrendingapi.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class RepositoryViewModel(app:Application,val itemRepository: ItemRepository):AndroidViewModel(app) {

    val repositories:MutableLiveData<Resource<ApiResponse>> = MutableLiveData()
    val allrepo:LiveData<List<Item>> =itemRepository.getAll()
    val allRepoByFork:LiveData<List<Item>> =itemRepository.getByFork()
    val allRepoByWatcher:LiveData<List<Item>> =itemRepository.getByWatcher()

    init {
        getApiCall()
    }

    fun getApiCall()=viewModelScope.launch {
        safeApiCall()
    }
    private suspend fun  safeApiCall(){
        repositories.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = itemRepository.getRepository()
                    for (element in response.body()!!.items) {
                        itemRepository.insert(element)
                    }
                repositories.postValue(handleSafeApiResponse(response))
            }else{
                repositories.postValue(Resource.Error("No Internet Connection"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException -> repositories.postValue(Resource.Error("Network failure"))
                else -> repositories.postValue(Resource.Error("conversion Error"))
            }
        }
    }
    private fun handleSafeApiResponse(response: Response<ApiResponse>):Resource<ApiResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }


    private fun hasInternetConnection():Boolean{
        val connectivityManager=getApplication<RepoApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        )as ConnectivityManager
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            val activeNetwork= connectivityManager.activeNetwork?:return false
            val capabilities=connectivityManager.getNetworkCapabilities(activeNetwork)?:return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
                else->false
            }
        }
        return false
    }
}