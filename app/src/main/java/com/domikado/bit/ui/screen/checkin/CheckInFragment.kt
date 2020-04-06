package com.domikado.bit.ui.screen.checkin


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.domikado.bit.R
import com.domikado.bit.abstraction.base.BaseFragment
import com.domikado.bit.domain.domainmodel.BitThrowable
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.domain.domainmodel.Operator
import com.domikado.bit.domain.domainmodel.Result
import com.domikado.bit.utility.DebugUtil
import com.domikado.bit.utility.GpsUtils
import com.domikado.bit.utility.PermissionUtil
import com.domikado.bit.utility.makeText
import com.github.ajalt.timberkt.Timber
import com.github.ajalt.timberkt.d
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_checkin.*

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
    private lateinit var btnCheckIn: MaterialButton
    private val gpsUtils: GpsUtils by lazy { GpsUtils(requireActivity()) }
    private val args: CheckInFragmentArgs by navArgs()
    private val checkInViewModel: CheckInViewModel by viewModels()
    private val checkInEvent by lazy { MutableLiveData<CheckInEvent>() }
    private val RC_PERMISSION_LOCATION = 1
    private val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        checkInViewModel.buildCheckInLogic(this)
        checkInViewModel.also {
            it.scheduleId = args.scheduleId
            it.workDate = args.workDate
            it.siteId = args.siteId
            it.siteName = args.siteName
            it.siteCode = args.siteCode
            it.siteStatus = args.siteStatus
            it.siteLatitude = args.siteLatitude?.toDouble()
            it.siteLongitude = args.siteLongitude?.toDouble()
            it.siteMonitorId = args.siteMonitorId
            it.operator = Gson().fromJson(args.operator, Operator::class.java)
        }
        d {"args: $args"}

        //checkInEvent.value = CheckInEvent.OnCreateView

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        siteIdSTPView = checkin_tf_siteidstp as TextInputEditText
        siteNameView = checkin_tf_sitename as TextInputEditText
        pmPeriodView = checkin_tf_pmperiod as TextInputEditText
        siteLatView = checkin_sitecoordinate_tf_latitude as TextInputEditText
        siteLongView = checkin_sitecoordinate_tf_longitude as TextInputEditText
        userLatView = checkin_usercoordinate_tf_latitude as TextInputEditText
        userLongView = checkin_usercoordinate_tf_longitude as TextInputEditText
        distanceToSiteView = checkin_tf_distancetosite as TextInputEditText
        gpsAccuracyView = checkin_tf_gpsaccuracy as TextInputEditText
        btnCheckIn = checkin_btn_checkin as MaterialButton

        btnCheckIn.setOnClickListener {
            if (gpsUtils.isGpsOn()) checkInEvent.value = CheckInEvent.OnCheckInClick
            else gpsUtils.askTurnOnGps()
        }

        checkInEvent.value = CheckInEvent.OnViewCreated
    }

    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            GpsUtils.GPS_REQUEST -> {
                if(resultCode == RESULT_OK) {
                    Timber.d { "on result GPS request" }
                    invokeLocationAction()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            RC_PERMISSION_LOCATION -> {
                Timber.d { "on request location permission result " }
                invokeLocationAction()
            }
        }
    }

    override fun showLoadingCheckIn(loading: Loading) {
        showLoadingMessage(loading.title, loading.message)
    }

    override fun dismissLoading() {
        hideLoading()
    }

    override fun showCheckInData(
        scheduleId: Int,
        workDate: String?,
        siteId: Int,
        siteName: String?,
        siteCode: String?,
        siteLatitude: Double?,
        siteLongitude: Double?,
        siteMonitorId: Int
    ) {
        d {"show checkin data -> scheduleid: $scheduleId, workdate: $workDate, siteid: $siteId, siteName: $siteName, siteCode: $siteCode, siteLat: $siteLatitude, siteLong; $siteLongitude, siteMonitorId: $siteMonitorId"}
        siteIdSTPView.setText(siteId.toString())
        siteNameView.setText(siteName?: "Site name N/A")
        pmPeriodView.setText(workDate?: "Work date N/A")
        siteLatView.setText(siteLatitude?.toString()?: "Koordinat latitude site N/A")
        siteLongView.setText(siteLongitude?.toString()?: "Koordinat longitude site N/A")
    }

    override fun checkInFailed(message: String) {
        requireActivity().makeText(message, Toast.LENGTH_LONG)
    }

    override fun showError(t: Throwable) {
        DebugUtil.handleError(t)
        requireActivity().makeText("$GAGAL_CHECK_IN: ${t.message}", Toast.LENGTH_LONG)
    }

    override fun navigateAfterCheckIn() {
        navigateToFormFill()
    }

    override fun setObserver(observer: Observer<CheckInEvent>) = checkInEvent.observe(viewLifecycleOwner, observer)

    private fun startLocationUpdates() {
        println("start location updates")
        checkInViewModel.locationService.observe(viewLifecycleOwner, Observer {
            try {
                when(it) {
                    is Result.Value -> {
                        val location = it.value
                        if (location != null) {

                            // update location data in real time
                            Timber.d { "lat, long, acc: ${location.latitude}, ${location.longitude}, ${location.accuracy}" }
                            userLatView.setText(location.latitude.toString())
                            userLongView.setText(location.longitude.toString())
                            gpsAccuracyView.setText(location.accuracy.toString())

                            val siteLocation = Location(LocationManager.GPS_PROVIDER).apply {
                                latitude = checkInViewModel.siteLatitude?: 0.0
                                longitude = checkInViewModel.siteLongitude?: 0.0
                            }

                            val distance = siteLocation.distanceTo(location)
                            distanceToSiteView.setText("$distance meter")

                            // enable btn checkin
                            btnCheckIn.isEnabled = true
                        }
                    }
                    is Result.Error -> {
                        throw it.error
                    }
                }
            } catch (t: Throwable) {
                showError(BitThrowable.BitLocationException(t.message?: "Terjadi kesalahan pada fitur lokasi"))
            }
        })
    }

    private fun invokeLocationAction() {
        when {
            !gpsUtils.isGpsOn() -> {
                btnCheckIn.isEnabled = false
                gpsUtils.askTurnOnGps()
            }
            PermissionUtil.hasPermissions(requireActivity(), permissions) -> startLocationUpdates()
            else -> PermissionUtil.requestPermissions(this, permissions, RC_PERMISSION_LOCATION)
        }
    }

    private fun navigateToFormFill() {
        val action = CheckInFragmentDirections.actionCheckInFragmentToFormFillFragment(
            checkInViewModel.scheduleId,
            checkInViewModel.siteMonitorId,
            Gson().toJson(
                checkInViewModel.operator,
                Operator::class.java
            ),
            checkInViewModel.siteLatitude?.toString(),
            checkInViewModel.siteLongitude?.toString()
        )
        findNavController().navigate(action)
    }
}
