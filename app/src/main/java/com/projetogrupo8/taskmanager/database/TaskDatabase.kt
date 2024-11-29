package com.projetogrupo8.taskmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projetogrupo8.taskmanager.model.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false)

abstract class TaskDatabase: RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object {
        private var instance: TaskDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): TaskDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task_db"
            ).build()
    }
}