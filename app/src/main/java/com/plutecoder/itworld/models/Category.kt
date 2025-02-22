package com.plutecoder.itworld.models

import java.io.Serializable

data class Category(
    var uid: String?,
    var uiType: String?,
    var image : String?,
    var title : String?,
    var subtitle : String?
) : Serializable
