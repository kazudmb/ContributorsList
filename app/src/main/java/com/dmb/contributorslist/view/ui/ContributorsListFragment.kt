package com.dmb.contributorslist.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dmb.contributorslist.R
import com.dmb.contributorslist.databinding.FragmentContributorsListBinding
import com.dmb.contributorslist.service.model.Contributors
import com.dmb.contributorslist.view.adapter.ProjectAdapter
import com.dmb.contributorslist.view.callback.ContributorsClickCallback
import com.dmb.contributorslist.viewModel.ContributorsListViewModel

const val TAG_OF_CONTRIBUTORS_LIST_FRAGMENT = "ContributorsListFragment"

class ContributorsListFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(ContributorsListViewModel::class.java)
    }

    private lateinit var binding: FragmentContributorsListBinding
    private val projectAdapter: ProjectAdapter = ProjectAdapter(object : ContributorsClickCallback {
        override fun onClick(contributors: Contributors) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) && activity is MainActivity) {
                (activity as MainActivity).show(contributors)
            }
        }
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contributors_list, container, false) //dataBinding
        binding.apply {
            contributorsList.adapter = projectAdapter
            isLoading = true
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.contributorsListLiveData.observe(viewLifecycleOwner, Observer { projects ->
            projects?.let {
                binding.isLoading = false
                projectAdapter.setProjectList(it)
            }
        })
    }
}