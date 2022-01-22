package com.example.spacedim.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.spacedim.interfaces.LifeCycleLogs
import com.example.spacedim.R
import com.example.spacedim.databinding.FragmentWinBinding
import com.example.spacedim.viewModel.WinLoseViewModel

class WinFragment : Fragment(), LifeCycleLogs {
    private val viewModelLoseViewModel: WinLoseViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentWinBinding.inflate(inflater)
        binding.retryBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_winFragment_to_createRoomFragment)
        }
        viewModelLoseViewModel.getItem()?.let { binding.score.setText("Score: "+it.score.toString()) }
        binding.scoreBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_winFragment_to_highScoreFragment)
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