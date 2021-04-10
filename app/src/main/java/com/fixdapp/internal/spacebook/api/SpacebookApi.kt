package com.fixdapp.internal.spacebook.api

import androidx.lifecycle.MutableLiveData
import com.fixdapp.internal.spacebook.api.models.ActivityModel
import com.fixdapp.internal.spacebook.api.models.PostModel
import com.fixdapp.internal.spacebook.api.models.SessionRequestModel
import com.fixdapp.internal.spacebook.api.models.UserModel
import com.fixdapp.internal.spacebook.api.response.SpacebookResponse
import retrofit2.http.*

interface SpacebookApi {
/*    @JsonClass(generateAdapter = true)
    data class ApiError(val type: String)

    @JsonClass(generateAdapter = true)
    data class ApiResponse<T>(
        val status: String,
        val data: T?,
        val error: ApiError?
    )

    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "id") val id: Int,
        @Json(name = "email") val email: String,
        @Json(name = "name") val name: String,
        @Json(name = "registeredAt") val registeredAt: Instant,
        @Json(name = "githubUsername") val githubUsername: String?,
        @Json(name = "rating") val rating: Double?,
    )

    @JsonClass(generateAdapter = true)
    data class SessionRequest(val email: String, val password: String)*/

    @POST("session")
    suspend fun login(@Body request: SessionRequestModel): SpacebookResponse<UserModel>

    @GET("users/{id}")
    suspend fun gerUserById(@Path("id") id: Int): MutableLiveData<SpacebookResponse<UserModel>>

    @GET("users/{userId}/feed")
    suspend fun getFeed(@Path("userId") userId: String, @Query("page") page: Int, @Query("perPage") perPage: Int): MutableLiveData<SpacebookResponse<ActivityModel>>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): MutableLiveData<SpacebookResponse<PostModel>>

    @DELETE("comments/{id}")
    suspend fun deleteCommentById(@Path("id") id: Int)

}
