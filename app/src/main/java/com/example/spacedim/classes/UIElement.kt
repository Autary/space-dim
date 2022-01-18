package com.example.spacedim.classes

import com.squareup.moshi.Json

data class UIElement(val id: Int, @Json(name = "type") val uiType: UIType, val content: String)
