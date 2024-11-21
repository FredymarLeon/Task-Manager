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
import androidx.navigation.findNavController
import com.projetogrupo8.taskmanager.R
import com.projetogrupo8.taskmanager.activities.MainActivity
import com.projetogrupo8.taskmanager.adapter.TaskAdapter
import com.projetogrupo8.taskmanager.databinding.FragmentHomeTaskManagerBinding
import com.projetogrupo8.taskmanager.viewModel.TaskViewModel

//TODO: ATUALIZAR A UI: image visivel quando a lista estiver vazia
//TODO: CONFIGURAR O RECYCLERVIEW (LISTAR AS TAREFAS)
//TODO: FUNÇÃO da lógica de pesquisa
//TODO: CONFIGURAR O RECYCLERVIEW (LISTAR AS CATEGORIAS)*

class HomeTaskManagerFragment : Fragment(R.layout.fragment_home_task_manager), SearchView.OnQueryTextListener, MenuProvider {

    private var homeTaskManagerBinding: FragmentHomeTaskManagerBinding? = null
    private val binding get() = homeTaskManagerBinding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeTaskManagerBinding = FragmentHomeTaskManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        taskViewModel = (activity as MainActivity).taskViewModel

        binding.addTaskFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeTaskManagerFragment_to_addTaskFragment)
        }
    }

    //Metodo fornecido pelo MenuProvider para configurar menu de pequisa

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.task_manager_menu, menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}