package com.domikado.bit.ui.screen.login.recyclerview

import com.domikado.bit.abstraction.recyclerview.IBaseRvListener

interface OnScheduleClickListener: IBaseRvListener<ScheduleModel> {
    fun onSiteCheckInButtonClick(
        scheduleId: Int,
        workDate: String?,
        siteId: Int,
        siteName: String?,
        siteIdCustomer: String?,
        siteLatitude: Double?,
        siteLongitude: Double?
    )
}