package com.projetogrupo8.taskmanager.repository

import com.projetogrupo8.taskmanager.database.TaskDatabase
import com.projetogrupo8.taskmanager.model.Task

class TaskRepository (private val db: TaskDatabase) {

    suspend fun  insertTask(task: Task) = db.getTaskDao().insertTask(task)
    suspend fun  deleteTask(task: Task) = db.getTaskDao().deleteTask(task)
    suspend fun  updateTask(task: Task) = db.getTaskDao().updateTask(task)

    fun readAllTasks()= db.getTaskDao().readAllTasks()
    fun searchTask(query: String?) = db.getTaskDao().searchTask(query)
}