package com.bashkir.documentstasks

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.bashkir.documentstasks.data.repositories.DocumentsTasksApi
import com.bashkir.documentstasks.data.repositories.localdata.preferences.LocalUserPreferences
import com.bashkir.documentstasks.data.repositories.localdata.room.AppDatabase
import com.bashkir.documentstasks.data.services.AuthService
import com.bashkir.documentstasks.data.services.NotificationsService
import com.bashkir.documentstasks.data.services.ProfileService
import com.bashkir.documentstasks.data.services.TasksService
import com.bashkir.documentstasks.viewmodels.AuthViewModel
import com.bashkir.documentstasks.viewmodels.NotificationsViewModel
import com.bashkir.documentstasks.viewmodels.ProfileViewModel
import com.bashkir.documentstasks.viewmodels.TasksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://documents-tasks-api.herokuapp.com/"

val repositoriesModule = module {
    single { LocalUserPreferences(androidContext()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "documents-tasks"
        ).fallbackToDestructiveMigration().build()
    }
    single {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DocumentsTasksApi::class.java)
    }
}

val localBaseDAOsModule = module {
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().taskDao() }
    single { get<AppDatabase>().notificationDao() }
}

val servicesModule = module {
    single { AuthService() }
    single { TasksService() }
    single { ProfileService() }
    single { NotificationsService() }
}

val viewModelModule = module {
    factory { params -> AuthViewModel(params.get(), androidContext(), get()) }
    factory { params -> TasksViewModel(params.get(), get()) }
    factory { params -> NotificationsViewModel(params.get(), get()) }
    factory { params -> ProfileViewModel(params.get(), androidContext(), get()) }
}

val utilsModule = module {
    factory(named("isOnline")) {
        val cm =
            androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.activeNetwork != null
    }
}

val modules = listOf(
    repositoriesModule,
    localBaseDAOsModule,
    servicesModule,
    viewModelModule,
    utilsModule
)