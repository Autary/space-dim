package com.example.spacedim.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.retrofit.overview.HttpViewModel
import com.example.spacedim.interfaces.LifeCycleLogs
import com.example.spacedim.R
import com.example.spacedim.databinding.FragmentCreateRoomBinding
import com.example.spacedim.sharedViewModel.WsViewModel

class CreateRoomFragment : Fragment(), LifeCycleLogs {
    private val viewModel: HttpViewModel by activityViewModels()
    private val wsviewModel: WsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCreateRoomBinding>(inflater,
            R.layout.fragment_create_room, container, false)

        binding.joinRoom.setOnClickListener { view : View ->
            viewModel.user.value?.let {  wsviewModel.createWS("Henri", it.id) }

            Log.i("TESTEEEEE","ws://spacedim.async-agency.com:8081/ws/join/Henri/"+viewModel.user.value?.let { it.id })

            //view.findNavController().navigate(R.id.action_createRoomFragment_to_waitingFragment)
        }
        wsviewModel.eventMessage.observe(viewLifecycleOwner, Observer { msg ->
           Log.i("tydshgfjhdf", msg.toString())
        })
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