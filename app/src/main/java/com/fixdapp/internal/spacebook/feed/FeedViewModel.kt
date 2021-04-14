package com.fixdapp.internal.spacebook.feed

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.fixdapp.internal.spacebook.api.SpacebookApi
import com.fixdapp.internal.spacebook.api.TokenManager
import com.fixdapp.internal.spacebook.api.models.individual.CommentModel
import com.fixdapp.internal.spacebook.persistence.SpacebookDatabase
import kotlinx.coroutines.launch
import retrofit2.HttpException

class FeedViewModel(private val api: SpacebookApi, private val database: SpacebookDatabase, private val tokenManager: TokenManager): ViewModel() {
    private val TAG = "FeedViewModel"

    private var numCommentsLD = MutableLiveData<Int>()

    fun getFeed(userId: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 50,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { FeedPagingSource(api, userId) }
        ).liveData

    fun getNumComments(postId: Int) {
        try {
            viewModelScope.launch {
                numCommentsLD.postValue(api.getPostComments(postId, 1, 1).data?.size)
            }
        } catch (ex: HttpException) {
            Log.e(TAG, ex.toString())
        }
    }

    fun logout() {
        tokenManager.logout()
    }

}