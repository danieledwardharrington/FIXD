package com.fixdapp.internal.spacebook.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.fixdapp.internal.spacebook.api.SpacebookApi
import com.fixdapp.internal.spacebook.api.TokenManager

//import com.fixdapp.internal.spacebook.post.comments.CommentsPagingSource

class PostViewModel(private val api: SpacebookApi, private val tokenManager: TokenManager): ViewModel() {

/*    fun getPostComments(postId: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 50,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CommentsPagingSource(api, postId) }
        ).liveData*/

    fun logout() {
        tokenManager.logout()
    }
}