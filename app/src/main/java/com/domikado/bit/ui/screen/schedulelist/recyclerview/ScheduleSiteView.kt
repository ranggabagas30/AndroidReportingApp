package com.domikado.bit.ui.screen.schedulelist.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.domikado.bit.R
import com.google.android.material.button.MaterialButton

class ScheduleSiteView: ConstraintLayout {

    @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
    ): super(context, attributeSet, defStyleAttr)

    private var siteRootView: ConstraintLayout =
        LayoutInflater.from(context)
            .inflate(R.layout.item_schedule_child, this, true) as ConstraintLayout
    private var siteNameView: AppCompatTextView
    private var siteStatusView: AppCompatTextView
    private var siteCheckInView: MaterialButton
    
    init {
        siteNameView = siteRootView.findViewById(R.id.schedule_child_site_name)
        siteStatusView = siteRootView.findViewById(R.id.schedule_child_site_status)
        siteCheckInView = siteRootView.findViewById(R.id.schedule_child_btn_checkin)
    }

    fun setViewId(id: Int) {
        siteRootView.id = id
    }

    fun setSiteName(siteName: String) {
        siteNameView.text = siteName
    }
    
    fun setSiteStatus(siteStatus: String) {
        siteStatusView.text = siteStatus
    }
    
    fun setCheckInOnClickListener(onClickListener: OnClickListener) {
        siteCheckInView.setOnClickListener(onClickListener)
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