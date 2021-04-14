package com.fixdapp.internal.spacebook.post.comments

import androidx.paging.PagingSource
import com.fixdapp.internal.spacebook.api.SpacebookApi
import com.fixdapp.internal.spacebook.api.models.individual.CommentModel
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_INDEX = 1

/*
class CommentsPagingSource(private val api: SpacebookApi, private val postId: Int):
    PagingSource<Int, CommentModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommentModel> {
        val position = params.key ?: STARTING_INDEX

        return try {
            val response = api.getPostComments(postId, position, params.loadSize)
            val comments = response.data!!

            LoadResult.Page(
                data = comments,
                prevKey = if (position == STARTING_INDEX) null else position - 1,
                nextKey = if (comments.isEmpty()) null else position + 1
            )

        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }
    }
}*/
