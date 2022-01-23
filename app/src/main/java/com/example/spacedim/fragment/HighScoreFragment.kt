package com.example.spacedim.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacedim.sharedViewModel.HttpViewModel
import com.example.spacedim.adapter.HighScoreAdapter
import com.example.spacedim.databinding.FragmentHighScoreBinding

class HighScoreFragment : Fragment() {

    private val viewModel: HttpViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHighScoreBinding.inflate(inflater)

        viewModel.getAllUsers()

        viewModel.userList.observe(viewLifecycleOwner, { users ->
            binding.listPlayers.layoutManager = LinearLayoutManager(view?.context)
            binding.listPlayers.adapter = HighScoreAdapter(users)

            // Timber.i(users.toString() )
        })

        return binding.root
    }


}