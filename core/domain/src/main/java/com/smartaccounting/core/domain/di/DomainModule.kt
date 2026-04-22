package com.smartaccounting.core.domain.di

import com.smartaccounting.core.domain.usecase.bill.AddBillUseCase
import com.smartaccounting.core.domain.usecase.bill.DeleteBillUseCase
import com.smartaccounting.core.domain.usecase.bill.GetBillsUseCase
import com.smartaccounting.core.domain.usecase.bill.UpdateBillUseCase
import com.smartaccounting.core.domain.usecase.statistics.GetStatisticsUseCase
import com.smartaccounting.core.domain.usecase.tag.GetTagsUseCase
import com.smartaccounting.core.domain.usecase.tag.ManageTagUseCase
import com.smartaccounting.core.domain.usecase.user.GetUserUseCase
import com.smartaccounting.core.domain.usecase.user.LoginUseCase
import com.smartaccounting.core.domain.usecase.user.RegisterUseCase
import com.smartaccounting.core.domain.usecase.user.UpdateUserUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { GetUserUseCase(get()) }
    factory { UpdateUserUseCase(get()) }
    
    factory { GetBillsUseCase(get()) }
    factory { AddBillUseCase(get()) }
    factory { UpdateBillUseCase(get()) }
    factory { DeleteBillUseCase(get()) }
    
    factory { GetTagsUseCase(get()) }
    factory { ManageTagUseCase(get()) }
    
    factory { GetStatisticsUseCase(get()) }
}
