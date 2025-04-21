package com.plutecoder.itworld.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.firebase.database.IgnoreExtraProperties

@Parcelize
@IgnoreExtraProperties
data class CategoryItem(
    var uid: String? = null,
    var basicRoadmap: String? = null,
    var categoryUid: String? = null,
    var info: String? = null,
    var logo: String? = null,
    var name: String? = null,
    var roadmaps: ArrayList<String> = arrayListOf(),
    var uses: ArrayList<Uses>? = null
) : Parcelable {
    constructor(title: String?, description: String?) : this(
        uid = null,
        basicRoadmap = null,
        categoryUid = null,
        info = description,
        logo = null,
        name = title,
        roadmaps = arrayListOf(),
        uses = null
    )

    constructor() : this(null, null, null, null, null, null, arrayListOf(), null)
}
