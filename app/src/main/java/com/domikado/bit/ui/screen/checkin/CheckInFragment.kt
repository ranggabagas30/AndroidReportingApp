package com.domikado.bit.ui.screen.checkin


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.domikado.bit.R
import com.domikado.bit.abstraction.base.BaseFragment
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.domain.domainmodel.Result
import com.domikado.bit.utility.DateUtil
import com.domikado.bit.utility.GpsUtils
import com.domikado.bit.utility.makeText
import com.github.ajalt.timberkt.Timber
import com.google.android.material.button.MaterialButton
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
    private lateinit var btnCheckIn: MaterialButton

    private val gpsUtils: GpsUtils by lazy {
        GpsUtils(requireActivity())
    }

    private val args: CheckInFragmentArgs by navArgs()
    private val checkInViewModel: CheckInViewModel by viewModels()
    private val checkInEvent by lazy {
        MutableLiveData<CheckInEvent>()
    }

    private val RC_PERMISSION_LOCATION = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        checkInViewModel.buildCheckInLogic(this)

        checkInEvent.value = CheckInEvent.OnCreate(
            args.scheduleId,
            args.workDate,
            args.siteId,
            args.siteName,
            args.siteCode,
            args.siteLatitude?.toDouble(),
            args.siteLongitude?.toDouble(),
            args.siteMonitorId
        )

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
            if (gpsUtils.isGpsOn()) navigateToFormFill()
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
        siteCode: String?,
        siteLatitude: Double?,
        siteLongitude: Double?,
        siteMonitorId: Int
    ) {
        siteIdSTPView.setText(siteId.toString())
        siteNameView.setText(siteName?: "Site name N/A")
        pmPeriodView.setText(workDate?.let {
            val localeDate = DateUtil.stringToDate(workDate)
            String.format("%s - %s", localeDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault()))
        }?: "Work date N/A")
        siteLatView.setText(siteLatitude?.toString()?: "0.0")
        siteLongView.setText(siteLongitude?.toString()?: "0.0")
    }

    override fun setObserver(observer: Observer<CheckInEvent>) = checkInEvent.observe(viewLifecycleOwner, observer)

    private fun isPermissionGranted() =
        ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) &&
                ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            RC_PERMISSION_LOCATION
        )
    }

    private fun startLocationUpdates() {
        checkInViewModel.locationService.observe(viewLifecycleOwner, Observer {
            try {
                when(it) {
                    is Result.Value -> {
                        val location = it.value
                        if (location != null) {
                            Timber.d { "lat, long, acc: ${location.latitude}, ${location.longitude}, ${location.accuracy}" }
                            userLatView.setText(location.latitude.toString())
                            userLongView.setText(location.longitude.toString())
                            gpsAccuracyView.setText(location.accuracy.toString())
                            btnCheckIn.isEnabled = true
                        }
                    }
                    is Result.Error -> {
                        throw it.error
                    }
                }
            } catch (t: Throwable) {
                Timber.e(t) { "error location"}
                requireActivity().makeText("Error location: ${t.message}", Toast.LENGTH_LONG)
            }
        })
    }

    private fun invokeLocationAction() {
        when {
            !gpsUtils.isGpsOn() -> {
                btnCheckIn.isEnabled = false
                gpsUtils.askTurnOnGps()
            }
            isPermissionGranted() -> startLocationUpdates()
            shouldShowRequestPermissionRationale() -> requireActivity().makeText("Mohon izinkan permission location untuk melakukan check in", Toast.LENGTH_LONG)
            else -> requestPermission()
        }
    }

    private fun navigateToFormFill() {
        findNavController().navigate(R.id.action_checkInFragment_to_formFillFragment)
    }
}
