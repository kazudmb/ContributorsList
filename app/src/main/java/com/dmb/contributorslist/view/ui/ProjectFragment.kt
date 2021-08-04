package com.dmb.contributorslist.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dmb.contributorslist.R
import com.dmb.contributorslist.databinding.FragmentProjectDetailsBinding
import com.dmb.contributorslist.viewModel.ProjectViewModel

class ProjectFragment : Fragment() {

    companion object {
        private const val KEY_PROJECT_ID = "project_id"

        fun forProject(projectId: String) = ProjectFragment().apply {
            arguments = Bundle().apply { putString(KEY_PROJECT_ID, projectId) }
        }
    }

    private val projectId by lazy {
        requireNotNull(
            arguments?.getString(KEY_PROJECT_ID)
        ) {
            "projectId must not be null"
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(this, ProjectViewModel.Factory(
            requireActivity().application, projectId
        )).get(ProjectViewModel::class.java)
    }

    private lateinit var binding: FragmentProjectDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            projectViewModel = viewModel
            isLoading = true
        }

        viewModel.projectLiveData.observe(viewLifecycleOwner, Observer { project ->
            project?.let {
                binding.isLoading = false
                viewModel.setProject(it)
            }
        })
    }
}