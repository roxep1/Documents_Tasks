package com.bashkir.documentstasks

import androidx.room.Room
import com.bashkir.documentstasks.data.repositories.DocumentsTasksApi
import com.bashkir.documentstasks.data.repositories.localdata.preferences.LocalUserPreferences
import com.bashkir.documentstasks.data.repositories.localdata.room.AppDatabase
import com.bashkir.documentstasks.data.repositories.localdata.room.UserDao
import com.bashkir.documentstasks.data.services.AuthService
import com.bashkir.documentstasks.data.services.ProfileService
import com.bashkir.documentstasks.data.services.TasksService
import com.bashkir.documentstasks.viewmodels.AuthViewModel
import com.bashkir.documentstasks.viewmodels.ProfileViewModel
import com.bashkir.documentstasks.viewmodels.TasksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val BASE_URL = "https://documents-tasks-api.herokuapp.com/"

val repositoriesModule = module {
    single { LocalUserPreferences(androidContext()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "documents-tasks"
        ).build()
    }
    single{
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DocumentsTasksApi::class.java)
    }
}

val localBaseDAOsModule = module {
    single { get<AppDatabase>().userDao() }
}

val servicesModule = module {
    single { AuthService() }
    single { TasksService() }
    single { ProfileService() }
}

val viewModelModule = module {
    factory { params -> AuthViewModel(params.get(), androidContext(), get()) }
    factory { params -> TasksViewModel(params.get(),  get()) }
    factory { params -> ProfileViewModel(params.get(), androidContext(), get()) }
}

val modules = listOf(
    repositoriesModule,
    localBaseDAOsModule,
    servicesModule,
    viewModelModule
)