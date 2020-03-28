package com.domikado.bit.ui.screen.schedulelist.recyclerview

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.domikado.bit.R
import com.domikado.bit.abstraction.recyclerview.AbstractViewHolder
import com.github.ajalt.timberkt.Timber

class ScheduleViewHolder(itemView: View, private val onScheduleClickListener: OnScheduleClickListener?): AbstractViewHolder<ScheduleModel>(itemView) {

    private var childSitesView: List<ScheduleSiteView>? = null
    private lateinit var parentRootView: ConstraintLayout
    private lateinit var parentSiteNameView: AppCompatTextView
    private lateinit var parentWorkTypeView: AppCompatTextView
    private lateinit var parentStatusView: AppCompatTextView
    private lateinit var parentProgressView: AppCompatTextView
    private lateinit var parentDropdownIcon: AppCompatImageView
    
    private val iconExpanded = R.drawable.ic_arrow_drop_down_24dp
    private val iconCollapsed = R.drawable.ic_arrow_drop_up_24dp

    override fun bind(model: ScheduleModel) {
        parentRootView     = itemView.findViewById(R.id.schedule_parent_root)
        parentSiteNameView = itemView.findViewById(R.id.schedule_parent_sitename)
        parentWorkTypeView = itemView.findViewById(R.id.schedule_parent_worktype)
        parentStatusView   = itemView.findViewById(R.id.schedule_parent_status)
        parentProgressView = itemView.findViewById(R.id.schedule_parent_progress)
        parentDropdownIcon = itemView.findViewById(R.id.schedule_parent_dropdown)

        parentSiteNameView.text = model.sites?.joinToString { it.siteName.toString() }
        parentWorkTypeView.text = model.workTypeName?: "Work type N/A"
        parentStatusView.text = model.status?: "Schedule status N/A"
        parentProgressView.text = model.progress?: "Progress N/A"

        parentRootView.setOnClickListener {
            if (model.isExpanded)
                collapse(model)
            else
                expand(model)
        }
        
        addChildSiteViewToParentView(model)
    }

    private fun addChildSiteViewToParentView(model: ScheduleModel) {
        Timber.d { "parent root id: ${parentRootView.id}" }
        Timber.d { "parent sitename id: ${parentSiteNameView.id}" }
        Timber.d { "parent worktype id: ${parentWorkTypeView.id}" }
        Timber.d { "parent status id: ${parentStatusView.id}" }
        Timber.d { "parent progress id: ${parentProgressView.id}" }

        childSitesView = ArrayList()
        childSitesView = model.sites?.map {
            val childSiteView = ScheduleSiteView(itemView.context)

            childSiteView.apply {
                setViewId(it.siteId)
                setSiteName(it.siteName?: "Site name N/A")
                setSiteStatus(it.siteStatus?.let {
                    SITE_STATUS[it]
                }?:  "Site status N/A")
                setCheckInOnClickListener(View.OnClickListener { _ ->
                    // interface click listener
                    // navigate to checkin
                    onScheduleClickListener?.onSiteCheckInButtonClick(
                        model.scheduleId,
                        model.workDate,
                        it.siteId,
                        it.siteName,
                        it.siteCode,
                        it.siteLatitude,
                        it.siteLongitude,
                        it.siteMonitorId
                    )
                })
                if (it.isCheckInAllowed) enableCheckin() else disableCheckin()
                Timber.d { "child Site id: ${childSiteView.id}" }
            }
        }?.also {
            for(i in it.indices) {

                val childSiteView = it[i]
                parentRootView.addView(childSiteView)

                val set = ConstraintSet()
                set.constrainWidth(childSiteView.id, ConstraintSet.MATCH_CONSTRAINT)
                set.constrainHeight(childSiteView.id, ConstraintSet.WRAP_CONTENT)
                set.connect(childSiteView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                set.connect(childSiteView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                if (i == 0) {
                    set.connect(childSiteView.id, ConstraintSet.TOP, parentStatusView.id, ConstraintSet.BOTTOM)
                } else {
                    set.connect(childSiteView.id, ConstraintSet.TOP, it[i-1].id, ConstraintSet.BOTTOM)
                }
                set.applyTo(parentRootView)
            }
        }
    }
    
    private fun collapse(model: ScheduleModel) {
        childSitesView?.also {
            model.isExpanded = false
            for (i in it.indices) {
                it[i].hide()
            }
            parentDropdownIcon.setImageResource(iconCollapsed)
        }
    }

    private fun expand(model: ScheduleModel) {
        childSitesView?.also {
            model.isExpanded = true
            for (i in it.indices) {
                it[i].show()
            }
            parentDropdownIcon.setImageResource(iconExpanded)
        }
    }
}