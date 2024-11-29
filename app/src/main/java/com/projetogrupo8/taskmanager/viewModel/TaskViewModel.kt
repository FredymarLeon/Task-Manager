package com.projetogrupo8.taskmanager.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.projetogrupo8.taskmanager.model.Task
import com.projetogrupo8.taskmanager.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel (app: Application, private val taskRepository: TaskRepository) : AndroidViewModel(app){

    fun addTask(task: Task) =
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }

    fun deleteTask(task: Task) =
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }

    fun updateTask(task: Task) =
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }

    fun readAllTasks() = taskRepository.readAllTasks()

    fun searchTask(query: String) = taskRepository.searchTask(query)
}