package com.domikado.bit.abstraction.recyclerview

import android.view.View
import com.domikado.bit.ui.screen.formfill.recyclerview.FormFillModel
import com.domikado.bit.ui.screen.schedulelist.recyclerview.HeaderDateModel
import com.domikado.bit.ui.screen.schedulelist.recyclerview.ScheduleModel
import com.domikado.bit.ui.screen.schedulelist.recyclerview.SiteModel

interface ViewHolderTypeFactory {
    fun type(model: ScheduleModel): Int
    fun type(dateModel: HeaderDateModel): Int
    fun type(itemModel: SiteModel): Int
    fun type(formFillModel: FormFillModel): Int
    fun createViewHolder(
        itemView: View,
        viewType: Int,
        listeners: Map<Int, IBaseRvListener<*>>?
    ): AbstractViewHolder<*>
}