package com.fixdapp.internal.spacebook

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fixdapp.internal.spacebook.api.InstantAdapter
import com.fixdapp.internal.spacebook.api.SpacebookApi
import com.fixdapp.internal.spacebook.api.TokenManager
import com.fixdapp.internal.spacebook.persistence.SpacebookDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.moshi.Moshi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

/**
 * This is a very MVP dependency injection mechanism. It uses lazy vars for singletons,
 * and could use getter props for temporary instances. This could be replaced by something
 * like Hilt.
 */
class Dependencies(private val applicationContext: Context) {
    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(InstantAdapter())
            .build()
    }

    val tokenManager: TokenManager by lazy {
        TokenManager(applicationContext)
    }

    val api: SpacebookApi by lazy {
        val contentType = MediaType.get("application/json")
        Retrofit.Builder()
            .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory(contentType))
            .baseUrl("https://spacebook-code-challenge.herokuapp.com/api/")
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(tokenManager)
                    .build()
            )
            .build()
            .create()
    }

    val sbDatabase: SpacebookDatabase = SpacebookDatabase.getDatabaseInstance(applicationContext)!!
}

val Activity.dependencies: Dependencies get() = (application as App).dependencies
val Fragment.dependencies: Dependencies get() = requireActivity().dependencies

/**
 * This is a helper that allows controllers to construct ViewModels from Dependencies.
 */
fun Fragment.fromDependencies(block: Dependencies.() -> ViewModel): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return block.invoke(dependencies) as T
        }
    }
}
