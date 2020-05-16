package com.domikado.bit.presentation.screen.schedulelist.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.domikado.bit.R
import com.google.android.material.button.MaterialButton

class SiteView : ConstraintLayout {

    constructor(context: Context): super(context)

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)

    private var siteRootView: View = LayoutInflater.from(context).inflate(R.layout.item_schedule_child, this, true) as ConstraintLayout
    private var siteNameView: AppCompatTextView
    private var siteStatusView: AppCompatTextView
    private var sitePercentageView: AppCompatTextView
    private var siteCheckInView: MaterialButton
    
    init {
        siteNameView = siteRootView.findViewById(R.id.site_name)
        siteStatusView = siteRootView.findViewById(R.id.site_status)
        sitePercentageView = siteRootView.findViewById(R.id.site_percentage)
        siteCheckInView = siteRootView.findViewById(R.id.site_btn_checkin)
    }

    fun setViewId(id: Int) {
        siteRootView.id = id
    }

    fun setSiteName(siteName: String) {
        siteNameView.text = siteName
    }

    fun getSiteName() = siteNameView.text.toString()

    fun setSitePercentage(percentage: Int) {
        sitePercentageView.text = "$percentage%"
    }

    fun setSiteStatus(siteStatus: String) {
        siteStatusView.text = siteStatus
    }

    fun getSiteStatus() = siteStatusView.text.toString()
    
    fun setCheckInOnClickListener(onClickListener: OnClickListener) {
        siteCheckInView.setOnClickListener(onClickListener)
    }

    fun setCheckinIsEnabled(isAllowed: Boolean) {
        if (isAllowed) enableCheckin() else disableCheckin()
    }

    // behaviour 
    fun show() {
        this.visibility = View.VISIBLE
    }
    
    fun hide() {
        this.visibility = View.GONE
    }
    
    fun disableCheckin() {
        siteCheckInView.isEnabled = false
    }
    
    fun enableCheckin() {
        siteCheckInView.isEnabled = true
    }
}