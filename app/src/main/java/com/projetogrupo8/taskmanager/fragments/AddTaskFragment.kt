package com.projetogrupo8.taskmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.projetogrupo8.taskmanager.R
import com.projetogrupo8.taskmanager.activities.MainActivity
import com.projetogrupo8.taskmanager.databinding.FragmentAddTaskBinding
import com.projetogrupo8.taskmanager.model.Task
import com.projetogrupo8.taskmanager.viewModel.TaskViewModel

//TODO: Função para salvar tarefa: fun saveTask | Mostar Toast

class AddTaskFragment : Fragment(R.layout.fragment_add_task), MenuProvider {

    private var addTaskBinding: FragmentAddTaskBinding? = null  //variável mutável que pode ser nula
    private val binding get() = addTaskBinding!!    //O operador !! força o Kotlin a tratar a variável como não-nula. Se a variável for nula, o app irá lançar uma exceção

    private lateinit var tasksViewModel: TaskViewModel   //Declaração do VieWModel
    private lateinit var addTaskView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addTaskBinding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    //1.Menu
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()  //Configurar menuHost
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        tasksViewModel = (activity as MainActivity).taskViewModel       //Modelo de visualização
        addTaskView = view
    }

    //2.Função para adicionar tarefa: fun addTask | Mostar Toast
    private fun addTask(view: View){
        val taskTitle = binding.etAddTaskTitle.text.toString().trim()
        val taskDesc = binding.etAddTaskDescription.text.toString().trim()

        if (taskTitle.isNotEmpty()){
            val task = Task(0, taskTitle, taskDesc)
            tasksViewModel.addTask(task)

            Toast.makeText(addTaskView.context, "Tarefa salva", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeTaskManagerFragment, false)
        } else{
            Toast.makeText(addTaskView.context, "Por favor insira o título e a descrição da tarefa", Toast.LENGTH_SHORT).show()
        }
    }

    //3.Funções do MenuProvider
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

    override fun onDestroy() {
        super.onDestroy()
        addTaskBinding = null
    }
}