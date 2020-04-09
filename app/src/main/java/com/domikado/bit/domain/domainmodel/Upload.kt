package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.remoterepo.pojo.JSONUploadFormData

data class Upload(
    var site: Site?
)

internal val JSONUploadFormData.toUpload
    get() = Upload(
        this.data?.toSite
    )