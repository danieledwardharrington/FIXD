package com.fixdapp.internal.spacebook.post

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
import com.fixdapp.internal.spacebook.api.models.individual.PostModel
import com.fixdapp.internal.spacebook.persistence.SpacebookDatabase
import com.fixdapp.internal.spacebook.post.comments.CommentsPagingSource
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//import com.fixdapp.internal.spacebook.post.comments.CommentsPagingSource

class PostViewModel(private val api: SpacebookApi, private val database: SpacebookDatabase, private val tokenManager: TokenManager): ViewModel() {
    private val TAG = "PostViewModel"

    private val currentId = MutableLiveData(DEFAULT_ID)

    val commentsLD = currentId.switchMap { id ->
        getPostComments(id).cachedIn(viewModelScope)
    }

    fun getComments(postId: Int) {
        Log.d(TAG, "getComments")
        currentId.value = postId
    }

    private fun getPostComments(postId: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 120,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CommentsPagingSource(api, postId) }
        ).liveData

    fun logout() {
        Log.d(TAG, "logout")
        tokenManager.logout()
        runBlocking {
            database.userDao().deleteAllUsers()
        }
    }

    companion object {
        private const val DEFAULT_ID = 0

    }

}