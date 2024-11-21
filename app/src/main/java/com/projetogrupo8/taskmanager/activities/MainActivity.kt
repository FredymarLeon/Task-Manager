package com.projetogrupo8.taskmanager.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.projetogrupo8.taskmanager.R
import com.projetogrupo8.taskmanager.database.TaskDatabase
import com.projetogrupo8.taskmanager.repository.TaskRepository
import com.projetogrupo8.taskmanager.viewModel.TaskViewModel
import com.projetogrupo8.taskmanager.viewModel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

   internal lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViewModel()

    }

    private fun setupViewModel(){
        val taskRepository = TaskRepository(TaskDatabase(this))
        val viewModelProviderFactory = TaskViewModelFactory(application, taskRepository)
        taskViewModel = ViewModelProvider(this, viewModelProviderFactory)[TaskViewModel::class.java]

    }
}