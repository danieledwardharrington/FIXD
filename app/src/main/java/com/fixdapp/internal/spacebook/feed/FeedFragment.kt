package com.fixdapp.internal.spacebook.feed

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.fixdapp.internal.spacebook.R
import com.fixdapp.internal.spacebook.api.TokenManager
import com.fixdapp.internal.spacebook.databinding.FragmentFeedBinding
import com.fixdapp.internal.spacebook.feed.adapter.FeedAdapter
import com.fixdapp.internal.spacebook.fromDependencies
import com.fixdapp.internal.spacebook.login.LoginFragmentDirections


class FeedFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    private val viewModel: FeedViewModel by activityViewModels {
        fromDependencies { FeedViewModel(api, sbDatabase, tokenManager) }
    }

    private val args by navArgs<FeedFragmentArgs>()

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
        initComponents()
    }

    private fun initComponents() {
        initRV()
        handleViewModel()
    }

    private fun handleViewModel() {
        viewModel.getFeed(args.currentUser.id).observe(viewLifecycleOwner) { pagingData ->
            if (pagingData!= null) {
                feedAdapter.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun initRV() {
        binding.feedRv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = feedAdapter
            // TODO: on event clicked
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

    //just for hiding the option to go to the feed, not necessary since we're there already
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.feed).setVisible(false)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
