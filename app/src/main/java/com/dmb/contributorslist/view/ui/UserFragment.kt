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
import com.dmb.contributorslist.databinding.FragmentUserBinding
import com.dmb.contributorslist.viewModel.UserViewModel

class UserFragment : Fragment() {

    companion object {
        private const val KEY_LOGIN_NAME = "login_name"

        fun forUser(loginName: String) = UserFragment().apply {
            arguments = Bundle().apply { putString(KEY_LOGIN_NAME, loginName) }
        }
    }

    private val loginName by lazy {
        requireNotNull(
            arguments?.getString(KEY_LOGIN_NAME)
        ) {
            "loginName must not be null"
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(this, UserViewModel.Factory(
            requireActivity().application, loginName
        )).get(UserViewModel::class.java)
    }

    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            userViewModel = viewModel
            isLoading = true
        }

        viewModel.userLiveData.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                binding.isLoading = false
                viewModel.setUser(it)
            }
        })
    }
}