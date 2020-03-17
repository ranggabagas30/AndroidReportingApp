package com.domikado.bit.ui.screen.checkin

import com.domikado.bit.abstraction.base.BaseViewModel

class CheckInViewModel: BaseViewModel() {

    var scheduleId: Int = -1
    var workDate: String? = null
    var siteId: Int = -1
    var siteName: String? = null
    var siteIdCustomer: String? = null
    var siteLatitude: Double? = null
    var siteLongitude: Double? = null

    fun buildCheckInLogic(view: CheckInFragment): CheckInLogic =
        CheckInLogic(
            view,
            this
        )
}