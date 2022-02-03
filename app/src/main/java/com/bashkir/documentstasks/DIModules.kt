package com.bashkir.documentstasks

import com.bashkir.documentstasks.viewmodels.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val apiModule = module {

}

val viewModelModule = module{
    factory { params -> AuthViewModel(params.get(), androidContext()) }
}