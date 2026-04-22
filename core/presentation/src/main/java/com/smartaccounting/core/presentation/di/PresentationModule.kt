package com.smartaccounting.core.presentation.di

import com.smartaccounting.core.presentation.ui.auth.AuthViewModel
import com.smartaccounting.core.presentation.ui.bill.AddBillViewModel
import com.smartaccounting.core.presentation.ui.bill.BillListViewModel
import com.smartaccounting.core.presentation.ui.settings.SettingsViewModel
import com.smartaccounting.core.presentation.ui.statistics.StatisticsViewModel
import com.smartaccounting.core.presentation.ui.tag.TagViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { AuthViewModel(get(), get(), get()) }
    viewModel { BillListViewModel(get(), get(), get()) }
    viewModel { AddBillViewModel(get(), get()) }
    viewModel { TagViewModel(get(), get()) }
    viewModel { StatisticsViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}
