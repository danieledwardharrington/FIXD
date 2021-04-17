package com.fixdapp.internal.spacebook.post.comments

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fixdapp.internal.spacebook.api.models.individual.CommentModel
import com.fixdapp.internal.spacebook.databinding.ItemCommentBinding
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*

class CommentsAdapter: PagingDataAdapter<CommentModel, CommentsAdapter.CommentViewHolder>(COMPARATOR) {
    private val TAG = "CommentsAdapter"

    private lateinit var itemLoaded: OnItemLoadedListener

    interface OnItemLoadedListener {
        fun itemLoaded()
    }

    fun setOnItemLoadedListener(newListener: OnItemLoadedListener) {
        itemLoaded = newListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val itemBinding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        when (holder) {
            is CommentViewHolder -> {
                getItem(holder.bindingAdapterPosition).let { commentModel ->
                    if (commentModel != null) {
                        holder.bind(commentModel)
                    }
                    itemLoaded.itemLoaded()
                }
            }
        }
    }

    fun getItemAtPosition(position: Int): CommentModel? {
        Log.d(TAG, "getItemAtPosition")
        return getItem(position)
    }

    class CommentViewHolder(itemBinding: ItemCommentBinding, parentContext: Context): RecyclerView.ViewHolder(itemBinding.root) {
        private val TAG = "CommentViewHolder"

        private val commentContentTV = itemBinding.commentContentTv
        private val dateTV = itemBinding.dateTv
        private val commenterNameTV = itemBinding.commenterNameTv
        private val commenterRB = itemBinding.userRb

        fun bind(commentModel: CommentModel) {
            commentContentTV.text = commentModel.message
            if (commentModel.commentAuthor != null) {
                commenterNameTV.text = commentModel.commentAuthor!!.name
                commenterRB.rating = commentModel.commentAuthor!!.rating!!.toFloat()
            }
            val date = commentModel.commentedAt.dropLast(1)
            val localDate = LocalDateTime.parse(date)
            val month = localDate.month.getDisplayName(TextStyle.SHORT, Locale.US)
            val day = localDate.dayOfMonth
            val year = localDate.year
            val dateStr = "$day $month $year"
            dateTV.text = dateStr
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<CommentModel>() {
            override fun areItemsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}