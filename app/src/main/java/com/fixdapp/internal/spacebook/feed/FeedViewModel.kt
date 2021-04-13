package com.fixdapp.internal.spacebook.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.fixdapp.internal.spacebook.api.SpacebookApi
import com.fixdapp.internal.spacebook.persistence.SpacebookDatabase
import kotlinx.coroutines.launch

class FeedViewModel(private val api: SpacebookApi, private val database: SpacebookDatabase): ViewModel() {

    fun getLoggedInUser() {
        viewModelScope.launch {
            val user = database.userDao().getUserById(1) //current user should always be index 1
            getFeed(user.id.toInt())
        }
    }

    private fun getFeed(userId: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 50,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { FeedPagingSource(api, userId) }
        ).liveData


}