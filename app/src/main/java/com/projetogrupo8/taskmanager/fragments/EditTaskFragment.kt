package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.projetogrupo8.taskmanager.R
import com.projetogrupo8.taskmanager.viewmodels.EditTaskViewModel

//TODO: Editar tarefa e salvar tarefa editada = button EditTaskFAB| Mostar Toast
//TODO: Detetar tarefa: fun deleteTask = icone do menu | Mostar AlertDialog


class EditTaskFragment : Fragment() {

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var saveFab: FloatingActionButton
    private lateinit var viewModel: EditTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_task, container, false)

        // Find views by ID
        titleEditText = view.findViewById(R.id.etEditTaskTitle)
        descriptionEditText = view.findViewById(R.id.etEditTaskDescription)
        saveFab = view.findViewById(R.id.EditTaskFAB)

        // Get ViewModel instance
        viewModel = requireActivity().viewModelStore.get(EditTaskViewModel::class.java)

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

            // Call ViewModel function to edit task
            viewModel.editTask(title, description)

            // After successful edit, navigate back to task list
            findNavController().popBackStack() // Navigate back to previous fragment
        }

        return view
    }
}