package com.example.testtask.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testtask.model.SportsTable

@Dao
interface SportsDao {

    @Query("SELECT * FROM sports")
    fun getAllSports(): LiveData<List<SportsTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSports(sports: List<SportsTable>)

    @Query("DELETE FROM sports")
    suspend fun deleteAllSports()

    @Query("DELETE FROM sports WHERE id = :id")
    suspend fun deleteSingleSport(id: Int)
}