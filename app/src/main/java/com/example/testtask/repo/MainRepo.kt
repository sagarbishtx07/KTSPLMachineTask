package com.example.testtask.repo

import androidx.lifecycle.LiveData
import com.example.testtask.api.SportsApi
import com.example.testtask.db.SportsDatabase
import com.example.testtask.model.SportListApiResponse
import com.example.testtask.model.SportsTable

class MainRepo(val db: SportsDatabase, val api: SportsApi) {
    suspend fun getAllSports(): SportListApiResponse? {
        return try {
            api.getAllSports()
        }catch (_:Exception){
            null
        }
    }

    fun getAllSportsDB(): LiveData<List<SportsTable>> {
        return db.getSportsDao().getAllSports()
    }

    suspend fun insertAllSports(sports: List<SportsTable>){
        db.getSportsDao().insertAllSports(sports)
    }

    suspend fun deleteAllSports(){
        db.getSportsDao().deleteAllSports()
    }

    suspend fun deleteSingleSport(data: SportsTable){
        db.getSportsDao().deleteSingleSport(data.id)
    }

}