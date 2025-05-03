package com.example.testtask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testtask.dao.SportsDao
import com.example.testtask.model.SportsTable


@Database(
    entities = [SportsTable::class],
    version = 1
)
abstract class SportsDatabase:RoomDatabase() {
    abstract fun getSportsDao(): SportsDao

    companion object{
        const val DATABASE_NAME = "sports_db"
        @Volatile
        private var instance:SportsDatabase?=null
        private val LOCK = Any()

        operator fun invoke(context:Context):SportsDatabase{
           return instance?: synchronized(LOCK){
                instance?: createDB(context).also {
                    instance = it
                }
            }
        }

        private fun createDB(context:Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SportsDatabase::class.java,
                DATABASE_NAME
            ).build()
    }

}