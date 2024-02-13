package com.example.taskat.core.ui.fragment.specializations

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskat.R
import com.example.taskat.core.ui.adapter.adapters.SpecializationAdapter
import com.example.taskat.core.ui.adapter.listener.SetOnDeleteListener
import com.example.taskat.core.ui.fragment.BindingFragment
import com.example.taskat.core.ui.helper.CheckerVisibility
import com.example.taskat.core.ui.helper.DeleteDialog
import com.example.taskat.core.ui.helper.InitRecyclerView
import com.example.taskat.core.ui.helper.VisibilityLoadingLayout
import com.example.taskat.core.ui.uistate.DeleteUIState
import com.example.taskat.core.ui.uistate.specialization.AddSpecializationUIState
import com.example.taskat.core.ui.uistate.specialization.SpecializationsUIState
import com.example.taskat.databinding.FragmentSpecializtionsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SpecializationsFragment :
    BindingFragment<FragmentSpecializtionsBinding>(FragmentSpecializtionsBinding::inflate),
    SetOnDeleteListener {

    private val viewModel: SpecializationsViewModel by viewModels()
    private val specializationAdapter: SpecializationAdapter = SpecializationAdapter(this)
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var deleteDialog: DeleteDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        deleteDialog = DeleteDialog(requireContext())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        useBinding {
            it.apply {
                btnAddNewSpecializations.setOnClickListener {
                    viewModel.addNewSpecialization(edSpecializationName.text.toString())
                }
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.specializationsUIState.collect { uiState ->
                            when (uiState) {
                                is SpecializationsUIState.Loading -> {
                                    if (uiState.isLoading) {
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
                                    CheckerVisibility.setVisibility(searchLayout.root, View.VISIBLE)
                                    CheckerVisibility.initNonEmptyList(
                                        emptyLayout.root,
                                        header.root,
                                        rvSpecializations
                                    )
                                    val specializations = uiState.data
                                    InitRecyclerView.initWithAsyncDiffer(
                                        specializations,
                                        specializationAdapter.getListDiffer(),
                                        rvSpecializations,
                                        specializationAdapter,
                                        linearLayoutManager

                                    )


                                }

                                is SpecializationsUIState.Search -> {
                                    CheckerVisibility.setVisibility(
                                        requireBinding().searchLayout.root,
                                        View.VISIBLE
                                    )

                                    CheckerVisibility.initNonEmptyList(
                                        requireBinding().emptyLayout.root,
                                        requireBinding().header.root,
                                        requireBinding().rvSpecializations
                                    )
                                    val searchResultList = uiState.result
                                    InitRecyclerView.initWithAsyncDiffer(
                                        searchResultList,
                                        specializationAdapter.getListDiffer(),
                                        requireBinding().rvSpecializations,
                                        specializationAdapter,
                                        linearLayoutManager

                                    )


                                }

                                is SpecializationsUIState.Empty -> {
                                    CheckerVisibility.setVisibility(
                                        searchLayout.root,
                                        View.GONE
                                    )
                                    CheckerVisibility.initEmptyList(
                                        emptyLayout.root,
                                        header.root,
                                        rvSpecializations
                                    )
                                    emptyLayout.tvEmptyData.text =
                                        getString(R.string.specializations_empty)

                                }

                                is SpecializationsUIState.Error<*> -> {
                                    Toast.makeText(
                                        requireContext(),
                                        (uiState.err) as String,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.d("SpecializationsUIState", "Error - > ${uiState.err}")
                                }

                            }

                        }
                    }
                }
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.addSpecializationUIState.collect { uiState ->
                            when (uiState) {
                                is AddSpecializationUIState.Loading -> {
                                    if (uiState.isLoading) {
                                        VisibilityLoadingLayout.setVisibility(
                                            requireBinding().loadingLayout, true
                                        )

                                    } else {
                                        VisibilityLoadingLayout.setVisibility(
                                            requireBinding().loadingLayout, false
                                        )

                                    }
                                }

                                is AddSpecializationUIState.Success -> {
                                    Toast.makeText(
                                        requireContext(),
                                        uiState.msg,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    collectArgs()

                                }


                                is AddSpecializationUIState.Error -> {
                                    Toast.makeText(
                                        requireContext(),
                                        (uiState.err),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.d("SpecializationsUIState", "Error - > ${uiState.err}")
                                }

                            }

                        }
                    }
                }
                lifecycleScope.launch {

                    viewModel.returnDestinationUIState.collect { destinationID ->
                        if (destinationID == 1) {
                            findNavController().navigate(R.id.action_specializationsFragment_to_selectSpecializationsFragment)

                        }


                    }
                }

                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.deleteSpecializationUIState.collect { deleteUIState ->
                            when (deleteUIState) {
                                is DeleteUIState.Loading -> {
                                    if (deleteUIState.isLoading) {
                                        VisibilityLoadingLayout.setVisibility(
                                            requireBinding().loadingLayout, true
                                        )

                                    } else {
                                        VisibilityLoadingLayout.setVisibility(
                                            requireBinding().loadingLayout, false
                                        )

                                    }
                                }

                                is DeleteUIState.Success -> {

                                    Toast.makeText(
                                        requireContext(),
                                        "${deleteUIState.numOfRows} deleted",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }


                                is DeleteUIState.Error -> {
                                    Toast.makeText(
                                        requireContext(), (deleteUIState.err), Toast.LENGTH_SHORT
                                    ).show()

                                }

                            }
                        }
                    }
                }
                lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.deleteDialogUIState.collect { dialogData ->
                            val state = dialogData.state
                            val deletedID = dialogData.deletedID
                            if (state) {
                                deleteDialog.createDialog { btnID ->
                                    if (btnID == AlertDialog.BUTTON_POSITIVE) {
                                        viewModel.deleteSpecialization(deletedID)


                                    }

                                }
                            } else {
                                deleteDialog.dismiss()
                                viewModel.updateStateDialog(false, -1)

                            }
                        }

                    }

                }
            }
        }


    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllSpecializations()
        requireBinding().searchLayout.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { txt ->
                    if (txt.isEmpty()) {
                        viewModel.getAllSpecializations()
                    } else {
                        viewModel.searchSpecialization(txt)

                    }
                }
                Log.d("searchView", "onQueryTextChange:${newText}")
                return true
            }

        })

    }

    private fun collectArgs() {
        arguments?.apply {
            val args = SpecializationsFragmentArgs.fromBundle(this)
            val fromDestinationID = args.from
            viewModel.setDestination(fromDestinationID)
        }
    }

    private fun bindingViews() {
        useBinding { binding ->
            binding.apply {
                btnAddNewSpecializations.setOnClickListener {
                    viewModel.addNewSpecialization(edSpecializationName.text.toString())
                }


            }
        }
    }

    override fun onClickDelete(id: Int) {
        viewModel.updateStateDialog(true, id)
    }
}