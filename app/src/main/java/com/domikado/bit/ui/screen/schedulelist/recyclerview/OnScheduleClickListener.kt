package com.domikado.bit.ui.screen.schedulelist.recyclerview

import com.domikado.bit.abstraction.recyclerview.IBaseRvListener
import com.domikado.bit.domain.domainmodel.Operator

interface OnScheduleClickListener: IBaseRvListener<ScheduleModel> {
    fun onSiteCheckInButtonClick(
        scheduleId: Int,
        workDate: String?,
        siteId: Int,
        siteName: String?,
        siteCode: String?,
        siteStatus: Int,
        siteLatitude: Double?,
        siteLongitude: Double?,
        siteMonitorId: Int,
        operator: Operator?
    )
}