package com.example.spacedim.game

import com.example.spacedim.classes.UIElement

data class Action(
    val sentence: String,
    val uiElement: UIElement,
    val time: Long = 8000
)
