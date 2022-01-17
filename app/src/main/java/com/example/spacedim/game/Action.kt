package com.example.spacedim.game

import com.example.spacedim.`class`.UIElement

data class Action(
    val sentence: String,
    val uiElement: UIElement,
    val time: Long = 8000
)
