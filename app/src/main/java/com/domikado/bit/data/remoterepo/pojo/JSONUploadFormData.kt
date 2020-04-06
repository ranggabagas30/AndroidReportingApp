package com.domikado.bit.data.remoterepo.pojo

data class JSONUploadFormData(
    val `data`: List<JSONSite>?,
    val message: String?,
    val status: Int
)