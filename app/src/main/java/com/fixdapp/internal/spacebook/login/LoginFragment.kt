package com.fixdapp.internal.spacebook.login

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.fixdapp.internal.spacebook.Communicator
import com.fixdapp.internal.spacebook.R
import com.fixdapp.internal.spacebook.api.models.feed.UserModel
import com.fixdapp.internal.spacebook.databinding.FragmentLoginBinding
import com.fixdapp.internal.spacebook.fromDependencies
import com.fixdapp.internal.spacebook.login.LoginViewModel.State.*
import com.fixdapp.internal.spacebook.login.LoginViewModel.State.Error.Reason.*
import kotlinx.coroutines.runBlocking


class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"

    val LOGIN_KEY = 2

    private val viewModel: LoginViewModel by activityViewModels {
        fromDependencies { LoginViewModel(api, sbDatabase) }
    }

    private val communicator: Communicator by activityViewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, this::onStateChanged)
        viewModel.currentUserLD.observe(viewLifecycleOwner) { userModel ->
            communicator.setUserId(userModel.id)
        }

        //hardcoding email and password so I don't have to type it in every time
        val emailEditable = SpannableStringBuilder("elmira@example.net")
        val passwordEditable = SpannableStringBuilder("bnpzvR+W")
        binding.LoginEmailField.editText!!.text = emailEditable
        binding.LoginPasswordField.editText!!.text = passwordEditable

        binding.LoginSubmit.setOnClickListener {
            val email = binding.LoginEmailField.editText!!.text.toString()
            val password = binding.LoginPasswordField.editText!!.text.toString()
            viewModel.login(email, password)
            dismissSoftKeyboard()
        }
    }

    private fun onStateChanged(state: LoginViewModel.State) {
        binding.LoginEmailField.error = null
        binding.LoginPasswordField.error = null
        binding.LoginSubmit.visibility = View.VISIBLE
        binding.LoginLoading.visibility = View.INVISIBLE
        binding.LoginErrorMessage.visibility = View.INVISIBLE
        when (state) {
            FillingOutForm -> return
            LoggingIn -> {
                binding.LoginSubmit.visibility = View.INVISIBLE
                binding.LoginLoading.visibility = View.VISIBLE
            }
            is Error -> when (state.reason) {
                INVALID_EMAIL -> binding.LoginEmailField.error = getString(R.string.invalid_email)
                INVALID_PASSWORD -> binding.LoginPasswordField.error =
                    getString(R.string.invalid_password)
                INCORRECT_PASSWORD -> binding.LoginPasswordField.error =
                    getString(R.string.incorrect_password)
                NETWORK_ERROR -> binding.LoginErrorMessage.visibility = View.VISIBLE
            }
            Success -> {
                val action = LoginFragmentDirections.loginFragmentToFeedFragment(LOGIN_KEY)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun dismissSoftKeyboard() {
        (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}
