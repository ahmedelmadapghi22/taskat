package com.example.taskat.core.ui.fragment.specialist.specialists

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskat.R
import com.example.taskat.core.ui.Constants
import com.example.taskat.core.ui.adapter.adapters.SpecialistAdapter
import com.example.taskat.core.ui.adapter.listener.SetOnClickEditListener
import com.example.taskat.core.ui.adapter.listener.SetOnClickPersonListener
import com.example.taskat.core.ui.adapter.listener.SetOnClickSpecializationsListener
import com.example.taskat.core.ui.adapter.listener.SetOnClickWhats
import com.example.taskat.core.ui.adapter.listener.SetOnDeleteListener
import com.example.taskat.core.ui.common.SharedTaskViewModel
import com.example.taskat.core.ui.dialog.ChooseFilterMethodDialog
import com.example.taskat.core.ui.dialog.SearchMethod
import com.example.taskat.core.ui.fragment.BindingFragment
import com.example.taskat.core.ui.helper.CheckerVisibility
import com.example.taskat.core.ui.helper.DeleteDialog
import com.example.taskat.core.ui.helper.InitRecyclerView
import com.example.taskat.core.ui.uistate.SpecialistsUIState
import com.example.taskat.databinding.FragmentSpecialistsBinding
import com.example.taskat.domain.model.Specialist
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SpecialistsFragment :
    BindingFragment<FragmentSpecialistsBinding>(FragmentSpecialistsBinding::inflate),
    SetOnClickWhats, SetOnDeleteListener, SetOnClickPersonListener,
    SetOnClickSpecializationsListener,
    SetOnClickEditListener<Specialist> {

    private val viewModel: SpecialistsViewModel by viewModels()

    @Inject
    lateinit var deleteDialog: DeleteDialog
    private val sharedViewModel: SharedTaskViewModel by activityViewModels()


    private lateinit var chooseFilterMethodDialog: ChooseFilterMethodDialog
    private lateinit var specialistAdapter: SpecialistAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var whereFrom = -1
    private var taskID = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseFilterMethodDialog = ChooseFilterMethodDialog(requireContext())
        specialistAdapter = SpecialistAdapter(this, this, this, this, this)
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        useBinding {
            it.apply {
                searchLayout.btnChooseFilterMethod.visibility = View.VISIBLE
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.specialistsUIState.collect { state ->
                            when (state) {
                                is SpecialistsUIState.Loading -> {
                                    if (state.isLoading) {
                                        CheckerVisibility.setVisibility(
                                            loadingLayout.root,
                                            View.VISIBLE
                                        )
                                    } else {
                                        CheckerVisibility.setVisibility(
                                            loadingLayout.root,
                                            View.GONE
                                        )
                                    }
                                }

                                is SpecialistsUIState.Success -> {
                                    CheckerVisibility.initNonEmptyList(
                                        emptyLayout.root,
                                        rvSpecialists
                                    )
                                    specialistAdapter.getListDiffer().submitList(state.data)
                                    Log.d("specialistAdapter", "onViewCreated:${state.data}")
                                    InitRecyclerView.init(
                                        rvSpecialists,
                                        specialistAdapter,
                                        linearLayoutManager
                                    )
                                }

                                is SpecialistsUIState.Search -> {
                                    CheckerVisibility.initNonEmptyList(
                                        emptyLayout.root,
                                        rvSpecialists
                                    )
                                    Log.d("specialistAdapter", "onViewCreatedRes:${state.result}")

                                    specialistAdapter.getListDiffer().submitList(state.result)
                                    InitRecyclerView.init(
                                        rvSpecialists,
                                        specialistAdapter,
                                        linearLayoutManager
                                    )
                                }

                                is SpecialistsUIState.Empty -> {
                                    CheckerVisibility.setVisibility(searchLayout.root, View.GONE)
                                    emptyLayout.tvEmptyData.text = "There are no specialists"
                                    CheckerVisibility.initEmptyList(emptyLayout.root, rvSpecialists)
                                }

                                is SpecialistsUIState.EditSpecialistsFromTasks -> {
                                    findNavController().navigate(R.id.action_specialistsFragment_to_tasksFragment)
                                }

                                is SpecialistsUIState.SpecialistsFromAddTask -> {
                                    findNavController().navigate(R.id.action_specialistsFragment_to_addNewTaskFragment)
                                }

                                else -> {}
                            }
                        }
                    }
                }
                lifecycleScope.launch {
                    viewModel.deleteDialogUIState.collect { dialogData ->
                        val state = dialogData.state
                        val deletedID = dialogData.deletedID
                        Log.d("?????????????????", "collect ${state}")
                        if (state) {
                            deleteDialog.createDialog { btnID ->
                                if (btnID == AlertDialog.BUTTON_POSITIVE) {
                                    viewModel.deleteSpecialist(deletedID)
                                }

                            }
                        } else {
                            deleteDialog.dismiss()
                        }
                    }
                }


                searchLayout.btnChooseFilterMethod.setOnClickListener {
                    chooseFilterMethodDialog.showDialog()
                }
                searchLayout.searchView.setOnQueryTextListener(object :
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        p0?.let { txt ->
                            if (txt.isEmpty()) {
                                viewModel.getAllSpecialist()
                            } else {
                                when (chooseFilterMethodDialog.getSearchMethod()) {
                                    SearchMethod.BY_NAME -> {
                                        // Search By Name
                                        Log.d("specialistAdapter", "searchSpecialistByName")

                                        viewModel.searchSpecialistByName(txt)

                                    }

                                    SearchMethod.BY_SPECIALIZATION -> {
                                        // Search By Specailization
                                        Log.d(
                                            "specialistAdapter",
                                            "searchSpecialistBySpecialization"
                                        )

                                        viewModel.searchSpecialistBySpecialization(txt)
                                    }

                                }
                            }
                        }
                        return true
                    }

                })
                btnAddNewSpecialist.setOnClickListener {
                    val action =
                        SpecialistsFragmentDirections.actionSpecialistsFragmentToSpecialistFragment()
                    action.whereFrom = whereFrom
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllSpecialist()
        arguments?.apply {
            val args = SpecialistsFragmentArgs.fromBundle(this)
            whereFrom = args.whereFrom
            taskID = args.taskID
        }
    }

    override fun talk(phone: String) {
        viewModel.talkPersonWhats(phone)
    }

    override fun onClickDelete(id: Int) {
        viewModel.updateStateDeleteDialog(true, id)
    }


    override fun onClickRoot(id: Int, name: String) {
        when (whereFrom) {
            Constants.ADD_TASK_FRAGMENT_ID -> {
                sharedViewModel.setSpecialistInfo(id, name)
                findNavController().navigate(R.id.action_specialistsFragment_to_addNewTaskFragment)
            }

            Constants.TASKS_FRAGMENT_ID -> {
                viewModel.editSpecialistTask(taskID, id)
                findNavController().navigate(R.id.action_specialistsFragment_to_addNewTaskFragment)
            }

        }


    }

    override fun onClickEdit(item: Specialist) {
        val action = SpecialistsFragmentDirections.actionSpecialistsFragmentToSpecialistFragment(
        )
        action.specialist = item
        findNavController().navigate(action)
    }

    override fun onClickSpecializations(id: Int) {
        val action =
            SpecialistsFragmentDirections.actionSpecialistsFragmentToSelectSpecializationsFragment()
        action.specialistID = id
        findNavController().navigate(action)
    }


}