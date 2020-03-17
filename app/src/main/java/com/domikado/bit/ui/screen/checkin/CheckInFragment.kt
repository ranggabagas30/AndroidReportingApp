package com.domikado.bit.ui.screen.checkin


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.domikado.bit.R
import com.domikado.bit.abstraction.base.BaseFragment
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.utility.DateUtil
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_checkin.*
import org.threeten.bp.format.TextStyle
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CheckInFragment : BaseFragment(), ICheckInContract.View {

    private lateinit var siteIdSTPView: TextInputEditText
    private lateinit var siteNameView: TextInputEditText
    private lateinit var pmPeriodView : TextInputEditText
    private lateinit var siteLatView: TextInputEditText
    private lateinit var siteLongView: TextInputEditText
    private lateinit var userLatView: TextInputEditText
    private lateinit var userLongView: TextInputEditText
    private lateinit var distanceToSiteView: TextInputEditText
    private lateinit var gpsAccuracyView: TextInputEditText

    private val args: CheckInFragmentArgs by navArgs()
    private val checkInViewModel: CheckInViewModel by viewModels()
    private val checkInEvent by lazy {
        MutableLiveData<CheckInEvent>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkInEvent.value = CheckInEvent.OnCreate(
            args.scheduleId,
            args.workDate,
            args.siteId,
            args.siteName,
            args.siteIdCustomer,
            args.siteLatitude?.toDouble(),
            args.siteLongitude?.toDouble()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        siteIdSTPView = checkin_tf_siteidstp as TextInputEditText
        siteNameView = checkin_tf_sitename as TextInputEditText
        pmPeriodView = checkin_tf_pmperiod as TextInputEditText
        siteLatView = checkin_sitecoordinate_tf_latitude as TextInputEditText
        siteLongView = checkin_sitecoordinate_tf_longitude as TextInputEditText
        userLatView = checkin_usercoordinate_tf_latitude as TextInputEditText
        userLongView = checkin_usercoordinate_tf_longitude as TextInputEditText
        distanceToSiteView = checkin_tf_distancetosite as TextInputEditText
        gpsAccuracyView = checkin_tf_gpsaccuracy as TextInputEditText

        checkInViewModel.buildCheckInLogic(this)

        checkInEvent.value = CheckInEvent.OnCreateView

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkInEvent.value = CheckInEvent.OnViewCreated

    }

    override fun showLoadingCheckIn(loading: Loading) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showCheckInData(
        scheduleId: Int,
        workDate: String?,
        siteId: Int,
        siteName: String?,
        siteIdCustomer: String?,
        siteLatitude: Double?,
        siteLongitude: Double?
    ) {
        siteIdSTPView.setText(siteId.toString())
        siteNameView.setText(siteName?: "Site name N/A")
        pmPeriodView.setText(workDate?.let {
            val localeDate = DateUtil.stringToDate(workDate)
            String.format("%s-%s", localeDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault()))
        }?: "Work date N/A")
        siteLatView.setText(siteLatitude?.toString()?: "0.0")
        siteLongView.setText(siteLongitude?.toString()?: "0.0")
    }

    override fun setObserver(observer: Observer<CheckInEvent>) = checkInEvent.observe(viewLifecycleOwner, observer)
}
