package com.domikado.bit.data.viewentitiy

import com.domikado.bit.abstraction.recyclerview.AbstractBaseItemModel
import com.domikado.bit.abstraction.recyclerview.ViewHolderTypeFactory

data class ScheduleItemModel(
    val scheduleId: Int,
    val siteId: Int
): AbstractBaseItemModel() {
    override fun type(viewHolderTypeFactory: ViewHolderTypeFactory): Int {
        return viewHolderTypeFactory.type(this)
    }
}