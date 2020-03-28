package com.domikado.bit.abstraction.recyclerview

import android.view.View
import com.domikado.bit.R
import com.domikado.bit.abstraction.recyclerview.ViewHolderTypeFactoryImpl.ViewHolderItemType.FORM_FILL_ITEM_TYPE
import com.domikado.bit.abstraction.recyclerview.ViewHolderTypeFactoryImpl.ViewHolderItemType.SCHEDULE_SITE_ITEM_TYPE
import com.domikado.bit.domain.domainmodel.BitThrowable
import com.domikado.bit.ui.screen.formfill.recyclerview.FormFillModel
import com.domikado.bit.ui.screen.formfill.recyclerview.FormFillViewHolder
import com.domikado.bit.ui.screen.formfill.recyclerview.OnFormFillListener
import com.domikado.bit.ui.screen.schedulelist.recyclerview.OnScheduleClickListener
import com.domikado.bit.ui.screen.schedulelist.recyclerview.ScheduleModel
import com.domikado.bit.ui.screen.schedulelist.recyclerview.ScheduleSiteModel
import com.domikado.bit.ui.screen.schedulelist.recyclerview.ScheduleViewHolder

class ViewHolderTypeFactoryImpl: ViewHolderTypeFactory {

    override fun type(itemModel: ScheduleSiteModel): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun type(model: ScheduleModel): Int = SCHEDULE_SITE_ITEM_TYPE

    override fun type(formFillModel: FormFillModel): Int = FORM_FILL_ITEM_TYPE

    override fun createViewHolder(itemView: View, viewType: Int, listeners: Map<Int, IBaseRvListener<*>>?): AbstractViewHolder<*> {
        return when(viewType) {
            SCHEDULE_SITE_ITEM_TYPE -> ScheduleViewHolder(itemView, listeners?.get(viewType) as OnScheduleClickListener)
            FORM_FILL_ITEM_TYPE -> FormFillViewHolder(itemView, listeners?.get(viewType) as OnFormFillListener)
            else -> throw BitThrowable.BitIllegalStateException(UNKNOWN_ITEM_TYPE)
        }
    }
    
    object ViewHolderItemType{
        const val SCHEDULE_SITE_ITEM_TYPE = R.layout.item_schedule_parent
        const val SCHEDULE_DATE_ITEM_TYPE = R.layout.item_schedule_date
        const val FORM_FILL_ITEM_TYPE = R.layout.item_form_fill
    }

    private val UNKNOWN_ITEM_TYPE = "Item type recyclerview tidak diketahui"
}