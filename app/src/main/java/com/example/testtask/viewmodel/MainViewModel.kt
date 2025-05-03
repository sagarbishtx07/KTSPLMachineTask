package com.example.testtask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.model.SportListApiResponse
import com.example.testtask.model.SportsTable
import com.example.testtask.repo.MainRepo
import kotlinx.coroutines.launch

class MainViewModel(
    val repo: MainRepo,
) :ViewModel(){
    fun getAllSportsNetwork(): MutableLiveData<SportListApiResponse?> {
        val data = MutableLiveData<SportListApiResponse?>()
        viewModelScope.launch {
           data.postValue(repo.getAllSports())
        }
        return data
    }

    fun insertAllSports(sports: List<SportsTable>){
        viewModelScope.launch {
            repo.deleteAllSports()
            repo.insertAllSports(sports)
        }
    }

    fun getAllSportsDB(): LiveData<List<SportsTable>> {
        return repo.getAllSportsDB()
    }

    fun deleteSingleSport(data: SportsTable){
        viewModelScope.launch {
            repo.deleteSingleSport(data)

        }

    }
}