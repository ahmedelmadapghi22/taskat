package com.example.taskat.core.ui.fragment.distributor.distributors

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskat.R
import com.example.taskat.core.ui.adapter.adapters.DistributorAdapter
import com.example.taskat.core.ui.adapter.listener.SetOnClickPersonListener
import com.example.taskat.core.ui.adapter.listener.SetOnClickWhats
import com.example.taskat.core.ui.adapter.listener.SetOnDeleteListener
import com.example.taskat.core.ui.common.SharedTaskViewModel
import com.example.taskat.core.ui.fragment.BindingFragment
import com.example.taskat.core.ui.helper.CheckerVisibility
import com.example.taskat.core.ui.helper.DeleteDialog
import com.example.taskat.core.ui.helper.InitRecyclerView
import com.example.taskat.core.ui.helper.VisibilityLoadingLayout
import com.example.taskat.core.ui.uistate.DeleteUIState
import com.example.taskat.core.ui.uistate.DistributorsUIState
import com.example.taskat.databinding.FragmentDistributorsBinding
import com.example.taskat.domain.model.Distributor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DistributorsFragment :
    BindingFragment<FragmentDistributorsBinding>(FragmentDistributorsBinding::inflate),
    SetOnClickWhats, SetOnDeleteListener, SetOnClickPersonListener {
    private val viewModel: DistributorsViewModel by viewModels()
    private val sharedViewModel: SharedTaskViewModel by activityViewModels()

    private val distributorsAdapter = DistributorAdapter(this, this, this)
    private lateinit var deleteDialog: DeleteDialog
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var whereFrom = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deleteDialog = DeleteDialog(requireContext())
        viewModel.getAllDistributor()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getDeleteDialogUIState().collect { dialogData ->
                    val state = dialogData.state
                    val deletedID = dialogData.deletedID
                    Log.d("?????????????????", "collect ${state}")
                    if (state) {
                        deleteDialog.createDialog { btnID ->
                            if (btnID == AlertDialog.BUTTON_POSITIVE) {
                                viewModel.deleteDistributor(deletedID)
                            }

                        }
                    } else {
                        deleteDialog.dismiss()
                    }
                }

            }

        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getDistributorsUIState().collect { distributorUIState ->
                    when (distributorUIState) {
                        is DistributorsUIState.Loading -> {
                            if (distributorUIState.isLoading) {
                                CheckerVisibility.setVisibility(
                                    requireBinding().loadingLayout.root, View.VISIBLE
                                )

                            } else {
                                CheckerVisibility.setVisibility(
                                    requireBinding().loadingLayout.root, View.GONE
                                )


                            }
                        }

                        is DistributorsUIState.Success -> {
                            CheckerVisibility.setVisibility(requireBinding().searchLayout.root,View.VISIBLE)
                            CheckerVisibility.initNonEmptyList(
                                requireBinding().emptyLayout.root,
                                requireBinding().distributorsHeaderLayout.root,
                                requireBinding().rvDistributors
                            )
                            val distributors = distributorUIState.data
                            InitRecyclerView.initWithAsyncDiffer(
                                distributors,
                                distributorsAdapter.getListDiffer(),
                                requireBinding().rvDistributors,
                                distributorsAdapter,
                                layoutManager = linearLayoutManager
                            )
                            Log.d("?????????????????", "${distributors}")

                            setNumbersDistributors(distributors.size)


                        }

                        is DistributorsUIState.Search -> {
                            CheckerVisibility.setVisibility(requireBinding().searchLayout.root,View.VISIBLE)
                            CheckerVisibility.initNonEmptyList(
                                requireBinding().emptyLayout.root,
                                requireBinding().distributorsHeaderLayout.root,
                                requireBinding().rvDistributors
                            )
                            val searchResultList = distributorUIState.result
                            InitRecyclerView.init(
                                requireBinding().rvDistributors,
                                distributorsAdapter,
                                layoutManager = linearLayoutManager
                            )
                            setNumbersDistributors(searchResultList.size)


                        }

                        is DistributorsUIState.Empty -> {
                            CheckerVisibility.initEmptyList(
                                requireBinding().emptyLayout.root,
                                requireBinding().distributorsHeaderLayout.root,
                                requireBinding().rvDistributors
                            )
                            CheckerVisibility.setVisibility(requireBinding().searchLayout.root,View.GONE)
                        }

                        is DistributorsUIState.Error<*> -> {
                            Toast.makeText(
                                requireContext(),
                                (distributorUIState.err) as String,
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.getDeleteDistributorsUIState().collect { deleteUIState ->
                when (deleteUIState) {
                    is DeleteUIState.Loading -> {
                        if (deleteUIState.isLoading) {
                            VisibilityLoadingLayout.setVisibility(
                                requireBinding().loadingLayout, true
                            )
                            Log.d("?????????????????", "Loading True")

                        } else {
                            VisibilityLoadingLayout.setVisibility(
                                requireBinding().loadingLayout, false
                            )
                            Log.d("?????????????????", "Loading false")

                        }
                    }

                    is DeleteUIState.Success -> {

                        Toast.makeText(
                            requireContext(), (deleteUIState.numOfRows), Toast.LENGTH_SHORT
                        ).show()

                    }


                    is DeleteUIState.Error -> {
                        Toast.makeText(
                            requireContext(), (deleteUIState.err), Toast.LENGTH_SHORT
                        ).show()
                        Log.d("?????????????????", "Error ${deleteUIState.err}")

                    }

                }
            }

        }
        useBinding {
            it.btnAddNewDistributor.setOnClickListener {
                val action = DistributorsFragmentDirections.actionDistributorsFragmentToNewDistributorFragment()
                action.whereFrom = whereFrom
                findNavController().navigate(action)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        arguments?.apply {
            val args = DistributorsFragmentArgs.fromBundle(this)
            whereFrom = args.whereFrom

        }
        linearLayoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        requireBinding().searchLayout.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { txt ->
                    if (txt.isEmpty()) {
                        viewModel.getAllDistributor()
                    } else {
                        viewModel.searchDistributor(txt)

                    }
                }
                Log.d("searchView", "onQueryTextChange:${newText}")
                return true
            }

        })

    }


    private fun setNumbersDistributors(count: Int) {
        requireBinding().distributorsHeaderLayout.tvTotalDistributors.text = "$count"
    }

    override fun talk(phone: String) {
        viewModel.talkToDistributor(phone)
    }

    override fun onClickDelete(id: Int) {
        viewModel.updateStateDeleteDialog(true, id)

    }

    override fun onClickRoot(id: Int, name: String) {
        sharedViewModel.setDistributorInfo(id, name)
        findNavController().navigate(R.id.action_distributorsFragment_to_addNewTaskFragment)
    }


}