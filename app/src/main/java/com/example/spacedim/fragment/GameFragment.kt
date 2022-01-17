package com.example.spacedim.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.spacedim.`class`.UIElement
import com.example.spacedim.`class`.UIType
import com.example.spacedim.`interface`.LifeCycleLogs
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
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(inflater,
            R.layout.fragment_game, container, false)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // essayer de trouver un moyen d'accéder à la vue fragment_button_game.xml pour recup le bouton
        //myButton = findViewById(R.id.buttonAction)
        // pour ensuite appeler setArgumentsButton() et set les boutons

        binding.fakeLooseBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_gameFragment_to_looseFragment)
        }
        binding.fakeWinBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_gameFragment_to_winFragment)
        }
        return binding.root
    }

    private fun setBtn() {
        elementsList = viewModel.getElements()

        elementsList.forEach {


            if(it.type == UIType.BUTTON)
            {

                //simpleGameButton()
            }

            if(it.type == UIType.SHAKE){
                //shakeTask()
            }

            if(it.type == UIType.SWITCH){
                //switchGameButton()
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