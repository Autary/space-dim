package com.example.spacedim.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.overview.HttpViewModel
import com.example.spacedim.R
import com.example.spacedim.adapter.HighScoreAdapter
import com.example.spacedim.classes.Event
import com.example.spacedim.databinding.FragmentHighScoreBinding
import com.example.spacedim.sharedViewModel.PolymoObject

class HighScoreFragment : Fragment() {

    private val viewModel: HttpViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHighScoreBinding.inflate(inflater)
        viewModel.getAllUsers()
        viewModel.userList.observe(viewLifecycleOwner, Observer { users ->
            val highScoreAdapter = HighScoreAdapter(users)
            binding.listPlayers.layoutManager = LinearLayoutManager(view?.context)
            binding.listPlayers.adapter = highScoreAdapter

            //list_players
            Log.i("TESTEEEEE", users.toString() )
        })



        return binding.root
    }


}