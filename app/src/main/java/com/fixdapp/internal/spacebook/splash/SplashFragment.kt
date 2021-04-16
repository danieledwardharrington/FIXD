package com.fixdapp.internal.spacebook.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.fixdapp.internal.spacebook.Communicator
import com.fixdapp.internal.spacebook.R
import com.fixdapp.internal.spacebook.fromDependencies

class SplashFragment : Fragment() {

    val SPLASH_KEY = 1

    private val viewModel: SplashViewModel by activityViewModels {
        fromDependencies { SplashViewModel(tokenManager) }
    }
    private val communicator: Communicator by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_splash, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
            if (isLoggedIn) {
                val action = SplashFragmentDirections.actionSplashToMain(SPLASH_KEY)
                findNavController().navigate(action)
            } else {
                findNavController().navigate(R.id.splash_to_login)
            }
        }
    }
}
