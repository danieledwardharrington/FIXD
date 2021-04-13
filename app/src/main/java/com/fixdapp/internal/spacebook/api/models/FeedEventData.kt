package com.fixdapp.internal.spacebook.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
This is a super jank way of going about this but for the life of me, I couldn't
figure out another way of dealing with the enum + its data, which depends on the enum type.
Basically, I'll know which members I can use by checking the ActivityModel type and from
there it's basically just memorization of which members won't be null, which is not ideal.
Unfortunately the only post addressing this direct issue online suggested exactly this solution.
 */

@Serializable
data class FeedEventData(   @SerialName("id")
                            val id: Int? = -1,

                            @SerialName("title")
                            val title: String? = "",

                            @SerialName("body")
                            val body: String? = "",

                            @SerialName("postedAt")
                            val postedAt: String? = "",

                            @SerialName("author")
                            val author: UserModel? = null,

                            @SerialName("message")
                            val message: String? = "",

                            @SerialName("userId")
                            val userId: Int? = -1,

                            @SerialName("postId")
                            val postId: Int? = -1,

                            @SerialName("commentedAt")
                            val commentedAt: String? = "",

                            @SerialName("raterId")
                            val raterId: Int? = -1,

                            @SerialName("rating")
                            val rating: Int? = -1,

                            @SerialName("ratedAt")
                            val ratedAt: String? = "",

                            @SerialName("githubId")
                            val githubId: String? = "",

                            @SerialName("url")
                            val url: String? = "",

                            @SerialName("branch")
                            val branch: String? = "",

                            @SerialName("pullRequestNumber")
                            val pullRequestNumber: Int? = -1
                            )
