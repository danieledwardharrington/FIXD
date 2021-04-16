package com.fixdapp.internal.spacebook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/*
Basically just using this communicator view model to communicate between
the login and feed fragments with live data in order to load the proper feed
 */
class Communicator: ViewModel() {

    val userIdLD = MutableLiveData<Int>()

    fun setUserId(userId: Int) {
        userIdLD.value = userId
    }
}