package com.fixdapp.internal.spacebook.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fixdapp.internal.spacebook.api.SpacebookApi

class PostViewModel(private val api: SpacebookApi): ViewModel() {

    fun getPostComments(postId: Int) {
        viewModelScope
    }
}