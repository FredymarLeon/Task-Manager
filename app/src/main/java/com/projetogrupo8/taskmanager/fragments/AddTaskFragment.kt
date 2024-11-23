package com.projetogrupo8.taskmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projetogrupo8.taskmanager.R
import com.masscode.simpletodolist.databinding.FragmentEditBinding
import com.masscode.simpletodolist.utils.hideKeyboard
import com.masscode.simpletodolist.utils.shortToast
import com.masscode.simpletodolist.viewmodel.TodoViewModel
import com.masscode.simpletodolist.viewmodel.TodoViewModelFactory
=======
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.projetogrupo8.taskmanager.R
import com.projetogrupo8.taskmanager.activities.MainActivity
import com.projetogrupo8.taskmanager.databinding.FragmentAddTaskBinding
import com.projetogrupo8.taskmanager.viewModel.TaskViewModel
>>>>>>> e5248fcbf4a9b20be0834b393fc6ddcbf9278be8

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