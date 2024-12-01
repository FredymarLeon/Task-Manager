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
import androidx.recyclerview.widget.LinearLayoutManager
import com.projetogrupo8.taskmanager.R
import com.projetogrupo8.taskmanager.activities.MainActivity
import com.projetogrupo8.taskmanager.adapter.TaskAdapter
import com.projetogrupo8.taskmanager.databinding.FragmentHomeTaskManagerBinding
import com.projetogrupo8.taskmanager.model.Task
import com.projetogrupo8.taskmanager.viewModel.TaskViewModel

class HomeTaskManagerFragment : Fragment(R.layout.fragment_home_task_manager), SearchView.OnQueryTextListener, MenuProvider {

    private var homeTaskManagerBinding: FragmentHomeTaskManagerBinding? = null
    private val binding get() = homeTaskManagerBinding!!

    private lateinit var tasksViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        homeTaskManagerBinding = FragmentHomeTaskManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        tasksViewModel = (activity as MainActivity).taskViewModel
        setupRecyclerView()

        binding.addTaskFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeTaskManagerFragment_to_addTaskFragment)
        }
    }

    private fun updateUI(task: List<Task>?){
        if (task != null){
            if (task.isNotEmpty()){
                binding.emptyTasksImage.visibility = View.GONE
                binding.rvListTasks.visibility = View.VISIBLE
            } else {
                binding.emptyTasksImage.visibility = View.VISIBLE
                binding.rvListTasks.visibility = View.GONE
            }
        }
    }

    private fun setupRecyclerView(){
        taskAdapter = TaskAdapter()
        binding.rvListTasks.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = taskAdapter
        }

        activity?.let {
            tasksViewModel.readAllTasks().observe(viewLifecycleOwner){task ->
                taskAdapter.asyncListDiffer.submitList(task)
                updateUI(task)
            }
        }
    }

    private fun searchTask(query: String){
        val searchQuery = "%$query%"

        tasksViewModel.searchTask(searchQuery).observe(this) {
                list -> taskAdapter.asyncListDiffer.submitList(list)

            if (list.isEmpty()) {
                binding.tvNoResults.text = String.format("\"%s\" %s", query, getString(R.string.no_results))
                binding.tvNoResults.visibility = View.VISIBLE
            } else {
                binding.tvNoResults.visibility = View.GONE
            }
        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchTask(newText)
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        homeTaskManagerBinding = null
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