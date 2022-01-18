package com.example.spacedim.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.retrofit.overview.HttpViewModel
import com.example.spacedim.interfaces.LifeCycleLogs
import com.example.spacedim.R
import com.example.spacedim.databinding.FragmentLoginBinding


class LoginFragment : Fragment(), LifeCycleLogs {

    private val viewModel: HttpViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val binding = FragmentLoginBinding.inflate(inflater)

        viewModel.eventGoToCreateRoom.observe(viewLifecycleOwner, Observer { goToCreateRoom ->
            if (goToCreateRoom)
                view?.findNavController()?.navigate(R.id.action_loginFragment_to_createRoomFragment)
        })

        binding.launchButton.setOnClickListener {
            if (!binding.nameInput.text.isNullOrBlank()) {
                viewModel.addUser(binding.nameInput.text.toString())
            }
        }


        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super<Fragment>.onCreate(savedInstanceState)
        super<LifeCycleLogs>.onCreate()
    }

    override fun onStart() {
        super<Fragment>.onStart()
        super<LifeCycleLogs>.onStart()
    }

    override fun onResume() {
        super<Fragment>.onResume()
        super<LifeCycleLogs>.onResume()
    }

    override fun onPause() {
        super<Fragment>.onPause()
        super<LifeCycleLogs>.onPause()
    }

    override fun onStop() {
        super<Fragment>.onStop()
        super<LifeCycleLogs>.onStop()
    }

    override fun onDestroy() {
        super<Fragment>.onDestroy()
        super<LifeCycleLogs>.onDestroy()
    }
}