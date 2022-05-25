package com.bashkir.documentstasks

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.room.Room
import com.bashkir.documentstasks.data.repositories.DocumentsTasksApi
import com.bashkir.documentstasks.data.repositories.localdata.preferences.LocalUserPreferences
import com.bashkir.documentstasks.data.repositories.localdata.room.AppDatabase
import com.bashkir.documentstasks.data.services.*
import com.bashkir.documentstasks.viewmodels.*
import okhttp3.*
import okhttp3.Cookie
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://api.ok654.org/"

val apiModule = module {
    single<CookieJar> {
        object : CookieJar {
            private var cookies = listOf<Cookie>()

            override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
                this.cookies = cookies
            }

            override fun loadForRequest(url: HttpUrl): MutableList<Cookie> = cookies.toMutableList()
        }
    }

    single {
        OkHttpClient.Builder().cookieJar(get()).build()
    }
}

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
            .client(get())
            .build()
            .create(DocumentsTasksApi::class.java)
    }
}

val localBaseDAOsModule = module {
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().taskDao() }
    single { get<AppDatabase>().notificationDao() }
    single { get<AppDatabase>().documentDao() }
}

val servicesModule = module {
    single { AuthService() }
    single { TasksService() }
    single { ProfileService() }
    single { NotificationsService() }
    single { DocumentsService() }
}

val viewModelModule = module {
    factory { params -> AuthViewModel(params.get(), androidContext(), get()) }
    factory { params -> TasksViewModel(params.get(), get(), androidContext()) }
    factory { params -> NotificationsViewModel(params.get(), get()) }
    factory { params -> ProfileViewModel(params.get(), androidContext(), get()) }
    factory { params -> DocumentsViewModel(params.get(), get(), androidContext()) }
}

val utilsModule = module {
    factory(named("isOnline")) {
        (androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.let {
                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || it.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            } ?: false
        }
    }
}

val modules = listOf(
    apiModule,
    repositoriesModule,
    localBaseDAOsModule,
    servicesModule,
    viewModelModule,
    utilsModule
)