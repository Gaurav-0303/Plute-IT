package com.plutecoder.itworld.models

import java.io.Serializable

data class CategoryItem(
    var uid: String?,
    var basicRoadmap: String?,
    var categoryUid : String?,
    var info: String?,
    var logo: String?,
    var name: String?,
    var roadmaps: ArrayList<String>,
    var uses: ArrayList<Uses>?
) : Serializable {

    // Secondary constructor for retrieving only title and description
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
}
