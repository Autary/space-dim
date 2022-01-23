package com.example.spacedim.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.spacedim.classes.UIElement
import com.example.spacedim.classes.UIType
import com.example.spacedim.interfaces.LifeCycleLogs
import com.example.spacedim.R
import com.example.spacedim.databinding.FragmentGameBinding
import com.example.spacedim.viewModel.GameViewModel
import android.os.CountDownTimer

import com.example.spacedim.game.Action
import com.github.nisrulz.sensey.Sensey
import com.github.nisrulz.sensey.ShakeDetector.ShakeListener
import androidx.fragment.app.activityViewModels
import com.example.spacedim.classes.Event
import com.example.spacedim.sharedViewModel.PolymoObject
import com.example.spacedim.sharedViewModel.WinLooseViewModel
import com.example.spacedim.sharedViewModel.WsViewModel


class GameFragment : Fragment(), LifeCycleLogs {
    private lateinit var viewModel: GameViewModel
    private val winLooseViewModel: WinLooseViewModel by activityViewModels()
    private val wsViewModel: WsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGameBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        viewModel.uiElements.observe(viewLifecycleOwner, {
            viewModel.uiElements.value?.let { setBtn(it,binding) }
        })

        viewModel.currentAction.observe(viewLifecycleOwner, {
            viewModel.currentAction.value?.let { setAction(it,binding) }
        })

        wsViewModel.listener.eventGameStarted.observe(viewLifecycleOwner, { gameStarted ->
            setBtn(gameStarted.uiElementList , binding)
        })

        wsViewModel.listener.eventNextLevel.observe(viewLifecycleOwner, { nextLevel ->
            var grid : GridLayout = binding.gridView
            grid.removeAllViews()
            setBtn(nextLevel.uiElementList , binding)
        })

        wsViewModel.listener.eventNextAction.observe(viewLifecycleOwner, { nextAction ->
            setAction(nextAction.action , binding)
            gameTimer(nextAction.action.time.toInt(),binding)
        })

        wsViewModel.listener.eventGameEnded.observe(viewLifecycleOwner, { gameEnded ->
            if(!gameEnded.win){
                winLooseViewModel.endEvent.value = gameEnded
                view?.findNavController()?.navigate(R.id.action_gameFragment_to_looseFragment)
            }
            else{
                winLooseViewModel.endEvent.value = gameEnded
                view?.findNavController()?.navigate(R.id.action_gameFragment_to_winFragment)
            }
        })

        /* debug buttons */
        binding.fakeLooseBtn.setOnClickListener { view : View ->
            winLooseViewModel.endEvent.value = Event.GameOver(1000,false,2)
            view.findNavController().navigate(R.id.action_gameFragment_to_looseFragment)
        }
        binding.fakeWinBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_gameFragment_to_winFragment)
        }
        /* end debug buttons*/

        return binding.root
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun setBtn(elementsList : List<UIElement>, binding: FragmentGameBinding) {
        elementsList.forEach {

            var grid : GridLayout = binding.gridView

            when (it.uiType){
                UIType.BUTTON -> {
                    val viewButton = layoutInflater.inflate(R.layout.button_game, grid, false)
                    val btn : Button = viewButton.findViewById(R.id.buttonAction)
                    btn.text = it.content
                    btn.setOnClickListener{  view : View ->
                        wsViewModel.ws.send(PolymoObject.adapterSpace.toJson(Event.PlayerAction(it)))
                        // Timber.i(PolymoObject.adapterSpace.toJson(Event.PlayerAction(it)))
                    }
                    grid.addView(viewButton)
                }
                UIType.SWITCH -> {
                    val viewSwitch = layoutInflater.inflate(R.layout.switch_game_button, grid, false)
                    val switch : Switch = viewSwitch.findViewById(R.id.switchAction)
                    switch.text = it.content
                    switch.setOnClickListener{ view : View ->
                        wsViewModel.ws.send(PolymoObject.adapterSpace.toJson(Event.PlayerAction(it)))
                        // Timber.i(PolymoObject.adapterSpace.toJson(Event.PlayerAction(it)))
                    }
                    grid.addView(viewSwitch)
                }
            }
        }
    }

    private fun setAction(action : Action, binding: FragmentGameBinding){
        binding.eventTextFaked.text = action.sentence
        if(action.uiElement.uiType === UIType.SHAKE){
            Sensey.getInstance().init(context)
            val shakeListener: ShakeListener = object : ShakeListener {
                override fun onShakeDetected() {
                    wsViewModel.ws.send(PolymoObject.adapterSpace.toJson(Event.PlayerAction(action.uiElement)))
                }
                override fun onShakeStopped() {

                }
            }
            Sensey.getInstance().startShakeDetection(4f,3000,shakeListener)
            // si Action OK
            //Sensey.getInstance().stopShakeDetection(shakeListener)
        }
    }

    /* Timer */
    var timeI = 0
    private lateinit var timerProgressBar : CountDownTimer

    private fun gameTimer(time : Int, binding : FragmentGameBinding){
        // global var to prevent double tick
        timeI = 0

        if(this::timerProgressBar.isInitialized){
            timerProgressBar.cancel()
        }

        binding.progressBar.progress = timeI
        binding.progressBar.max = time

        timerProgressBar = object : CountDownTimer(time.toLong(), 100) {
            override fun onTick(millisUntilFinished: Long) {
                timeI++
                binding.progressBar.progress = timeI * time / (time / 100)
            }

            override fun onFinish() {
                timeI++
                binding.progressBar.progress = time
            }
        }
        timerProgressBar.start()
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
        Sensey.getInstance().stop()
    }
}