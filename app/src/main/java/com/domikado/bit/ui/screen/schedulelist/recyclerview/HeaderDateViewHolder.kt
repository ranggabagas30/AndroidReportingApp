package com.domikado.bit.ui.screen.schedulelist.recyclerview

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.domikado.bit.R
import com.domikado.bit.abstraction.recyclerview.AbstractViewHolder
import com.domikado.bit.utility.DateUtil
import com.github.ajalt.timberkt.d
import org.threeten.bp.format.TextStyle
import java.util.*

class HeaderDateViewHolder(itemView: View): AbstractViewHolder<HeaderDateModel>(itemView) {
    override fun bind(model: HeaderDateModel) {
        val localDate = DateUtil.stringToDate(model.workDate)
        d {"day: ${localDate.dayOfWeek.value}"}
        d {"month: ${localDate.monthValue}"}
        d {"year: ${localDate.year}"}

        val dateView: AppCompatTextView = itemView.findViewById(R.id.schedule_item_date)
        dateView.text = "${localDate.dayOfMonth} ${localDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${localDate.year}"
    }
}