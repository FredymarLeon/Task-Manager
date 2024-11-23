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
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton

=======
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
>>>>>>> e5248fcbf4a9b20be0834b393fc6ddcbf9278be8
import com.projetogrupo8.taskmanager.R
import com.projetogrupo8.taskmanager.activities.MainActivity
import com.projetogrupo8.taskmanager.databinding.FragmentEditTaskBinding
import com.projetogrupo8.taskmanager.model.Task
import com.projetogrupo8.taskmanager.viewModel.TaskViewModel

//TODO: Editar tarefa e salvar tarefa editada = button EditTaskFAB| Mostar Toast
//TODO: Detetar tarefa: fun deleteTask = icone do menu | Mostar AlertDialog

<<<<<<< HEAD

class EditTaskFragment : Fragment() {
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var saveFab: FloatingActionButton

=======
class EditTaskFragment : Fragment(R.layout.fragment_edit_task), MenuProvider {

    private var editTaskBinding : FragmentEditTaskBinding? = null
    private  val binding get() = editTaskBinding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var currentTask: Task

    private val args: EditTaskFragmentArgs by navArgs()
>>>>>>> e5248fcbf4a9b20be0834b393fc6ddcbf9278be8

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
<<<<<<< HEAD
        val view = inflater.inflate(R.layout.fragment_edit_task, container, false)

        // Find views by ID
        titleEditText = view.findViewById(R.id.etEditTaskTitle)
        descriptionEditText = view.findViewById(R.id.etEditTaskDescription)
        saveFab = view.findViewById(R.Id.EditTaskFAB)


        // Set up click listener for save button

        saveFab.setOnClickListener {
            // Get user input from EditTexts
            val title = titleEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()

            // Validate user input (optional)
            if (title.isEmpty()) {
                // Show error message for empty title
                return@setOnClickListener
            }

            // Edit the task (logic depends on your data storage)
            // Here's an example using a placeholder function
            editTask(title, description)

            // After successful edit, consider either:
            // 1. Navigate back to the task list fragment
            // 2. Display a confirmation message
        }

        // (Optional) Pre-populate EditTexts if editing an existing task
        val arguments = arguments
        if (arguments != null) {
            val existingTitle = arguments.getString("taskTitle")
            val existingDescription = arguments.getString("taskDescription")
            if (existingTitle != null && existingDescription != null) {
                titleEditText.setText(existingTitle)
                descriptionEditText.setText(existingDescription)
            }
        }

        return view
    }

    // Function to edit the task (replace with your actual implementation)
    private fun editTask(title: String, description: String) {
        // This is a placeholder function, replace it with your logic to
        // update the task in your data storage (e.g., database, shared preferences)
    }
}
=======
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
>>>>>>> e5248fcbf4a9b20be0834b393fc6ddcbf9278be8
