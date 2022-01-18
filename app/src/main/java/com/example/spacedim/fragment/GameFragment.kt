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
import android.os.CountDownTimer
import android.os.Handler

import android.widget.ProgressBar
import android.view.Gravity
import com.example.spacedim.game.Action
import com.github.nisrulz.sensey.Sensey
import com.github.nisrulz.sensey.ShakeDetector
import com.github.nisrulz.sensey.ShakeDetector.ShakeListener
import androidx.fragment.app.activityViewModels
import com.example.spacedim.classes.Event
import com.example.spacedim.sharedViewModel.PolymoObject
import com.example.spacedim.sharedViewModel.WsViewModel


class GameFragment : Fragment(), LifeCycleLogs {
    private lateinit var viewModel: GameViewModel
    private val wsViewModel: WsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGameBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)


        viewModel.uiElements.observe(viewLifecycleOwner, Observer { newUIElements ->
            viewModel.uiElements.value?.let { setBtn(it,binding) }
        })

        viewModel.timer.observe(viewLifecycleOwner, Observer { newTimer ->
            viewModel.timer.value?.let { gameTimer(it,binding) }
        })

        viewModel.currentAction.observe(viewLifecycleOwner, Observer { newAction ->
            viewModel.currentAction.value?.let { setAction(it,binding) }
        })

        wsViewModel.listener.eventGameStarted.observe(viewLifecycleOwner, Observer { msg ->
            setBtn(msg.uiElementList , binding)
        })
        wsViewModel.listener.eventNextLevel.observe(viewLifecycleOwner, Observer { msg ->
            setBtn(msg.uiElementList , binding)
        })
        wsViewModel.listener.eventNextAction.observe(viewLifecycleOwner, Observer { msg ->
            setAction(msg.action , binding)
            gameTimer(msg.action.time.toInt(),binding)
        })


        wsViewModel.listener.eventGameOver.observe(viewLifecycleOwner, Observer { over ->
            if(over.win == false){
                view?.findNavController()?.navigate(R.id.action_gameFragment_to_looseFragment)
            }
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
                    btn.setOnClickListener{  view : View ->
                        wsViewModel.ws.send(PolymoObject.adapterSpace.toJson(Event.PlayerAction(it)))
                    }
                    grid.addView(viewButton)
                }
                UIType.SWITCH -> {
                    val viewSwitch = layoutInflater.inflate(R.layout.switch_game_button, grid, false)
                    val switch : Switch = viewSwitch.findViewById(R.id.switchAction)
                    switch.setText(it.content)
                    switch.setOnClickListener{ view : View ->
                        wsViewModel.ws.send(PolymoObject.adapterSpace.toJson(Event.PlayerAction(it)))
                        Log.i("TESTEEEEE",PolymoObject.adapterSpace.toJson(Event.PlayerAction(it)))
                    }
                    grid.addView(viewSwitch)
                }
            }
        }
    }

    private fun setAction(action : Action, binding: FragmentGameBinding){
        binding.eventTextFaked.setText(action.sentence)
        when(action.uiElement.uiType){
            UIType.SHAKE -> {
                Sensey.getInstance().init(context);
                val shakeListener: ShakeListener = object : ShakeListener {

                    override fun onShakeDetected() {
                        // envoi SHAKE au server

                    }

                    override fun onShakeStopped() {

                    }
                }

                Sensey.getInstance().startShakeDetection(4f,3000,shakeListener);

                // si Action OK
                //Sensey.getInstance().stopShakeDetection(shakeListener);
            }
            else -> {
                // si le serveur à reçu l'action dans les temps
            }
        }
    }

    private fun gameTimer(time : Int, binding : FragmentGameBinding){
        val timerProgressBar: CountDownTimer
        var i = 0

        val pb = binding.progressBar
        pb.progress = i
        pb.max = time

        timerProgressBar = object : CountDownTimer(time.toLong(), 100) {
            override fun onTick(millisUntilFinished: Long) {
                i++
                pb.progress = i as Int * time / (time / 100)
            }

            override fun onFinish() {
                i++
                pb.progress = time
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
        Sensey.getInstance().stop();
    }
}