package com.fixdapp.internal.spacebook.feed

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fixdapp.internal.spacebook.api.SpacebookApi
import com.fixdapp.internal.spacebook.api.models.feed.ParentFeed
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_INDEX = 1

class FeedPagingSource(private val api: SpacebookApi, private val id: Int):
    PagingSource<Int, ParentFeed>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ParentFeed> {
        val position = params.key ?: STARTING_INDEX

        return try {
            val response = api.getFeed(id, position, params.loadSize)
            val events = response.data!!

            LoadResult.Page(
                data = events,
                prevKey = if (position == STARTING_INDEX) null else position - 1,
                nextKey = if (events.isEmpty()) null else position + 1
            )
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }


    }

    override fun getRefreshKey(state: PagingState<Int, ParentFeed>): Int? {
        return state.anchorPosition
    }
}
