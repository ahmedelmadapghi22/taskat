package com.example.taskat.core.ui.fragment.specialist.selectspecilizations

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
import com.example.taskat.core.ui.adapter.adapters.ChooseSpecializationAdapter
import com.example.taskat.core.ui.adapter.listener.ChooseSpecializationListener
import com.example.taskat.core.ui.fragment.BindingFragment
import com.example.taskat.core.ui.helper.CheckerVisibility
import com.example.taskat.core.ui.helper.InitRecyclerView
import com.example.taskat.core.ui.uistate.SpecialistAndSpecializationUIState
import com.example.taskat.core.ui.uistate.specialization.SpecializationsUIState
import com.example.taskat.databinding.FragmentSelectSpecilzationsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectSpecializationsFragment :
    BindingFragment<FragmentSelectSpecilzationsBinding>(FragmentSelectSpecilzationsBinding::inflate),
    ChooseSpecializationListener {
    private lateinit var chooseSpecializationAdapter: ChooseSpecializationAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel: SelectSpecializationsViewModel by viewModels()
    private var specialistID = -1
    private var whereFrom = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseSpecializationAdapter = ChooseSpecializationAdapter(this)
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        useBinding {
            it.apply {
                CheckerVisibility.setVisibility(searchLayout.btnChooseFilterMethod,View.GONE)
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.specializationsUIState.collect { state ->
                            when (state) {
                                is SpecializationsUIState.Loading -> {
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

                                is SpecializationsUIState.Success -> {
                                    CheckerVisibility.initNonEmptyList(
                                        emptyLayout.root,
                                        rvSpecializations
                                    )
                                    val list = state.data
                                    chooseSpecializationAdapter.injectSpecializations(list)
                                    InitRecyclerView.init(
                                        recyclerView = rvSpecializations,
                                        adapter = chooseSpecializationAdapter,
                                        layoutManager = linearLayoutManager
                                    )
                                }

                                is SpecializationsUIState.Search -> {
                                    CheckerVisibility.initNonEmptyList(
                                        emptyLayout.root,
                                        rvSpecializations
                                    )
                                    val list = state.result
                                    chooseSpecializationAdapter.injectSpecializations(list)
                                    InitRecyclerView.init(
                                        recyclerView = rvSpecializations,
                                        adapter = chooseSpecializationAdapter,
                                        layoutManager = linearLayoutManager
                                    )
                                }

                                is SpecializationsUIState.Empty -> {
                                    if (state.isEmpty) {
                                        CheckerVisibility.initEmptyList(
                                            emptyLayout.root,
                                            rvSpecializations
                                        )
                                        emptyLayout.tvEmptyData.text =
                                            getString(R.string.err_no_specialization_to_select)
                                        emptyLayout.root.setOnClickListener {
                                            val goToSpecializationsFragmentAction =
                                                SelectSpecializationsFragmentDirections.actionSelectSpecializationsFragmentToSpecializationsFragment()
                                            goToSpecializationsFragmentAction.from = 1
                                            findNavController().navigate(
                                                goToSpecializationsFragmentAction
                                            )
                                        }
                                    }

                                }

                                is SpecializationsUIState.Error<*> -> {
                                    val errorMsg = state.err as String
                                    makeToastFromStr(errorMsg)
                                }


                            }

                        }
                    }
                }
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.specialistAndSpecializationsUIState.collect { state ->
                            when (state) {
                                is SpecialistAndSpecializationUIState.Loading -> {
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

                                is SpecialistAndSpecializationUIState.Assign -> {
                                   if(state.isAssign){
                                       makeToastFromStr("Success")
                                   }
                                }

                                is SpecialistAndSpecializationUIState.Delete -> {
                                    if(state.isDelete){
                                        makeToastFromStr("Deleted")
                                    }
                                }
                                is SpecialistAndSpecializationUIState.SuccessSpecializations -> {
                                    chooseSpecializationAdapter.injectSpecializationCheckedID(state.specializationsID)
                                    viewModel.getAllSpecializations()
                                }
                                is SpecialistAndSpecializationUIState.Error -> {
                                    makeToastFromStr(state.err)
                                }
                                is SpecialistAndSpecializationUIState.FinishSelectSpecializations -> {
                                    findNavController().navigate(R.id.action_selectSpecializationsFragment_to_addNewTaskFragment)
                                }

                            }

                        }
                    }
                }
                searchLayout.tvTitleSearch.text = getString(R.string.select_specializations)
                searchLayout.searchView.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText.equals("")) {
                            viewModel.getAllSpecializations()
                        } else {
                            viewModel.searchSpecialization(newText.toString())
                        }
                        return true
                    }

                })
                btnDone.setOnClickListener {
                    viewModel.finish(whereFrom)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        arguments?.apply {
            val args = SelectSpecializationsFragmentArgs.fromBundle(this)
            specialistID = args.specialistID
            whereFrom = args.whereFrom
            viewModel.getSpecializationsOfSpecialist(specialistID)

        }

    }

    override fun onChecked(id: Int) {
        Log.d("SelectFragment", "onChecked:$id")
        viewModel.saveSpecialization(specialistID,id)
    }

    override fun onUnChecked(id: Int) {
        Log.d("SelectFragment", "onUnChecked:$id")
        viewModel.deleteSpecialization(specialistID,id)
    }


}