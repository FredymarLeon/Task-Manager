package com.projetogrupo8.taskmanager.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import com.projetogrupo8.taskmanager.databinding.CustomAlertDialogBinding
import com.projetogrupo8.taskmanager.databinding.FragmentEditTaskBinding
import com.projetogrupo8.taskmanager.model.Task
import com.projetogrupo8.taskmanager.viewModel.TaskViewModel
import java.util.Calendar

class EditTaskFragment : Fragment(R.layout.fragment_edit_task), MenuProvider {

    private var editTaskBinding: FragmentEditTaskBinding? = null
    private val binding get() = editTaskBinding!!
    private lateinit var tasksViewModel: TaskViewModel
    private lateinit var currentTask: Task
    private val args: EditTaskFragmentArgs by navArgs()

    private var selectedDate: String = ""
    private var selectedTime: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        editTaskBinding = FragmentEditTaskBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        tasksViewModel = (activity as MainActivity).taskViewModel
        currentTask = args.task!!

        binding.etEditTaskTitle.setText(currentTask.tvTaskTitle)
        binding.etEditTaskDescription.setText(currentTask.tvTaskDescription)

        selectedDate = currentTask.date
        selectedTime = currentTask.time

        binding.tvSelectedDate.text = selectedDate.ifEmpty { getString(R.string.date_not_select) }
        binding.tvSelectedTime.text = selectedTime.ifEmpty { getString(R.string.time_not_select) }

        binding.ivSelectDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.ivSelectTime.setOnClickListener {
            showTimePickerDialog()
        }

        binding.editTaskFab.setOnClickListener {
            val tvTaskTitle = binding.etEditTaskTitle.text.toString().trim()
            val tvTaskDescription = binding.etEditTaskDescription.text.toString().trim()

            if (tvTaskTitle.isNotEmpty()) {
                val task = Task(
                    currentTask.id, tvTaskTitle, tvTaskDescription,
                    date = selectedDate,
                    time = selectedTime
                )
                tasksViewModel.updateTask(task)
                view.findNavController().popBackStack(R.id.homeTaskManagerFragment, false)
                Toast.makeText(context, getString(R.string.confirm_edit), Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(context, getString(R.string.insert_title), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->

                val dateFormat = getString(R.string.data_format)
                selectedDate = String.format(dateFormat, selectedYear, selectedMonth + 1, selectedDay)
                binding.tvSelectedDate.text = selectedDate
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                selectedTime =
                    String.format(getString(R.string.time_format), selectedHour, selectedMinute)
                binding.tvSelectedDate.text = selectedTime
            },
            hour, minute, true
        )
        timePickerDialog.show()
    }

    private fun deleteTask() {

        val dialogBinding = CustomAlertDialogBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnConfirm.setOnClickListener {
            tasksViewModel.deleteTask(currentTask)
            Toast.makeText(
                context,
                getString(R.string.confirm_task_removed),
                Toast.LENGTH_SHORT
            ).show()
            view?.findNavController()?.popBackStack(R.id.homeTaskManagerFragment, false)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_task, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
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