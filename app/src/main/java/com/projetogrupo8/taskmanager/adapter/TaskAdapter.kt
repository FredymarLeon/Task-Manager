package com.projetogrupo8.taskmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.projetogrupo8.taskmanager.databinding.ItemTaskBinding
import com.projetogrupo8.taskmanager.fragments.HomeTaskManagerFragmentDirections
import com.projetogrupo8.taskmanager.model.Task

class TaskAdapter: RecyclerView.Adapter<TaskViewHolder>(){

    //classe utilitária que calcula a diferença entre duas listas e gera uma lista de operações de atualização que converte a primeira lista na segunda
    private val diffUtil = object : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.titleTask == newItem.titleTask &&
                    oldItem.descriptionTask == newItem.descriptionTask
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)


    //Metodos do Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = asyncListDiffer.currentList[position]         //val item = taskList[position]
        holder.itemTaskBinding.tvTaskTitle.text = currentTask.titleTask
        holder.itemTaskBinding.tvTaskDescription.text = currentTask.descriptionTask

        holder.itemView.setOnClickListener {
            val direction = HomeTaskManagerFragmentDirections.actionHomeTaskManagerFragmentToEditTaskFragment(currentTask)
            it.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }
}