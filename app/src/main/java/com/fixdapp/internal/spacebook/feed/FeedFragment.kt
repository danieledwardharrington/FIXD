package com.fixdapp.internal.spacebook.feed

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.fixdapp.internal.spacebook.Communicator
import com.fixdapp.internal.spacebook.R
import com.fixdapp.internal.spacebook.api.models.feed.ParentFeed
import com.fixdapp.internal.spacebook.databinding.FragmentFeedBinding
import com.fixdapp.internal.spacebook.feed.adapter.FeedAdapter
import com.fixdapp.internal.spacebook.fromDependencies


class FeedFragment : Fragment(), Toolbar.OnMenuItemClickListener, FeedAdapter.OnEventItemClickedListener {
    private val TAG = "FeedFragment"

    private val viewModel: FeedViewModel by activityViewModels {
        fromDependencies { FeedViewModel(api, sbDatabase, tokenManager) }
    }
    private val communicator: Communicator by activityViewModels()
    private val args: FeedFragmentArgs by navArgs()

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val feedAdapter = FeedAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        view.findViewById<Toolbar>(R.id.toolbar).setOnMenuItemClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFeedBinding.bind(view)
        initComponents()
    }

    private fun initComponents() {
        initRV()
        handleViewModel()
    }

    private fun initRV() {
        binding.feedRv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            feedAdapter.setEventItemClickedListener(this@FeedFragment)
            adapter = feedAdapter
        }
    }

    private fun handleViewModel() {
        if (args.key == 1) {
            Log.d(TAG, "From splashFragment")
            viewModel.userFromRoom.observe(viewLifecycleOwner) { userEntity ->
                Log.d(TAG, "userFromRoom observe: ${userEntity.sbId}")
                viewModel.getUserFeed(userEntity.sbId)
            }
            viewModel.getUserFromDB()
        }
        communicator.userIdLD.observe(viewLifecycleOwner) { userId ->
            viewModel.getUserFeed(userId)
            viewModel.insertUser(userId)
        }
        viewModel.feedPD.observe(viewLifecycleOwner) { pagingData ->
            Log.d(TAG, "feedPD observe")
            feedAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            binding.loadingPb.visibility = View.GONE
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

    override fun onEventClicked(parentFeed: ParentFeed) {
        Log.d(TAG, "onEventClicked")
        when (parentFeed) {
            is ParentFeed.GithubNewRepoFeed -> navigateToRepo(parentFeed.githubNewRepo.url)
            is ParentFeed.GithubNewPRFeed -> navigateToRepo(parentFeed.githubNewPR.url)
            is ParentFeed.GithubMergePRFeed -> navigateToRepo(parentFeed.githubNewPR.url)
            is ParentFeed.GithubPushFeed -> navigateToRepo(parentFeed.githubPushModel.url)
            is ParentFeed.PostFeed -> {
                val action = FeedFragmentDirections.actionFeedFragmentToPostFragment(parentFeed.postModel.id)
                findNavController().navigate(action)
            }
            is ParentFeed.CommentFeed -> {
                val action = FeedFragmentDirections.actionFeedFragmentToPostFragment(parentFeed.commentModel.postId)
                findNavController().navigate(action)
            }
            else -> Log.d(TAG, "when/else event click")
        }
    }

    private fun navigateToRepo(url: String) {
        Log.d(TAG, "navigateToRepo")
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (ex: Exception) {
            Log.d(TAG, ex.message.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
