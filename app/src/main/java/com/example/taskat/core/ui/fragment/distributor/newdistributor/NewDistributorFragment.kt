package com.example.taskat.core.ui.fragment.distributor.newdistributor

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.taskat.R
import com.example.taskat.core.ui.adapter.listener.SetOnClickCountry
import com.example.taskat.core.ui.common.SharedTaskViewModel
import com.example.taskat.core.ui.fragment.BindingFragment
import com.example.taskat.core.ui.helper.CountryCodeHelper
import com.example.taskat.core.ui.helper.VisibilityLoadingLayout
import com.example.taskat.core.ui.uistate.AddPersonUIState
import com.example.taskat.databinding.FragmentNewDisributorBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewDistributorFragment :
    BindingFragment<FragmentNewDisributorBinding>(FragmentNewDisributorBinding::inflate),
    SetOnClickCountry {


    private val viewModel: NewDistributorViewModel by viewModels()
    private val sharedTaskViewModel: SharedTaskViewModel by activityViewModels()

    @Inject
    lateinit var countryCodeHelper: CountryCodeHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addDistributorUIState.collect { state ->
                    when (state) {
                        is AddPersonUIState.Loading -> {
                            if (state.isLoading) {
                                VisibilityLoadingLayout.setVisibility(
                                    requireBinding().loadingLayout,
                                    true
                                )
                                Log.d("newDistributor", "AddDistributorUIState.Loading:Loading)")


                            } else {
                                VisibilityLoadingLayout.setVisibility(
                                    requireBinding().loadingLayout,
                                    false
                                )
                                Log.d(
                                    "newDistributor",
                                    "AddDistributorUIState.Loading:Finish Loading)"
                                )

                            }
                        }

                        is AddPersonUIState.Success -> {
                            if (state.isSuccess) {
                                makeToastFromStr("Saved")
                                viewModel.getLastDistributorID()
                            }


                        }

                        is AddPersonUIState.SuccessLastID -> {
                            val id = state.lastID
                            val name = requireBinding().edDistributorName.text.toString()
                            sharedTaskViewModel.setDistributorInfo(id,name)
                            makeToastFromStr("Saved")

                            findNavController().navigate(R.id.action_newDistributorFragment_to_addNewTaskFragment)


                        }

                        is AddPersonUIState.Error -> {
                            Log.d("newDistributor", "makeToastFromStr.Error:${state.err}")
                            makeToastFromStr(state.err) // Display the error message to the user
                            Log.d("newDistributor", "Error state received: ${state.err}")
                        }


                    }

                }
            }
        }
        requireBinding().btnAddNewDistributor.setOnClickListener {
            lifecycleScope.launch {
                viewModel.addNewDistributor(getDistributorName(), getCountryCode(), getPhone())
            }


        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            initCountryCode()
        }
    }

    private fun getDistributorName() = requireBinding().edDistributorName.text.toString()
    private fun getCountryCode() = requireBinding().edCodeCountry.text.toString()
    private fun getPhone() = requireBinding().edPhoneDistributor.text.toString()


    private suspend fun initCountryCode() {
        val countries = viewModel.getCountryCodes()
        (requireBinding().edCodeCountry as? AutoCompleteTextView)?.let {
            countryCodeHelper.initCountryCodeList(
                countries, this,
                it
            )
        }


    }

    override fun onClick(code: String) {
        requireBinding().edCodeCountry.setText(code)
    }

}