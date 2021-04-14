package com.fixdapp.internal.spacebook.post

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.fixdapp.internal.spacebook.feed.FeedViewModel
import com.fixdapp.internal.spacebook.fromDependencies

class PostFragment: Fragment() {

    private val viewModel: PostViewModel by activityViewModels {
        fromDependencies { PostViewModel(api) }
    }
}