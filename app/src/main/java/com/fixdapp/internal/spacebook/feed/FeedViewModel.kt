package com.fixdapp.internal.spacebook.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fixdapp.internal.spacebook.api.SpacebookApi
import com.fixdapp.internal.spacebook.persistence.SpacebookDatabase
import kotlinx.coroutines.launch

class FeedViewModel(private val api: SpacebookApi, private val database: SpacebookDatabase): ViewModel() {

    fun getFeed(userId: String) {
        viewModelScope.launch {

        }
    }


}