package com.fixdapp.internal.spacebook.util

import com.fixdapp.internal.spacebook.api.models.feed.UserModel
import com.fixdapp.internal.spacebook.persistence.entities.UserEntity

class Helpers {

    fun mapUserModelToEntity(userModel: UserModel): UserEntity {
        return UserEntity(userModel.id, userModel.email, userModel.name, userModel.registeredAt, userModel.githubUserName, userModel.rating)
    }
}