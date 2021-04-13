package com.fixdapp.internal.spacebook.api.models.feed
import com.fixdapp.internal.spacebook.api.models.individual.CommentModel
import com.fixdapp.internal.spacebook.api.models.individual.PostModel
import com.fixdapp.internal.spacebook.api.models.individual.RatingModel
import com.fixdapp.internal.spacebook.api.models.individual.github.GithubEventModel
import com.fixdapp.internal.spacebook.api.models.individual.github.GithubPRModel
import com.fixdapp.internal.spacebook.api.models.individual.github.GithubPushModel
import kotlinx.serialization.*

/*
going with a sealed class and its children per the kotlinx
serialization documentation's recommendations for best practice
with polymorphic serialization
 */

@Polymorphic
@Serializable
sealed class ParentFeed {
    @SerialName("id") abstract val id: Int
    @SerialName("userId") abstract val userId: Int
    @SerialName("occurredAt") abstract val occurredAt: String

    @SerialName("NEW_POST")
    @Serializable
    data class PostFeed(@SerialName("data") val postModel: PostModel,
                        @SerialName("id") override val id: Int,
                        @SerialName("userId") override val userId: Int,
                        @SerialName("occurredAt") override val occurredAt: String
    ): ParentFeed()

    @SerialName("NEW_COMMENT")
    @Serializable
    data class CommentFeed(@SerialName("data") val commentModel: CommentModel,
                           @SerialName("id") override val id: Int,
                           @SerialName("userId") override val userId: Int,
                           @SerialName("occurredAt") override val occurredAt: String
    ): ParentFeed()

    @SerialName("HIGH_RATING")
    @Serializable
    data class RatingFeed(@SerialName("data") val ratingModel: RatingModel,
                          @SerialName("id") override val id: Int,
                          @SerialName("userId") override val userId: Int,
                          @SerialName("occurredAt") override val occurredAt: String
                          ): ParentFeed()


    @SerialName("GITHUB_NEW_REPO")
    @Serializable
    data class GithubNewRepoFeed(@SerialName("data") val githubNewRepo: GithubEventModel,
                                 @SerialName("id") override val id: Int,
                                 @SerialName("userId") override val userId: Int,
                                 @SerialName("occurredAt") override val occurredAt: String
                                 ): ParentFeed()

    @SerialName("GITHUB_NEW_PR")
    @Serializable
    data class GithubNewPRFeed(@SerialName("data") val githubNewPR: GithubPRModel,
                               @SerialName("id") override val id: Int,
                               @SerialName("userId") override val userId: Int,
                               @SerialName("occurredAt") override val occurredAt: String
                               ): ParentFeed()

    @SerialName("GITHUB_MERGED_PR")
    @Serializable
    data class GithubMergePRFeed(@SerialName("data") val githubNewPR: GithubPRModel,
                                 @SerialName("id") override val id: Int,
                                 @SerialName("userId") override val userId: Int,
                                 @SerialName("occurredAt") override val occurredAt: String
                                 ): ParentFeed()

    @SerialName("GITHUB_PUSH")
    @Serializable
    data class GithubPushFeed(@SerialName("data") val githubPushModel: GithubPushModel,
                              @SerialName("id") override val id: Int,
                              @SerialName("userId") override val userId: Int,
                              @SerialName("occurredAt") override val occurredAt: String
                              ): ParentFeed()
}