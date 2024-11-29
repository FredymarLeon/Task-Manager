package com.projetogrupo8.taskmanager.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.projetogrupo8.taskmanager.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks_table ORDER BY id ASC")
    fun readAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks_table WHERE tvTaskTitle LIKE :query Or tvTaskDescription LIKE  :query")
    fun searchTask(query: String): LiveData<List<Task>>
}