package com.fixdapp.internal.spacebook.login

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fixdapp.internal.spacebook.api.SpacebookApi
import com.fixdapp.internal.spacebook.api.models.feed.ParentFeed
import com.fixdapp.internal.spacebook.api.models.feed.UserModel
import com.fixdapp.internal.spacebook.api.models.individual.SessionRequestModel
import com.fixdapp.internal.spacebook.api.models.individual.github.GithubEventModel
import com.fixdapp.internal.spacebook.login.LoginViewModel.State.Error.Reason.*
import com.fixdapp.internal.spacebook.persistence.SpacebookDatabase
import com.fixdapp.internal.spacebook.util.Helpers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val api: SpacebookApi, private val sbDatabse: SpacebookDatabase) : ViewModel() {

    sealed class State {
        object FillingOutForm : State()
        object LoggingIn : State()
        object Success : State()
        data class Error(val reason: Reason) : State() {
            enum class Reason { INVALID_EMAIL, INVALID_PASSWORD, INCORRECT_PASSWORD, NETWORK_ERROR }
        }
    }

    private val _state: MutableLiveData<State> = MutableLiveData(State.FillingOutForm)
    val state: LiveData<State> get() = _state

    var currentUserLD = MutableLiveData<UserModel>()

    fun login(email: String, password: String) {
        _state.value = State.LoggingIn
        if (!isValidEmail(email)) {
            _state.value = State.Error(INVALID_EMAIL)
            return
        }
        if (!isValidPassword(password)) {
            _state.value = State.Error(INVALID_PASSWORD)
            return
        }
        viewModelScope.launch {
            try {
                val res = api.login(SessionRequestModel(email, password))
                _state.value = when {
                    res.data != null -> State.Success
                    res.error?.type == "NOT_AUTHENTICATED" -> State.Error(INCORRECT_PASSWORD)
                    else -> State.Error(NETWORK_ERROR)
                }
                //checking some user info in logcat
                Log.d("USER ID: ", res.data!!.id.toString())
                Log.d("NAME: ", res.data.name)

                currentUserLD.postValue(res.data!!)

            } catch (e: HttpException) {
                // TODO: not the VM's responsibility to wrap retrofit
                if (e.response()?.code() == 401) {
                    _state.value = State.Error(INCORRECT_PASSWORD)
                } else {
                    _state.value = State.Error(NETWORK_ERROR)
                }
            }
        }

    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
}
