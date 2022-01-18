package com.example.spacedim.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.retrofit.overview.HttpViewModel
import com.example.spacedim.interfaces.LifeCycleLogs
import com.example.spacedim.R
import com.example.spacedim.classes.Event
import com.example.spacedim.classes.User
import com.example.spacedim.databinding.FragmentWaitingBinding
import com.example.spacedim.sharedViewModel.PolymoObject
import com.example.spacedim.sharedViewModel.WsViewModel
import com.example.spacedim.viewModel.WaitingViewModel

class WaitingFragment : Fragment(), LifeCycleLogs {

    private lateinit var waitingViewModel: WaitingViewModel
    private val viewModel: HttpViewModel by activityViewModels()
    private val wsViewModel: WsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWaitingBinding.inflate(inflater)

        waitingViewModel = ViewModelProvider(this).get(WaitingViewModel::class.java)

        waitingViewModel.users.observe(viewLifecycleOwner, Observer { newUsers ->
            waitingViewModel.users.value?.let { setUsers(it, binding) }
        })

        binding.playButton.setOnClickListener { view: View ->

            val redy = PolymoObject.adapterSpace.toJson(Event.Ready(true))
            wsViewModel.ws.send(redy)

            //view.findNavController().navigate(R.id.action_waitingFragment_to_gameFragment)
        }

        return binding.root
    }

    private fun setUsers(usersList: List<User>, binding: FragmentWaitingBinding) {

        usersList.forEach {

            var list: TableLayout = binding.playersList

            val view = layoutInflater.inflate(R.layout.player_card, list, false)
            val name: TextView = view.findViewById(R.id.playerName)
            val status: TextView = view.findViewById(R.id.playerStatus)

            name.setText(it.name)
            status.setText(it.state.name)

            list.addView(view)
        }
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