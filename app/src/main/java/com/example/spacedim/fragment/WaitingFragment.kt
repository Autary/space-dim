package com.example.spacedim.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.spacedim.sharedViewModel.HttpViewModel
import com.example.spacedim.R
import com.example.spacedim.classes.Event
import com.example.spacedim.classes.User
import com.example.spacedim.databinding.FragmentWaitingBinding
import com.example.spacedim.sharedViewModel.PolymoObject
import com.example.spacedim.sharedViewModel.WsViewModel

class WaitingFragment : Fragment() {

    private val viewModel: HttpViewModel by activityViewModels()
    private val wsViewModel: WsViewModel by activityViewModels()
    private var isReady = false;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentWaitingBinding.inflate(inflater)

        binding.wsViewModel = wsViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.playButton.setOnClickListener {
            isReady = !isReady
            val ready = PolymoObject.adapterSpace.toJson(Event.Ready(isReady))
            wsViewModel.ws.send(ready)
        }

        wsViewModel.listener.eventGoToPlay.observe(viewLifecycleOwner, { play ->
            if(play){
                view?.findNavController()?.navigate(R.id.action_waitingFragment_to_gameFragment)
            }
        })

        wsViewModel.listener.eventWaitingForPlayers.observe(viewLifecycleOwner, { waiting ->
            setUsers(waiting.userList, binding)
        })

        return binding.root
    }

    private fun setUsers(usersList : List<User>, binding: FragmentWaitingBinding) {
        var list : TableLayout = binding.playersList
        list.removeAllViews()

        usersList.forEach {
            val view = layoutInflater.inflate(R.layout.player_card, list, false)
            val name: TextView = view.findViewById(R.id.playerName)
            val status: TextView = view.findViewById(R.id.playerStatus)

            name.text = it.name

            var statut = it.state.name
            if(it.id == viewModel.user.value?.id){
                statut = "$statut (me)"
            }
            status.text = statut

            list.addView(view)
        }
    }


}