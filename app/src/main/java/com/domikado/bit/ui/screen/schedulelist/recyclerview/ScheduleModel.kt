package com.domikado.bit.ui.screen.schedulelist.recyclerview

import com.domikado.bit.abstraction.recyclerview.AbstractBaseItemModel
import com.domikado.bit.abstraction.recyclerview.ViewHolderTypeFactory
import com.domikado.bit.domain.domainmodel.Operator

data class ScheduleModel(
    var id: Int,
    var progress: Int? = null,
    var picStatus: Int,
    var pmStatus: Int,
    var picStatusText: String = "N/A",
    var pmStatusText: String = "N/A",
    var rejection: String?,
    var workDate: String?,
    var sites: List<SiteModel>?,
    var operators: List<Operator>?,
    var isExpanded: Boolean = true,
    var isAllowed: Boolean = true
): AbstractBaseItemModel() {
    override fun type(viewHolderTypeFactory: ViewHolderTypeFactory): Int {
        return viewHolderTypeFactory.type(this)
    }
}