package com.projetogrupo8.taskmanager.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import java.util.Calendar


class AddTaskFragment : Fragment(R.layout.fragment_add_task), MenuProvider {

    private var addTaskBinding: FragmentAddTaskBinding? = null  //variável mutável que pode ser nula
    private val binding get() = addTaskBinding!!    //O operador !! força o Kotlin a tratar a variável como não-nula. Se a variável for nula, o app irá lançar uma exceção

    private lateinit var tasksViewModel: TaskViewModel   //Declaração do VieWModel
    private lateinit var addTaskView: View

    private var selectedDate: String = ""
    private var selectedTime: String = ""

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

        // Configurar listeners para os ImageViews de data e hora
        binding.ivSelectDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.ivSelectTime.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Atualizar a variável e o TextView correspondente
                selectedDate = String.format(getString(R.string.data_format), selectedDay, selectedMonth + 1, selectedYear)
                binding.tvSelectedDate.text = selectedDate
            },
            year, month, day
        ).show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                // Atualizar a variável e o TextView correspondente
                selectedTime = String.format(getString(R.string.time_format), selectedHour, selectedMinute)
                binding.tvSelectedTime.text = selectedTime
            },
            hour, minute, true // Use `false` para formato AM/PM
        ).show()
    }



    //2.Função para adicionar tarefa: fun addTask | Mostar Toast
    private fun addTask(view: View){
        val taskTitle = binding.etAddTaskTitle.text.toString().trim()
        val taskDesc = binding.etAddTaskDescription.text.toString().trim()

        if (taskTitle.isNotEmpty()){
            val task = Task(
                0, taskTitle, taskDesc,
                date = selectedDate,
                time = selectedTime
            )
            tasksViewModel.addTask(task)

            Toast.makeText(addTaskView.context, getString(R.string.saved_task), Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeTaskManagerFragment, false)
        } else{
            Toast.makeText(addTaskView.context, getString(R.string.task_title_and_description), Toast.LENGTH_SHORT).show()
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