package com.fixdapp.internal.spacebook.post

import android.os.Bundle
import android.view.*
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fixdapp.internal.spacebook.R
import com.fixdapp.internal.spacebook.databinding.FragmentPostBinding
import com.fixdapp.internal.spacebook.feed.FeedFragmentDirections
import com.fixdapp.internal.spacebook.fromDependencies

class PostFragment: Fragment(), Toolbar.OnMenuItemClickListener {
    private val TAG = "PostFragment"

    private val viewModel: PostViewModel by activityViewModels {
        fromDependencies { PostViewModel(api, tokenManager) }
    }
    private val args: PostFragmentArgs by navArgs()

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!



/*    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).setOnMenuItemClickListener(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostBinding.bind(view)
        initComponents()
    }

    fun initComponents() {
        initRV()
    }

    fun initRV() {
        binding.commentsRv.apply {

        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.logout -> {
                viewModel.logout()
                findNavController().navigate(FeedFragmentDirections.actionFeedFragmentToLoginFragment())
            }
            else -> return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}