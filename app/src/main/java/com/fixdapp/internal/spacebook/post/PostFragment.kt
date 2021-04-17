package com.fixdapp.internal.spacebook.post

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fixdapp.internal.spacebook.R
import com.fixdapp.internal.spacebook.api.models.individual.PostModel
import com.fixdapp.internal.spacebook.databinding.FragmentPostBinding
import com.fixdapp.internal.spacebook.fromDependencies
import com.fixdapp.internal.spacebook.post.comments.CommentsAdapter
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*

class PostFragment: Fragment(), Toolbar.OnMenuItemClickListener, CommentsAdapter.OnItemLoadedListener {
    private val TAG = "PostFragment"

    private val viewModel: PostViewModel by activityViewModels {
        fromDependencies { PostViewModel(api, sbDatabase, tokenManager) }
    }
    private val args: PostFragmentArgs by navArgs()

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    private val commentsAdapter = CommentsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        view.findViewById<Toolbar>(R.id.toolbar).setOnMenuItemClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostBinding.bind(view)
        initComponents()
    }

    private fun initComponents() {
        Log.d(TAG, "initComponents")
        binding.loadingPb.visibility = View.VISIBLE
        setViews(args.postModel)
        initRV()
        handleViewModel()
    }

    private fun initRV() {
        Log.d(TAG, "initRV")
        binding.commentsRv.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            setHasFixedSize(true)
            commentsAdapter.setOnItemLoadedListener(this@PostFragment)
            adapter = commentsAdapter
        }
    }

    private fun handleViewModel() {
        Log.d(TAG, "handleViewModel")
        try {
            viewModel.getComments(args.postModel.id)
            viewModel.commentsLD.observe(viewLifecycleOwner) { pagingData ->
                commentsAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        } catch (ex: Exception) {
            binding.loadingPb.visibility = View.GONE
            Log.e(TAG, ex.message.toString())
        }
    }

    private fun setViews(postModel: PostModel) {
        Log.d(TAG, "setViews")
        binding.postTitleTv.text = postModel.title
        binding.postContentTv.text = postModel.body
        binding.authorNameTv.text = postModel.author.name
        binding.authorRatingBar.rating = postModel.author.rating!!.toFloat()
        val date = postModel.postedAt.dropLast(1)
        val localDate = LocalDateTime.parse(date)
        val month = localDate.month.getDisplayName(TextStyle.SHORT, Locale.US)
        val day = localDate.dayOfMonth
        val year = localDate.year
        val dateStr = "$day $month $year"
        binding.dateTv.text = dateStr
        binding.commentsTitleTv.text = "${postModel.numComments} Comments"
        binding.toolbar.title = postModel.title
    }

    override fun itemLoaded() {
        binding.loadingPb.visibility = View.GONE
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.logout -> {
                viewModel.logout()
                findNavController().navigate(PostFragmentDirections.actionPostFragmentToLoginFragment())
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