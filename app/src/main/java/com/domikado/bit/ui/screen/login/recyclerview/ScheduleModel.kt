package com.domikado.bit.ui.screen.login.recyclerview

import com.domikado.bit.abstraction.recyclerview.AbstractBaseItemModel
import com.domikado.bit.abstraction.recyclerview.ViewHolderTypeFactory

data class ScheduleModel(
    val scheduleId: Int,
    val progress: String?,
    val status: String?,
    val workTypeName: String?,
    val workDate: String?,
    val sites: List<ScheduleSiteModel>?,
    var isExpanded: Boolean = true,
    var isAllowed: Boolean = true
): AbstractBaseItemModel() {
    override fun type(viewHolderTypeFactory: ViewHolderTypeFactory): Int {
        return viewHolderTypeFactory.type(this)
    }
}