package com.example.spacedim.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
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
    private lateinit var binding: FragmentGameBinding
    private lateinit var elementsList: MutableList<UIElement>
    private lateinit var myButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGameBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)



        setBtn(binding)




        binding.fakeLooseBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_gameFragment_to_looseFragment)
        }
        binding.fakeWinBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_gameFragment_to_winFragment)
        }
        return binding.root
    }

    private fun setBtn(binding: FragmentGameBinding) {
        elementsList = viewModel.getElements()

        elementsList.forEach {

            var grid : GridLayout = binding.gridView

            when (it.type){
                UIType.BUTTON -> {
                    val view2 = layoutInflater.inflate(R.layout.button_game, grid, false)
                    val btn2 : Button = view2.findViewById(R.id.buttonAction)
                    btn2.setText(it.content)
                    btn2.setOnClickListener{
                        Log.i("GameFragmentButton", it.id.toString())
                    }
                    grid.addView(view2)
                }
                UIType.SWITCH -> {
                    val viewSwitch = layoutInflater.inflate(R.layout.switch_game_button, grid, false)
                    val switch : Switch = viewSwitch.findViewById(R.id.switchAction)
                    switch.id = it.id
                    switch.setText(it.content)
                    switch.setOnClickListener{
                        Log.i("GameFragmentSwitch", it.id.toString())
                    }
                    grid.addView(viewSwitch)
                }
                UIType.SHAKE -> {

                }
            }

        }
    }

    private fun setArgumentsButton(id: Int, textButton : String, btn: Button)
    {

        //val btn : Button = findViewById(R.id.buttonAction)
        //btn.setText(textButton)
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