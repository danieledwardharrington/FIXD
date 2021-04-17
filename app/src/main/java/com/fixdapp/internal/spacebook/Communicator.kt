package com.fixdapp.internal.spacebook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fixdapp.internal.spacebook.persistence.entities.UserEntity

/*
Basically just using this communicator view model to communicate between
the login and feed fragments with live data in order to load the proper feed
 */
class Communicator: ViewModel() {

    val userEntityLD = MutableLiveData<UserEntity>()

    fun setUserEntity(userId: Int, userName: String) {
        userEntityLD.value = UserEntity(userId, userName)
    }
}