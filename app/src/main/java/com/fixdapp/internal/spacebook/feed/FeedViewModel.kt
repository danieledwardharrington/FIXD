package com.fixdapp.internal.spacebook.feed

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.fixdapp.internal.spacebook.api.SpacebookApi
import com.fixdapp.internal.spacebook.api.TokenManager
import com.fixdapp.internal.spacebook.api.models.individual.CommentModel
import com.fixdapp.internal.spacebook.persistence.SpacebookDatabase
import com.fixdapp.internal.spacebook.persistence.entities.UserEntity
import kotlinx.coroutines.launch
import retrofit2.HttpException

class FeedViewModel(private val api: SpacebookApi, private val database: SpacebookDatabase, private val tokenManager: TokenManager): ViewModel() {
    private val TAG = "FeedViewModel"

    private val currentId = MutableLiveData(DEFAULT_ID)
    val feedPD = currentId.switchMap { id ->
        getFeed(id).cachedIn(viewModelScope)
    }

    fun getUserFeed(userId: Int) {
        Log.d(TAG, "getUserFeed")
        currentId.value = userId
    }

    private fun getFeed(userId: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 120,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { FeedPagingSource(api, userId) }
        ).liveData

    fun getNumComments(postId: Int) {
        try {
            viewModelScope.launch {
            }
        } catch (ex: HttpException) {
            Log.e(TAG, ex.toString())
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            database.userDao().getUserById(1)
        }
    }

    fun logout() {
        tokenManager.logout()
    }

    companion object {
        private const val DEFAULT_ID = 1
    }

}