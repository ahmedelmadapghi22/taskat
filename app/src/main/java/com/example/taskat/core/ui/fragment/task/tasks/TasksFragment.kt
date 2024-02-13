package com.example.taskat.core.ui.fragment.task.tasks

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskat.R
import com.example.taskat.core.ui.Constants
import com.example.taskat.core.ui.ConstantsEditDialogs.INCOME_CURRENCY
import com.example.taskat.core.ui.ConstantsEditDialogs.INCOME_PRICE
import com.example.taskat.core.ui.ConstantsEditDialogs.OUTCOME_CURRENCY
import com.example.taskat.core.ui.ConstantsEditDialogs.OUTCOME_PRICE
import com.example.taskat.core.ui.adapter.adapters.TaskAdapter
import com.example.taskat.core.ui.adapter.listener.SetOnClickCurrencyListener
import com.example.taskat.core.ui.adapter.listener.task.TaskOperations
import com.example.taskat.core.ui.dialog.ChooseFilterMethodForTaskDialog
import com.example.taskat.core.ui.dialog.CurrencyDialog
import com.example.taskat.core.ui.dialog.EditTaskPrice
import com.example.taskat.core.ui.dialog.TaskPriceEditDialog
import com.example.taskat.core.ui.dialog.TaskStateDialog
import com.example.taskat.core.ui.dialog.listener.SetOnClickMethodTaskSearch
import com.example.taskat.core.ui.fragment.BindingFragment
import com.example.taskat.core.ui.helper.CheckerVisibility
import com.example.taskat.core.ui.helper.InitRecyclerView
import com.example.taskat.core.ui.helper.StringResourceHelper
import com.example.taskat.core.ui.uistate.TasksUIState
import com.example.taskat.databinding.FragmentTasksBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val COMPLETE_TASK_STATE_ID: Int = 1

@AndroidEntryPoint
class TasksFragment : BindingFragment<FragmentTasksBinding>(FragmentTasksBinding::inflate),
    TaskOperations, EditTaskPrice, SetOnClickCurrencyListener, SetOnClickMethodTaskSearch {


    @Inject
    lateinit var stringResourceHelper: StringResourceHelper
    private val viewModel: TasksViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var taskStateDialog: TaskStateDialog
    private lateinit var taskPriceEditDialog: TaskPriceEditDialog
    private lateinit var currencyDialog: CurrencyDialog
    private lateinit var chooseFilterMethodForTaskDialog: ChooseFilterMethodForTaskDialog
    private var taskID: Int = -1
    private var searchTaskMethod: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskAdapter = TaskAdapter(stringResourceHelper, this)

        taskStateDialog = TaskStateDialog(requireContext())
        taskPriceEditDialog = TaskPriceEditDialog(requireContext(), this)
        currencyDialog = CurrencyDialog(requireContext(), stringResourceHelper, this)
        chooseFilterMethodForTaskDialog = ChooseFilterMethodForTaskDialog(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        useBinding {
            it.apply {
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.tasksUIState.collect { tasksUIState ->
                            when (tasksUIState) {
                                is TasksUIState.Loading -> {
                                    if (tasksUIState.isLoading) {
                                        CheckerVisibility.setVisibility(
                                            loadingLayout.root, View.VISIBLE
                                        )
                                    } else {
                                        CheckerVisibility.setVisibility(
                                            loadingLayout.root, View.GONE
                                        )

                                    }
                                }

                                is TasksUIState.Success -> {
                                    CheckerVisibility.initNonEmptyList(emptyLayout.root, rvTasks)
                                    CheckerVisibility.setVisibility(searchLayout.root, View.VISIBLE)

                                    Log.d("TasksUIState", "Success:${tasksUIState.tasks}")
                                    taskAdapter.getListDiffer().submitList(tasksUIState.tasks)
                                    InitRecyclerView.init(
                                        requireBinding().rvTasks, taskAdapter, linearLayoutManager
                                    )

                                }

                                is TasksUIState.Empty -> {
                                    emptyLayout.tvEmptyData.text = getString(R.string.err_no_tasks)
                                    CheckerVisibility.initEmptyList(emptyLayout.root, rvTasks)
                                    CheckerVisibility.setVisibility(searchLayout.root, View.GONE)


                                }

                                is TasksUIState.Error -> {
                                    makeToastFromStr(tasksUIState.err)
                                    Log.d("TasksUIState", "onViewCreated:${tasksUIState.err}")
                                }

                                is TasksUIState.Edit -> {
                                    if (taskPriceEditDialog.isShowing) {
                                        viewModel.setStatePriceEditDialog(false, -1, -1, 0f)
                                    }
                                    if (currencyDialog.isShowing) {
                                        viewModel.setStateCurrencyEditDialog(false)
                                    }
                                    makeToastFromStr(tasksUIState.isEdit.toString())
                                }


                            }

                        }
                    }
                }
                lifecycleScope.launch {
                    viewModel.taskStateDialogUIState.collect { taskStateDialogState ->
                        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                            taskStateDialogState.apply {
                                if (dialogState) {
                                    taskStateDialog.createDialog({
                                        viewModel.editTaskState(
                                            taskID,
                                            COMPLETE_TASK_STATE_ID
                                        )
                                    },
                                        { viewModel.removeEditTaskStateDialog() })
                                }
                            }

                        }

                    }
                }
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.editPriceDialogUIState.collect { state ->
                            if (state.dialogState) {
                                taskPriceEditDialog.createDialog(
                                    state.wherePrice, state.data, state.taskID
                                )

                            } else {
                                taskPriceEditDialog.dismiss()
                            }


                        }
                    }
                }
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.editCurrencyDialogUIState.collect { state ->
                            if (state) {
                                currencyDialog.setCurrenciesList(viewModel.getCurrencies())
                                currencyDialog.createDialog()
                                currencyDialog.showDialog()

                            } else {
                                currencyDialog.dismiss()
                            }


                        }
                    }
                }
                searchLayout.btnChooseFilterMethod.setOnClickListener {
                    chooseFilterMethodForTaskDialog.showDialog()
                }
                searchLayout.searchView.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.searchTasks(searchTaskMethod, newText.toString())
                        return true
                    }

                })
                btnAddTask.setOnClickListener {
                    findNavController().navigate(R.id.action_tasksFragment_to_addNewTaskFragment)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getTasks()
    }

    override fun onStop() {
        super.onStop()
        viewModel.showEditTaskStateDialog(false, -1, -1)
        taskID = -1
    }


    override fun onClickTaskState(taskID: Int, taskState: Int) {
        viewModel.showEditTaskStateDialog(true, taskID, taskState)
    }

    override fun onClickTaskSpecialist(taskID: Int) {
        val action = TasksFragmentDirections.actionTasksFragmentToSpecialistsFragment()
        action.whereFrom = Constants.TASKS_FRAGMENT_ID
        action.taskID = taskID
        findNavController().navigate(action)
    }

    override fun onClickIncomePrice(taskID: Int, oldIncomePrice: Float) {
        viewModel.setStatePriceEditDialog(
            true,
            taskID,
            INCOME_PRICE,
            oldIncomePrice
        )
    }

    override fun onClickOutcomePrice(taskID: Int, oldOutcomePrice: Float) {
        viewModel.setStatePriceEditDialog(
            true,
            taskID,
            OUTCOME_PRICE,
            oldOutcomePrice
        )
    }

    override fun onClickOutcomeCurrency(taskID: Int) {
        this.taskID = taskID
        currencyDialog.setWhereCurrency(OUTCOME_CURRENCY)
        viewModel.setStateCurrencyEditDialog(true)
    }

    override fun onClickIncomeCurrency(taskID: Int) {
        this.taskID = taskID
        currencyDialog.setWhereCurrency(INCOME_CURRENCY)
        viewModel.setStateCurrencyEditDialog(true)
    }

    override fun onClickIsDistributorAccounted(taskID: Int) {
        viewModel.editTaskDistributorAccounted(taskID)
    }

    override fun onClickIsSpecialistAccounted(taskID: Int) {
        viewModel.editTaskSpecialistAccounted(taskID)

    }


    override fun onEditTaskIncomePrice(taskID: Int, newIncomePrice: String) {
        viewModel.editTaskIncomePrice(taskID, newIncomePrice)
    }

    override fun onEditTaskOutcomePrice(taskID: Int, newOutcomePrice: String) {
        viewModel.editTaskOutcomePrice(taskID, newOutcomePrice)

    }

    override fun onClickDialogIncomeCurrency(nameResID: Int) {
        viewModel.editTaskIncomeCurrency(taskID, nameResID)

    }

    override fun onClickDialogOutcomeCurrency(nameResID: Int) {
        viewModel.editTaskOutcomeCurrency(taskID, nameResID)


    }

    override fun onClick(searchMethod: String) {
        searchTaskMethod = searchMethod
        viewModel.searchTasks(searchTaskMethod, "")
    }


}