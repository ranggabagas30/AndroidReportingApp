package com.domikado.bit.presentation.screen.formfill.recyclerview

import com.domikado.bit.abstraction.recyclerview.AbstractBaseItemModel
import com.domikado.bit.abstraction.recyclerview.ViewHolderTypeFactory

data class FormFillModel(
    val id: Int,
    //val formFillDataId: Int,
    val siteMonitorId: Int,
    var modifiedAt: Long? = null,
    var lastUploadAt: String? = null,
    val header: HeaderModel,
    val body: BodyModel
): AbstractBaseItemModel() {
    override fun type(viewHolderTypeFactory: ViewHolderTypeFactory): Int {
        return viewHolderTypeFactory.type(this)
    }
}