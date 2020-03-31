package com.domikado.bit.ui.screen.schedulelist.recyclerview

import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.domikado.bit.R
import com.domikado.bit.abstraction.database.BitDatabase
import com.domikado.bit.abstraction.network.BitAPI
import com.domikado.bit.abstraction.recyclerview.AbstractViewHolder
import com.domikado.bit.abstraction.rx.AppSchedulerProvider
import com.domikado.bit.data.local.schedule.RoomLocalScheduleImpl
import com.domikado.bit.data.remote.schedule.RemoteScheduleImpl
import com.domikado.bit.domain.interactor.ScheduleSource
import com.domikado.bit.domain.servicelocator.ScheduleServiceLocator
import com.github.ajalt.timberkt.Timber
import com.github.ajalt.timberkt.e
import io.reactivex.disposables.CompositeDisposable

class ScheduleViewHolder(itemView: View, private val onScheduleClickListener: OnScheduleClickListener?): AbstractViewHolder<ScheduleModel>(itemView) {

    private var childSitesView: List<SiteView>? = null
    private lateinit var parentRootView: ConstraintLayout
    private lateinit var parentSiteNameView: AppCompatTextView
    private lateinit var parentWorkTypeView: AppCompatTextView
    private lateinit var parentStatusView: AppCompatTextView
    private lateinit var parentRejectionView: AppCompatTextView
    private lateinit var parentProgressView: AppCompatTextView
    private lateinit var parentDropdownIcon: AppCompatImageView
    
    private val iconExpanded = R.drawable.ic_arrow_drop_down_24dp
    private val iconCollapsed = R.drawable.ic_arrow_drop_up_24dp

    private val compositeDisposable by lazy { CompositeDisposable() }
    private val scheduleProvider by lazy { AppSchedulerProvider() }
    private val scheduleDao by lazy { BitDatabase.getInstance(itemView.context).scheduleDao() }
    private val localScheduleRepository by lazy { RoomLocalScheduleImpl(scheduleDao) }
    private val remoteScheduleRepository by lazy { RemoteScheduleImpl(BitAPI) }
    private val scheduleServiceLocator by lazy { ScheduleServiceLocator(localScheduleRepository, remoteScheduleRepository) }
    private val scheduleSource by lazy { ScheduleSource() }

    override fun bind(model: ScheduleModel) {
        parentRootView     = itemView.findViewById(R.id.schedule_parent_root)
        parentSiteNameView = itemView.findViewById(R.id.schedule_parent_sitename)
        parentWorkTypeView = itemView.findViewById(R.id.schedule_parent_worktype)
        parentStatusView   = itemView.findViewById(R.id.schedule_parent_status)
        parentRejectionView = itemView.findViewById(R.id.schedule_parent_rejection)
        parentProgressView = itemView.findViewById(R.id.schedule_parent_progress)
        parentDropdownIcon = itemView.findViewById(R.id.schedule_parent_dropdown)

        parentSiteNameView.text = model.sites?.joinToString { it.name }
        parentWorkTypeView.text = "OTDR"
        parentStatusView.text = "PIC status: ${model.picStatusText}\n" +
                                "PM status: ${model.pmStatusText}"
        parentRejectionView.apply {
            if (TextUtils.isEmpty(model.rejection)) {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
                text = model.rejection
            }
        }

        if (model.progress != null) parentProgressView.text = "${model.progress}%"
        else {
            compositeDisposable.add(
                scheduleSource.getProgress(model.id, scheduleServiceLocator)
                    .subscribeOn(scheduleProvider.io())
                    .observeOn(scheduleProvider.ui())
                    .subscribe({ count ->
                        val taskCount = 4 // 2 sites, each has 2 form fill
                        val progress = count.toFloat() / taskCount.toFloat()
                        model.progress = progress.toInt()
                        parentProgressView.text = "${model.progress}%"
                    }, {
                        e(it)
                        parentProgressView.text = "error"
                    })
            )
        }

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

        println("schedule sites: ${model.sites}")

        childSitesView = ArrayList()
        childSitesView = model.sites?.map {
            val childSiteView = SiteView(itemView.context)

            childSiteView.apply {
                setViewId(it.id)
                setSiteName(it.name)
                setSiteStatus(it.status)
                setCheckInOnClickListener(View.OnClickListener { _ ->
                    // interface click listener
                    // navigate to checkin
                    onScheduleClickListener?.onSiteCheckInButtonClick(
                        model.id,
                        model.workDate,
                        it.id,
                        it.name,
                        it.code,
                        it.latitude,
                        it.longitude,
                        it.siteMonitorId,
                        model.operators?.get(0)
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