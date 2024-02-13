package com.example.taskat.core.ui.fragment.specialist.newspecialist

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
import com.example.taskat.core.ui.helper.CheckerVisibility
import com.example.taskat.core.ui.helper.CountryCodeHelper
import com.example.taskat.core.ui.uistate.AddSpecialistUIState
import com.example.taskat.databinding.FragmentSpecialistBinding
import com.example.taskat.domain.model.Specialist
import com.example.taskat.domain.util.PreConditionPhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddSpecialistFragment :
    BindingFragment<FragmentSpecialistBinding>(FragmentSpecialistBinding::inflate),
    SetOnClickCountry {

    @Inject
    lateinit var countryCodeHelper: CountryCodeHelper
    private val viewModel: AddSpecialistViewModel by viewModels()
    private val sharedTaskViewModel:SharedTaskViewModel by activityViewModels ()

    private var oldSpecialist: Specialist? = null
    private var whereFrom: Int = -1
    private var isEdit: Boolean = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        useBinding {
            it.apply {
                btnAddNewSpecialist.setOnClickListener {
                    viewModel.addNewSpecialist(
                        edSpecialistName.text.toString(),
                        edCodeCountry.text.toString(),
                        edSpecialistPhone.text.toString(),
                        edSpecialistNotes.text.toString(),
                        ratingBar.rating
                    )
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addSpecialistUIState.collect { state ->
                    when (state) {
                        is AddSpecialistUIState.Loading -> {
                            Log.d("AddSpecialistUIState", "onViewCreated:Loading ")

                            if (state.isLoading) {
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

                        is AddSpecialistUIState.Success -> {
                            Log.d("AddSpecialistUIState", "onViewCreated:Success ")
                            if (state.isSuccess) {
                                if (isEdit) {
                                    findNavController().navigate(R.id.action_specialistFragment_to_specialistsFragment)
                                } else {
                                    viewModel.getLastID()
                                }

                            }
                        }

                        is AddSpecialistUIState.SuccessLastID -> {
                            sharedTaskViewModel.setSpecialistInfo(state.lastID,requireBinding().edSpecialistName.text.toString())
                            val goToSelectSpecializationsFragment =
                                AddSpecialistFragmentDirections.actionSpecialistFragmentToSelectSpecializationsFragment()
                            goToSelectSpecializationsFragment.specialistID = state.lastID
                            goToSelectSpecializationsFragment.whereFrom = whereFrom
                            findNavController().navigate(goToSelectSpecializationsFragment)
                            Log.d("AddSpecialistUIState", "onViewCreated:SuccessLastID ")

                        }

                        is AddSpecialistUIState.Error -> {
                            Log.d("AddSpecialistUIState", "onViewCreated:Error ")

                            makeToastFromStr(state.err)
                        }

                        is AddSpecialistUIState.Edit -> {
                            setSpecialistToView(state.specialist)
                            requireBinding().btnAddNewSpecialist.text =
                                getString(R.string.apply_change)
                            requireBinding().btnAddNewSpecialist.setOnClickListener {
                                viewModel.editSpecialist(oldSpecialist!!, getSpecialistData())
                            }
                        }


                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            initCountryCode()
        }
        arguments?.apply {
            val args = AddSpecialistFragmentArgs.fromBundle(this)
            oldSpecialist = args.specialist
            whereFrom = args.whereFrom
            if (oldSpecialist != null) {
                oldSpecialist?.let {
                    isEdit = true
                    viewModel.setSpecialistEditable(it)
                }
            }
        }

    }

    override fun onStop() {
        super.onStop()
        isEdit = false
    }

    private fun getSpecialistData(): Specialist {
        val id = oldSpecialist!!.id
        val name = requireBinding().edSpecialistName.text.toString()
        val phone =
            "${requireBinding().edSpecialistPhone.text}${requireBinding().edCodeCountry.text}"
        val notes = requireBinding().edSpecialistNotes.text.toString()
        val rating = requireBinding().ratingBar.rating
        return Specialist(id, name, phone, rating, notes)
    }

    private fun setSpecialistToView(specialist: Specialist) {

        requireBinding().apply {
            edSpecialistName.setText(specialist.name)
            edSpecialistPhone.setText(PreConditionPhone.getPhoneNumber(specialist.phone))
            edCodeCountry.setText(PreConditionPhone.getCountryCode(specialist.phone))
            edSpecialistNotes.setText(specialist.notes)
            ratingBar.rating = specialist.evaluation
        }
    }

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