package com.domikado.bit.data.remote.pojo

data class JSONUploadFormData(
    val `data`: List<JSONSite>?,
    val message: String?,
    val status: Int
)