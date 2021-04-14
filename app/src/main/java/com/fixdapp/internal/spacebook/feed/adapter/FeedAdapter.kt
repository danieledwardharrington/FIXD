package com.fixdapp.internal.spacebook.feed.adapter

import android.content.Context
import android.provider.SearchRecentSuggestions
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fixdapp.internal.spacebook.R
import com.fixdapp.internal.spacebook.api.models.feed.ParentFeed
import com.fixdapp.internal.spacebook.api.models.individual.CommentModel
import com.fixdapp.internal.spacebook.databinding.ItemFeedEventBinding
import kotlin.math.floor

class FeedAdapter: PagingDataAdapter<ParentFeed, FeedAdapter.FeedViewHolder>(COMPARATOR) {
    private val TAG = "FeedAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val itemBinding = ItemFeedEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        when (holder) {
            is FeedViewHolder -> {
                getItem(holder.bindingAdapterPosition).let { parentFeed ->
                    if (parentFeed != null) {
                        holder.bind(parentFeed)
                    }
                }

                holder.itemView.setOnClickListener { view ->

                }
            }
        }
    }

    fun getItemAtPosition(position: Int): ParentFeed? {
        return getItem(position)
    }

    class FeedViewHolder(itemBinding: ItemFeedEventBinding, parentContext: Context): RecyclerView.ViewHolder(itemBinding.root) {
        private val TAG = "FeedViewHolder"

        private val context = parentContext
        private val dateTV = itemBinding.dateTv
        private val newPostTV = itemBinding.newPostTv
        private val eventTV = itemBinding.eventTv
        private val userRB = itemBinding.userRatingBar
        private val commentsTV = itemBinding.commentsTv
        private val arrowIV = itemBinding.arrowIv

        fun bind(parentFeed: ParentFeed) {
            dateTV.text = parentFeed.occurredAt
            var eventString = ""
            when (parentFeed) {
                is ParentFeed.PostFeed -> {
                    userRB.visibility = View.GONE
                    newPostTV.visibility = View.VISIBLE
                    commentsTV.visibility = View.VISIBLE
                    eventTV.text = parentFeed.postModel.title
                }

                is ParentFeed.CommentFeed -> {
                    newPostTV.visibility = View.GONE
                    commentsTV.visibility = View.GONE
                    userRB.visibility = View.VISIBLE
                    eventString = context.getString(R.string.commented_on)
                    eventString += " \n${parentFeed.commentModel.post!!.author.name}"
                    eventTV.text = eventString
                    userRB.rating = parentFeed.commentModel.post!!.author.rating!!.toFloat()
                }

                is ParentFeed.RatingFeed -> {
                    newPostTV.visibility = View.GONE
                    commentsTV.visibility = View.GONE
                    userRB.visibility = View.VISIBLE
                    val emoji = context.getString(R.string.party_emoji)
                    eventString = "Passed ${floor(parentFeed.ratingModel.rating)} stars! $emoji"
                    eventTV.text = eventString
                    userRB.rating = parentFeed.ratingModel.rating.toFloat()
                }

                is ParentFeed.GithubNewPRFeed -> {
                    newPostTV.visibility = View.GONE
                    commentsTV.visibility = View.GONE
                    userRB.visibility = View.GONE
                    eventString = context.getString(R.string.opened_pr)
                    eventString += " ${parentFeed.githubNewPR.pullRequestNumber} \nfor ${parentFeed.githubNewPR.repo}"
                    eventTV.text = eventString
                }

                is ParentFeed.GithubMergePRFeed -> {
                    newPostTV.visibility = View.GONE
                    commentsTV.visibility = View.GONE
                    userRB.visibility = View.GONE
                    eventString = "Merged ${parentFeed.githubNewPR.pullRequestNumber} into \n${parentFeed.githubNewPR.repo}"
                    eventTV.text = eventString
                }

                is ParentFeed.GithubPushFeed -> {
                    newPostTV.visibility = View.GONE
                    commentsTV.visibility = View.GONE
                    userRB.visibility = View.GONE
                    eventString = "Pushed commits to\n ${parentFeed.githubPushModel.repo} ${parentFeed.githubPushModel.branch}"
                    eventTV.text = eventString
                }

                is ParentFeed.GithubNewRepoFeed -> {
                    newPostTV.visibility = View.GONE
                    commentsTV.visibility = View.GONE
                    userRB.visibility = View.GONE
                    eventString = "Created a new repository \n${parentFeed.githubNewRepo.repo}"
                    eventTV.text = eventString
                }
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ParentFeed>() {
            override fun areItemsTheSame(oldItem: ParentFeed, newItem: ParentFeed): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ParentFeed, newItem: ParentFeed): Boolean {
                return oldItem == newItem
            }
        }
    }
}