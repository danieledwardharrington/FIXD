package com.fixdapp.internal.spacebook.feed

import androidx.lifecycle.ViewModel
import com.fixdapp.internal.spacebook.api.SpacebookApi
import com.fixdapp.internal.spacebook.persistence.SpacebookDatabase

class FeedViewModel(private val api: SpacebookApi, private val database: SpacebookDatabase): ViewModel() {

    fun getFeed(userId: String) {

    }


}