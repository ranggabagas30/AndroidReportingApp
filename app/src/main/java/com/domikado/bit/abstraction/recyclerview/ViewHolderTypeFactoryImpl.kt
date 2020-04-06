package com.domikado.bit.abstraction.recyclerview

import android.view.View
import com.domikado.bit.R
import com.domikado.bit.abstraction.recyclerview.ViewHolderTypeFactoryImpl.ViewHolderItemType.FORM_FILL_ITEM_TYPE
import com.domikado.bit.abstraction.recyclerview.ViewHolderTypeFactoryImpl.ViewHolderItemType.HEADER_DATE_ITEM_TYPE
import com.domikado.bit.abstraction.recyclerview.ViewHolderTypeFactoryImpl.ViewHolderItemType.SCHEDULE_SITE_ITEM_TYPE
import com.domikado.bit.domain.domainmodel.BitThrowable
import com.domikado.bit.presentation.screen.formfill.recyclerview.FormFillModel
import com.domikado.bit.presentation.screen.formfill.recyclerview.FormFillViewHolder
import com.domikado.bit.presentation.screen.formfill.recyclerview.OnFormFillListener
import com.domikado.bit.presentation.screen.schedulelist.recyclerview.*

class ViewHolderTypeFactoryImpl: ViewHolderTypeFactory {

    override fun type(itemModel: SiteModel): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun type(model: ScheduleModel): Int = SCHEDULE_SITE_ITEM_TYPE

    override fun type(dateModel: HeaderDateModel): Int = HEADER_DATE_ITEM_TYPE

    override fun type(formFillModel: FormFillModel): Int = FORM_FILL_ITEM_TYPE

    override fun createViewHolder(itemView: View, viewType: Int, listeners: Map<Int, IBaseRvListener<*>>?): AbstractViewHolder<*> {
        return when(viewType) {
            SCHEDULE_SITE_ITEM_TYPE -> ScheduleViewHolder(itemView, listeners?.get(viewType) as OnScheduleClickListener)
            HEADER_DATE_ITEM_TYPE -> HeaderDateViewHolder(itemView)
            FORM_FILL_ITEM_TYPE -> FormFillViewHolder(itemView, listeners?.get(viewType) as OnFormFillListener)
            else -> throw BitThrowable.BitIllegalStateException(UNKNOWN_ITEM_TYPE)
        }
    }
    
    object ViewHolderItemType{
        const val SCHEDULE_SITE_ITEM_TYPE = R.layout.item_schedule_parent
        const val HEADER_DATE_ITEM_TYPE = R.layout.item_schedule_date
        const val FORM_FILL_ITEM_TYPE = R.layout.item_form_fill
    }

    private val UNKNOWN_ITEM_TYPE = "Item type recyclerview tidak diketahui"
}