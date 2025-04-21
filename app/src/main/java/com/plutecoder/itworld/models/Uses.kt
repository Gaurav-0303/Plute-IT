package com.plutecoder.itworld.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.firebase.database.IgnoreExtraProperties

@Parcelize
@IgnoreExtraProperties
data class Uses(
    var title: String? = null,
    var description: String? = null
) : Parcelable {
    constructor() : this(null, null)
}
