package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.projetogrupo8.taskmanager.R
import com.projetogrupo8.taskmanager.activities.MainActivity
import com.projetogrupo8.taskmanager.databinding.FragmentAddTaskBinding
import com.projetogrupo8.taskmanager.databinding.FragmentEditTaskBinding
import com.projetogrupo8.taskmanager.fragments.EditTaskFragmentArgs
import com.projetogrupo8.taskmanager.model.Task
import com.projetogrupo8.taskmanager.viewModel.EditTaskViewModel
import com.projetogrupo8.taskmanager.viewModel.TaskViewModel

//TODO: Editar tarefa e salvar tarefa editada = button EditTaskFAB| Mostar Toast
//TODO: Detetar tarefa: fun deleteTask = icone do menu | Mostar AlertDialog


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

            } else {
                Toast.makeText(context, "Por Favor, insira o título da tarefa!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun deleteTask() {
        AlertDialog.Builder(activity).apply {
            setTitle("Vôce tem certeza?")
            setMessage("Remover Tarefa?")
            setPositiveButton("SIM") {_,_ ->
                tasksViewModel.deleteTask(currentTask)
                Toast.makeText(context, "Tarefa foi removido", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeTaskManagerFragment, false)
            }
            setNegativeButton("CANCELAR", null)
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