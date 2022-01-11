package com.example.spacedim.Class

import com.example.spacedim.Interface.IElement

sealed class UIElement(val type: UIType) : IElement {
    data class Button(override var id: Int, override val content: String) : UIElement(UIType.BUTTON)
    data class Switch(override var id: Int, override val content: String) : UIElement(UIType.SWITCH)
    data class Shake(override var id: Int, override val content: String) : UIElement(UIType.SHAKE)
}