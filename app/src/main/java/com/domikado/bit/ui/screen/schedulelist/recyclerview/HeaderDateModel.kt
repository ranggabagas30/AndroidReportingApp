package com.domikado.bit.ui.screen.schedulelist.recyclerview

import com.domikado.bit.abstraction.recyclerview.AbstractBaseItemModel
import com.domikado.bit.abstraction.recyclerview.ViewHolderTypeFactory

data class HeaderDateModel(val workDate: String): AbstractBaseItemModel() {
    override fun type(viewHolderTypeFactory: ViewHolderTypeFactory): Int {
        return viewHolderTypeFactory.type(this)
    }
}