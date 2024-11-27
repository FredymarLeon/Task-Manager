package com.projetogrupo8.taskmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.projetogrupo8.taskmanager.R
import com.projetogrupo8.taskmanager.activities.MainActivity
import com.projetogrupo8.taskmanager.databinding.FragmentEditTaskBinding
import com.projetogrupo8.taskmanager.model.Task
import com.projetogrupo8.taskmanager.viewModel.TaskViewModel

class EditTaskFragment : Fragment(R.layout.fragment_edit_task), MenuProvider {

    private var editTaskBinding: FragmentEditTaskBinding? = null
    private val binding get() = editTaskBinding!!
    private lateinit var tasksViewModel: TaskViewModel
    private lateinit var currentTask: Task
    private val args: EditTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editTaskBinding = FragmentEditTaskBinding.inflate(inflater, container, false)
        return binding.root

    }

    //1.Menu
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()  //Configurar menuHost
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        tasksViewModel = (activity as MainActivity).taskViewModel       //Modelo de visualização
        currentTask = args.task!!

        binding.etEditTaskTitle.setText(currentTask.tvTaskTitle)
        binding.etEditTaskDescription.setText(currentTask.tvTaskDescription)

        binding.editTaskFab.setOnClickListener {
            val tvTaskTitle = binding.etEditTaskTitle.text.toString().trim()
            val tvTaskDescription = binding.etEditTaskDescription.text.toString().trim()

            if (tvTaskTitle.isNotEmpty()) {
                val task = Task(currentTask.id, tvTaskTitle, tvTaskDescription)
                tasksViewModel.updateTask(task)
                view.findNavController().popBackStack(R.id.homeTaskManagerFragment, false)
                Toast.makeText(context, getString(R.string.confirm_edit), Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(context, getString(R.string.insert_title), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun deleteTask() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle(getString(R.string.set_Title))
            setMessage(getString(R.string.set_Message))
            setPositiveButton(getString(R.string.set_Positive_Button)) { _, _ ->
                tasksViewModel.deleteTask(currentTask)
                Toast.makeText(context, getString(R.string.confirm_task_removed), Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeTaskManagerFragment, false)
            }
            setNegativeButton(getString(R.string.set_Negative_Button), null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_task,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteTask()
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editTaskBinding = null
    }
}