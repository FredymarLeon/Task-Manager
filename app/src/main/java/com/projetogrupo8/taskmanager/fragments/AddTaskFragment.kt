package com.projetogrupo8.taskmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.projetogrupo8.taskmanager.R
import com.projetogrupo8.taskmanager.activities.MainActivity
import com.projetogrupo8.taskmanager.databinding.FragmentAddTaskBinding
import com.projetogrupo8.taskmanager.viewModel.TaskViewModel

//TODO: Função para salvar tarefa: fun saveTask | Mostar Toast

class AddTaskFragment : Fragment(R.layout.fragment_add_task), MenuProvider {

    private var addTaskBinding: FragmentAddTaskBinding? = null  //variável mutável que pode ser nula
    private val binding get() = addTaskBinding!!    //O operador !! força o Kotlin a tratar a variável como não-nula. Se a variável for nula, o app irá lançar uma exceção

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var addTaskView: View


    //Falta construir a função para adicionar tarefa: fun addTask | Mostar Toast
    fun addTask(view: View){}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addTaskBinding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        taskViewModel = (activity as MainActivity).taskViewModel
        addTaskView = view
    }

    //Funções do MenuProvider
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_task,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu -> {
                addTask(addTaskView)
                true
            }
            else -> false
        }
    }

}