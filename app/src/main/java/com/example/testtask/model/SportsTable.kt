package com.example.testtask.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sports")
data class SportsTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", index = true)
    val id: Int,
    val nsrs_sport_id: Int,
    val rf_sport_db_name: String,
    val sport_id: Int,
    val sport_name: String,
    val status: String
)