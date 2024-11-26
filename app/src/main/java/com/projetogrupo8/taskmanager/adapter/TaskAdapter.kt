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

class TaskAdapter: RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){
    class TaskViewHolder(val itemBinding: ItemTaskBinding): RecyclerView.ViewHolder(itemBinding.root)

    //classe utilitária que calcula a diferença entre duas listas e gera uma lista de operações de atualização que converte a primeira lista na segunda
    private val diffUtil = object : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.tvTaskTitle == newItem.tvTaskTitle &&
                    oldItem.tvTaskDescription == newItem.tvTaskDescription
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    val asyncListDiffer = AsyncListDiffer(this, diffUtil)


    //Metodos do Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = asyncListDiffer.currentList[position]         //val item = taskList[position]
        holder.itemBinding.tvTaskTitle.text = currentTask.tvTaskTitle
        holder.itemBinding.tvTaskDescription.text = currentTask.tvTaskDescription

        holder.itemView.setOnClickListener {
            val direction = HomeTaskManagerFragmentDirections.actionHomeTaskManagerFragmentToEditTaskFragment(currentTask)
            it.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }
}