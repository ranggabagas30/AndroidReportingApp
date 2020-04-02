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
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import io.reactivex.disposables.CompositeDisposable

class ScheduleViewHolder(itemView: View, private val onScheduleClickListener: OnScheduleClickListener?): AbstractViewHolder<ScheduleModel>(itemView) {

    private val childSitesView: ArrayList<SiteView> by lazy { ArrayList<SiteView>() }
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

    private lateinit var model: ScheduleModel

    override fun bind(model: ScheduleModel) {
        d {"bind view on scheduleid: ${model.id}"}
        parentRootView     = itemView.findViewById(R.id.schedule_parent_root)
        parentSiteNameView = itemView.findViewById(R.id.schedule_parent_sitename)
        parentWorkTypeView = itemView.findViewById(R.id.schedule_parent_worktype)
        parentStatusView   = itemView.findViewById(R.id.schedule_parent_status)
        parentRejectionView = itemView.findViewById(R.id.schedule_parent_rejection)
        parentProgressView = itemView.findViewById(R.id.schedule_parent_progress)
        parentDropdownIcon = itemView.findViewById(R.id.schedule_parent_dropdown)

        this.model = model

        setParentView()
        setChildView()
    }

    private fun setParentView() {
        parentSiteNameView.text = model.sites?.joinToString { it.name }
        parentWorkTypeView.text = "OTDR"
        parentStatusView.text =  "PIC status: ${model.picStatusText}\n" +
                                 "PM status: ${model.pmStatusText}"
        parentRejectionView.apply {
            if (TextUtils.isEmpty(model.rejection)) {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
                text = model.rejection
            }
        }

        // NB: progress dihitung dari backend
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
                collapse()
            else
                expand()
        }
    }

    private fun setChildView() {
        model.sites?.also { sites ->
            var previousChildSiteView: SiteView? = null
            var siteIndex = 1
            for (site in sites) {
                d { "child site view id: $siteIndex" }
                d { "id: ${site.id}"}
                d { "name: ${site.name}"}
                d { "status: ${site.status}"}
                var childSiteView: SiteView? = parentRootView.findViewById(siteIndex)
                if (childSiteView == null) {
                    d {"--> add to parent"}
                    childSiteView = createChildSiteView(site, siteIndex)
                    addToParentView(childSiteView, previousChildSiteView)
                    previousChildSiteView = childSiteView
                    childSitesView.add(childSiteView)
                }

                childSiteView.apply {
                    setSiteName(site.name)
                    setSiteStatus(site.status_text)
                    setCheckinIsEnabled(site.isCheckInAllowed)
                }
                siteIndex++
            }
        }
    }


    private fun createChildSiteView(site: SiteModel, siteIndex: Int): SiteView {
        return SiteView(itemView.context).apply {
            setViewId(siteIndex)
            setCheckInOnClickListener(View.OnClickListener {
                onScheduleClickListener?.onSiteCheckInButtonClick(
                    model.id,
                    model.workDate,
                    site.id,
                    site.name,
                    site.code,
                    site.status,
                    site.latitude,
                    site.longitude,
                    site.siteMonitorId,
                    model.operators?.get(0)
                )
            })
        }
    }

    private fun addToParentView(childSiteView: ConstraintLayout, previousChildSiteView: ConstraintLayout?) {
        parentRootView.addView(childSiteView)
        val set = ConstraintSet()
        set.constrainWidth(childSiteView.id, ConstraintSet.MATCH_CONSTRAINT)
        set.constrainHeight(childSiteView.id, ConstraintSet.WRAP_CONTENT)
        set.connect(childSiteView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        set.connect(childSiteView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        if (previousChildSiteView == null) {
            set.connect(childSiteView.id, ConstraintSet.TOP, parentRejectionView.id, ConstraintSet.BOTTOM)
            d {"child site view id: ${childSiteView.id}, constraint top with top of ${parentRejectionView.id}"}
        } else {
            set.connect(childSiteView.id, ConstraintSet.TOP, previousChildSiteView.id, ConstraintSet.BOTTOM)
            d {"child site view id: ${childSiteView.id}, constraint top to bottom of ${previousChildSiteView.id}"}
        }
        set.applyTo(parentRootView)
    }

    private fun collapse() {
        model.isExpanded = false
        for (i in childSitesView.indices) {
            childSitesView[i].hide()
        }
        parentDropdownIcon.setImageResource(iconCollapsed)
    }

    private fun expand() {
        model.isExpanded = true
        for (i in childSitesView.indices) {
            childSitesView[i].show()
        }
        parentDropdownIcon.setImageResource(iconExpanded)
    }
}