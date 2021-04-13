package com.fixdapp.internal.spacebook.api

import com.fixdapp.internal.spacebook.api.models.feed.ParentFeed
import com.fixdapp.internal.spacebook.api.models.individual.PostModel
import com.fixdapp.internal.spacebook.api.models.individual.SessionRequestModel
import com.fixdapp.internal.spacebook.api.models.feed.UserModel
import com.fixdapp.internal.spacebook.api.models.individual.CommentModel
import com.fixdapp.internal.spacebook.api.response.SpacebookResponse
import retrofit2.http.*

interface SpacebookApi {

    @POST("session")
    suspend fun login(@Body request: SessionRequestModel): SpacebookResponse<UserModel>

    @GET("users/{id}")
    suspend fun gerUserById(@Path("id") id: Int): SpacebookResponse<UserModel>

    @GET("users/{userId}/feed")
    suspend fun getFeed(@Path("userId") userId: Int, @Query("page") page: Int, @Query("perPage") perPage: Int): SpacebookResponse<List<ParentFeed>>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): SpacebookResponse<PostModel>

    @GET("posts/{postId}/comments")
    suspend fun getPostComments(@Path("postId")postId: Int, @Query("page") page: Int, @Query("perPage") perPage: Int): SpacebookResponse<List<CommentModel>>

    @DELETE("comments/{id}")
    suspend fun deleteCommentById(@Path("id") id: Int)

}
