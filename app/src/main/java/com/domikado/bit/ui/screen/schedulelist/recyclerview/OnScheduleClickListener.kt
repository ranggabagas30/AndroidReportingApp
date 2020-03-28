package com.domikado.bit.ui.screen.schedulelist.recyclerview

import com.domikado.bit.abstraction.recyclerview.IBaseRvListener

interface OnScheduleClickListener: IBaseRvListener<ScheduleModel> {
    fun onSiteCheckInButtonClick(
        scheduleId: Int,
        workDate: String?,
        siteId: Int,
        siteName: String?,
        siteCode: String?,
        siteLatitude: Double?,
        siteLongitude: Double?,
        siteMonitorId: Int
    )
}