package com.example.taskat.core.ui.fragment.task.newtask

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.taskat.R
import com.example.taskat.core.ui.Constants
import com.example.taskat.core.ui.ConstantsEditDialogs
import com.example.taskat.core.ui.adapter.listener.SetOnClickCurrencyListener
import com.example.taskat.core.ui.common.SharedTaskViewModel
import com.example.taskat.core.ui.dialog.CurrencyDialog
import com.example.taskat.core.ui.fragment.BindingFragment
import com.example.taskat.core.ui.helper.CheckerVisibility
import com.example.taskat.core.ui.helper.EditTextHelper
import com.example.taskat.core.ui.helper.StringResourceHelper
import com.example.taskat.core.ui.uistate.TaskUIState
import com.example.taskat.core.ui.uistate.newtask.TempCurrentTask
import com.example.taskat.databinding.FragmentAddNewTaskBinding
import com.example.taskat.domain.model.Date
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AddNewTaskFragment :
    BindingFragment<FragmentAddNewTaskBinding>(FragmentAddNewTaskBinding::inflate),
    SetOnClickCurrencyListener {

    private val viewModel: AddNewTaskViewModel by viewModels()
    private val sharedViewModel: SharedTaskViewModel by activityViewModels()
    private lateinit var currencyDialog: CurrencyDialog
    private var currencyIncome = -1
    private var currencyoutcome = -1
    private var stateTask = -1

    @Inject
    lateinit var stringResourceHelper: StringResourceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currencyDialog = CurrencyDialog(requireContext(), stringResourceHelper, this)
        currencyDialog.setCurrenciesList(viewModel.getAllCurrencies())
        currencyDialog.createDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMenu()
        useBinding { binding ->
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    sharedViewModel.addTaskUIState.collect { task ->
                        task?.let { _task ->
                            Log.d("taskUIState", "_task:${_task}")

                            requireBinding().apply {
                                edTitleTask.setText(_task.taskTitle)
                                edSpecialist.setText(_task.specialistName)
                                edSpecialist.tag = _task.specialistID
                                edDistributor.tag = _task.distributorID
                                edDistributor.setText(_task.distributorName)
                                edDay.setText((_task.taskDate.day).toString())
                                edMonth.setText((_task.taskDate.month).toString())
                                edYear.setText((_task.taskDate.year).toString())
                                if (_task.taskState != -1) {
                                    taskState.setText(resources.getStringArray(R.array.taskState)[_task.taskState])
                                }
                                detailsPriceTask.apply {
                                    edDistributorPrice.setText(_task.incomePrice)
                                    edMyPercent.setText(_task.percent)
                                    edSpecialistPrice.setText(_task.outcomePrice)
                                }
                            }
                        }
                    }
                }
            }
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.taskUIState.collect { taskUIState ->
                        when (taskUIState) {
                            is TaskUIState.Loading -> {
                                Log.d("taskUIState", "onViewCreated:${taskUIState.isLoading}")

                                if (taskUIState.isLoading) {
                                    CheckerVisibility.setVisibility(
                                        requireBinding().loadingLayout.root,
                                        View.VISIBLE
                                    )
                                } else {
                                    CheckerVisibility.setVisibility(
                                        requireBinding().loadingLayout.root,
                                        View.GONE
                                    )

                                }
                            }


                            is TaskUIState.Success -> {
                                if (taskUIState.isSuccess) {
                                    makeToastFromStr("Saved")
                                    sharedViewModel.removeTemp()
                                    removeDataFromViews()
                                    findNavController().navigate(R.id.action_addNewTaskFragment_to_tasksFragment)

                                }

                            }

                            is TaskUIState.PriceSpecialist -> {
                                Log.d("PriceTask", "PriceSpecialist")
                                requireBinding().detailsPriceTask.edSpecialistPrice.setText(
                                    taskUIState.price
                                )

                            }

                            is TaskUIState.Error -> {
                                makeToastFromStr(taskUIState.err)
                                Log.d("taskUIState", "onViewCreated:${taskUIState.err}")

                            }


                        }
                    }
                }

            }

            binding.btnSaveTask.setOnClickListener {

                sharedViewModel.setCurrentTask(
                    getData()
                )
                Log.d("taskState", "onViewCreated:${sharedViewModel.getTempTaskInstance()}")

                viewModel.addNewTask(sharedViewModel.getTempTaskInstance())
            }
            binding.btnAddDistributor.setOnClickListener {
                val action = AddNewTaskFragmentDirections.actionAddNewTaskFragmentToDistributorsFragment()
                action.whereFrom = Constants.ADD_TASK_FRAGMENT_ID
                findNavController().navigate(action)
            }
            binding.btnAddSpecialist.setOnClickListener {
                val action =
                    AddNewTaskFragmentDirections.actionAddNewTaskFragmentToSpecialistsFragment()
                action.whereFrom = Constants.ADD_TASK_FRAGMENT_ID
                findNavController().navigate(action)
            }
            binding.detailsPriceTask.edMyPercent.doAfterTextChanged { text->
                Log.d("PriceTask", "edMyPercent.doOnTextChanged ${text} ")

                viewModel.calculatePriceForSpecialist(
                    requireBinding().detailsPriceTask.edDistributorPrice.text.toString(),
                    text.toString()
                )


            }
            binding.detailsPriceTask.edDistributorPrice.doAfterTextChanged { text ->
                viewModel.calculatePriceForSpecialist(
                        text.toString(),
                        requireBinding().detailsPriceTask.edMyPercent.text.toString()
                    )


            }
            binding.detailsPriceTask.btnChooseIncomeCurrency.setOnClickListener {
                currencyDialog.setWhereCurrency(ConstantsEditDialogs.INCOME_CURRENCY)
                currencyDialog.showDialog()


            }
            binding.detailsPriceTask.btnChooseOutcomeCurrency.setOnClickListener {
                currencyDialog.setWhereCurrency(ConstantsEditDialogs.OUTCOME_CURRENCY)
                currencyDialog.showDialog()


            }
        }
    }

    override fun onStop() {
        super.onStop()


        sharedViewModel.setCurrentTask(
            getData()
        )
        removeDataFromViews()

    }


    private fun getData(): TempCurrentTask {
        var temp: TempCurrentTask
        requireBinding().apply {
            temp = TempCurrentTask(
                taskTitle = EditTextHelper.getTextFromEditText(edTitleTask),
                taskDate = Date(
                    EditTextHelper.getIntFromEditText(edDay),
                    EditTextHelper.getIntFromEditText(edMonth),
                    EditTextHelper.getIntFromEditText(edYear)
                ),
                taskState = stateTask,
                incomePrice = EditTextHelper.getTextFromEditText(detailsPriceTask.edDistributorPrice),
                percent = EditTextHelper.getTextFromEditText(detailsPriceTask.edMyPercent),
                outcomePrice = EditTextHelper.getTextFromEditText(detailsPriceTask.edSpecialistPrice),
                incomeCurrency = currencyIncome,
                outcomeCurrency = currencyoutcome,
                distributorName = EditTextHelper.getTextFromEditText(edDistributor),
                specialistName = EditTextHelper.getTextFromEditText(edSpecialist),
                specialistID = edSpecialist.tag as Int?,
                distributorID = edDistributor.tag as Int?

            )
        }
        return temp
    }

    private fun removeDataFromViews() {
        requireBinding().apply {
            EditTextHelper.setEmptyToEditText(edTitleTask)
            EditTextHelper.setEmptyToEditText(edDistributor)
            EditTextHelper.setEmptyToEditText(edSpecialist)
            EditTextHelper.setEmptyToEditText(taskState)
            EditTextHelper.setEmptyToEditText(detailsPriceTask.edSpecialistPrice)
            EditTextHelper.setEmptyToEditText(detailsPriceTask.edDistributorPrice)
            EditTextHelper.setEmptyToEditText(detailsPriceTask.edMyPercent)
            EditTextHelper.removeTagFromEditText(edSpecialist)
            EditTextHelper.removeTagFromEditText(edDistributor)
        }
    }


    private fun setMenu() {
        val menuItems = requireContext().resources.getStringArray(R.array.taskState)
        val menuItemsIds = requireContext().resources.getIntArray(R.array.taskStateIds)

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, menuItems)
        (requireBinding().taskState as? AutoCompleteTextView)?.setAdapter(adapter)
        (requireBinding().taskState as? AutoCompleteTextView)?.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                stateTask = menuItemsIds[position]
                Log.d("taskState", "stateTask :  ${stateTask}")

            }
    }


    override fun onClickDialogIncomeCurrency(nameResID: Int) {

        currencyIncome = nameResID
        Log.d("taskState", "onClickDialogIncomeCurrency${currencyIncome}")


    }

    override fun onClickDialogOutcomeCurrency(nameResID: Int) {
        currencyoutcome = nameResID
        Log.d("taskState", "onClickDialogOutcomeCurrency ${currencyoutcome}")


    }


}