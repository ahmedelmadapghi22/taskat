package com.example.taskat.core.ui.fragment.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.taskat.R
import com.example.taskat.core.ui.adapter.adapters.CurrencySummaryAdapter
import com.example.taskat.core.ui.fragment.BindingFragment
import com.example.taskat.core.ui.helper.CheckerVisibility
import com.example.taskat.core.ui.helper.InitRecyclerView
import com.example.taskat.core.ui.helper.StringResourceHelper
import com.example.taskat.core.ui.uistate.HomeFragmentUIState
import com.example.taskat.databinding.FragmentHomeBinding
import com.example.taskat.domain.model.SummaryTask
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    @Inject
    lateinit var stringResourceHelper: StringResourceHelper
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var summaryAdapter: CurrencySummaryAdapter
    private lateinit var incomeSummaryLayoutManager: GridLayoutManager
    private lateinit var outcomeSummaryLayoutManager: GridLayoutManager
    private var isIncomeSummaryShow: Boolean = false
    private var isOutcomeSummaryShow: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        summaryAdapter = CurrencySummaryAdapter(stringResourceHelper)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        useBinding {
            it.apply {
                lifecycleScope.launch {
                    viewModel.homeUIState.collect { homeFragmentUIState ->
                        when (homeFragmentUIState) {
                            is HomeFragmentUIState.Loading -> {
                                if (homeFragmentUIState.isLoading) {
                                    CheckerVisibility.setVisibility(
                                        loadingLayout.root,
                                        View.VISIBLE
                                    )
                                } else {
                                    CheckerVisibility.setVisibility(loadingLayout.root, View.GONE)

                                }
                            }

                            is HomeFragmentUIState.SummaryTasks -> {
                                val summaryTasks = homeFragmentUIState.summaryTask
                                setSummaryTask(summaryTasks)
                            }

                            is HomeFragmentUIState.ShowSummaryIncome -> {
                                doWhenClickBtnIncomeSummary(homeFragmentUIState.isShow)

                            }

                            is HomeFragmentUIState.SummaryIncome -> {
                                CheckerVisibility.setVisibility(tvEmptyShowTodayIncome, View.GONE)
                                CheckerVisibility.setVisibility(rvSummaryIncome, View.VISIBLE)
                                incomeSummaryLayoutManager = GridLayoutManager(
                                    requireContext(),
                                    2
                                )
                                summaryAdapter.setSummaryList(homeFragmentUIState.summaryIncomeList)
                                InitRecyclerView.init(
                                    rvSummaryIncome,
                                    summaryAdapter,
                                    incomeSummaryLayoutManager
                                )
                            }

                            is HomeFragmentUIState.EmptySummaryIncome -> {
                                CheckerVisibility.setVisibility(rvSummaryIncome, View.GONE)
                                CheckerVisibility.setVisibility(
                                    tvEmptyShowTodayIncome,
                                    View.VISIBLE
                                )
                                tvEmptyShowTodayIncome.text =
                                    stringResourceHelper.getStringFromRes(R.string.no_income_summary_today)
                            }

                            is HomeFragmentUIState.ShowSummaryOutcome -> {
                                doWhenClickBtnOutcomeSummary(homeFragmentUIState.isShow)
                            }

                            is HomeFragmentUIState.SummaryOutcome -> {
                                CheckerVisibility.setVisibility(tvEmptyShowTodayOutcome, View.GONE)
                                CheckerVisibility.setVisibility(rvSummaryOutcome, View.VISIBLE)
                                outcomeSummaryLayoutManager = GridLayoutManager(
                                    requireContext(),
                                    2
                                )
                                summaryAdapter.setSummaryList(homeFragmentUIState.summaryOutcomeList)
                                InitRecyclerView.init(
                                    rvSummaryOutcome,
                                    summaryAdapter,
                                    outcomeSummaryLayoutManager
                                )
                            }

                            is HomeFragmentUIState.EmptySummaryOutcome -> {
                                CheckerVisibility.setVisibility(rvSummaryOutcome, View.GONE)
                                CheckerVisibility.setVisibility(
                                    tvEmptyShowTodayOutcome,
                                    View.VISIBLE
                                )
                                tvEmptyShowTodayOutcome.text =
                                    stringResourceHelper.getStringFromRes(R.string.no_outcome_summary_today)
                            }

                            is HomeFragmentUIState.Error -> {
                                makeToastFromStr(homeFragmentUIState.err)
                            }

                        }
                    }
                }
                btnShowTodayIncome.setOnClickListener {
                    isIncomeSummaryShow = !isIncomeSummaryShow
                    viewModel.showIncomeSummary(isIncomeSummaryShow)
                }
                btnShowTodayOutcome.setOnClickListener {
                    isOutcomeSummaryShow = !isOutcomeSummaryShow
                    viewModel.showOutcomeSummary(isOutcomeSummaryShow)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getSummaryTasksToday()
    }

    private fun doWhenClickBtnOutcomeSummary(state: Boolean) {
        requireBinding().apply {
            if (state) {
                btnShowTodayOutcome.setImageResource(R.drawable.ic_arrow_up)
                viewModel.getSummeryOutcome()

            } else {
                btnShowTodayOutcome.setImageResource(R.drawable.ic_arrow_down)
                CheckerVisibility.setVisibility(tvEmptyShowTodayOutcome, View.GONE)
                CheckerVisibility.setVisibility(rvSummaryOutcome, View.GONE)


            }
        }

    }

    private fun doWhenClickBtnIncomeSummary(state: Boolean) {
        requireBinding().apply {
            if (state) {
                btnShowTodayIncome.setImageResource(R.drawable.ic_arrow_up)
                viewModel.getSummeryIncome()

            } else {
                btnShowTodayIncome.setImageResource(R.drawable.ic_arrow_down)
                CheckerVisibility.setVisibility(tvEmptyShowTodayIncome, View.GONE)
                CheckerVisibility.setVisibility(rvSummaryIncome, View.GONE)


            }
        }

    }

    private fun appendCountToTextView(count: Int, textView: TextView) {
        textView.text = buildString {
            append("(")
            append("$count")
            append(")")
        }
    }

    private fun setSummaryTask(summaryTask: SummaryTask) {
        summaryTask.apply {
            requireBinding().apply {
                tvCountRecentTasks.text = buildString {
                    append(stringResourceHelper.getStringFromRes(R.string.there_are))
                    append(" $recentTasks ")
                    append(stringResourceHelper.getStringFromRes(R.string.tasks))
                    append(" ")
                    append(stringResourceHelper.getStringFromRes(R.string.today))

                }
                appendCountToTextView(completeTasksToday, tvCountCompleteTasks)
                appendCountToTextView(inDoTasksToday, tvCountIndoTasks)
                appendCountToTextView(paidDistributorTasksToday, tvPayDistributorTasks)
                appendCountToTextView(unPaidDistributorTasksToday, tvUnPayDistributorTasks)
                appendCountToTextView(paidSpecialistTasksToday, tvPaySpecialistTasks)
                appendCountToTextView(unPaidSpecialistTasksToday, tvUnPaySpecialistTasks)
            }
        }
    }
}