package com.example.spacedim.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.spacedim.classes.UIElement
import com.example.spacedim.classes.UIType
import com.example.spacedim.interfaces.LifeCycleLogs
import com.example.spacedim.R
import com.example.spacedim.databinding.FragmentGameBinding
import com.example.spacedim.viewModel.GameViewModel

class GameFragment : Fragment(), LifeCycleLogs {
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGameBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        viewModel.uiElements.observe(viewLifecycleOwner, Observer { newUIElements ->
            viewModel.uiElements.value?.let { setBtn(it,binding) }
        })

        binding.fakeLooseBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_gameFragment_to_looseFragment)
        }
        binding.fakeWinBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_gameFragment_to_winFragment)
        }
        return binding.root
    }

    private fun setBtn(elementsList : List<UIElement>, binding: FragmentGameBinding) {

        elementsList.forEach {

            var grid : GridLayout = binding.gridView

            when (it.uiType){
                UIType.BUTTON -> {
                    val viewButton = layoutInflater.inflate(R.layout.button_game, grid, false)
                    val btn : Button = viewButton.findViewById(R.id.buttonAction)
                    btn.setText(it.content)
                    btn.setOnClickListener{ view : View ->
                        Log.i("GameFragmentButton", it.id.toString())
                    }
                    grid.addView(viewButton)
                }
                UIType.SWITCH -> {
                    val viewSwitch = layoutInflater.inflate(R.layout.switch_game_button, grid, false)
                    val switch : Switch = viewSwitch.findViewById(R.id.switchAction)
                    switch.setText(it.content)
                    switch.setOnClickListener{ view : View ->
                        Log.i("GameFragmentSwitch", it.id.toString())
                    }
                    grid.addView(viewSwitch)
                }
                UIType.SHAKE -> {

                }
            }

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