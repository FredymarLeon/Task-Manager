package com.projetogrupo8.taskmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.projetogrupo8.taskmanager.R
import com.projetogrupo8.taskmanager.activities.MainActivity
import com.projetogrupo8.taskmanager.databinding.FragmentEditTaskBinding
import com.projetogrupo8.taskmanager.model.Task
import com.projetogrupo8.taskmanager.viewModel.TaskViewModel

//TODO: Editar tarefa e salvar tarefa editada = button EditTaskFAB| Mostar Toast
//TODO: Detetar tarefa: fun deleteTask = icone do menu | Mostar AlertDialog

class EditTaskFragment : Fragment(R.layout.fragment_edit_task), MenuProvider {

    private var editTaskBinding : FragmentEditTaskBinding? = null
    private  val binding get() = editTaskBinding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var currentTask: Task

    private val args: EditTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editTaskBinding = FragmentEditTaskBinding.inflate(inflater, container, false)
        return  binding.root
    }

    //Falta construir a função para deletar tarefa: fun deleteTask | Mostar Toast
    fun deleteTask(){}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        taskViewModel = (activity as MainActivity).taskViewModel
        currentTask = args.task!!

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_task, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteTask()
                true
            } else -> false
        }
    }


}