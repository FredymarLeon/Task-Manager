package com.projetogrupo8.taskmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.projetogrupo8.taskmanager.R



class EditTaskFragment : Fragment() {
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var saveFab: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
