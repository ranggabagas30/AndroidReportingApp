package com.domikado.bit.ui.screen.schedulelist


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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.domikado.bit.R
import com.domikado.bit.abstraction.base.BaseFragment
import com.domikado.bit.abstraction.recyclerview.AbstractBaseItemModel
import com.domikado.bit.abstraction.recyclerview.RecyclerAdapter
import com.domikado.bit.abstraction.recyclerview.ViewHolderTypeFactoryImpl
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.domain.domainmodel.Operator
import com.domikado.bit.ui.screen.schedulelist.recyclerview.OnScheduleClickListener
import com.domikado.bit.ui.screen.schedulelist.recyclerview.ScheduleModel
import com.domikado.bit.utility.makeText
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_schedule.*

/**
 * A simple [Fragment] subclass.
 */
class ScheduleListFragment : BaseFragment(), IScheduleListContract.View {

    private val args: ScheduleListFragmentArgs by navArgs()
    private val scheduleListEvent by lazy { MutableLiveData<ScheduleListEvent>() }
    private lateinit var rvSchedules: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val scheduleListViewModel: ScheduleListViewModel by viewModels()
        scheduleListViewModel
            .buildScheduleListLogic(this)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvSchedules = schedule_rv_
        rvSchedules.layoutManager = linearLayoutManager

        recyclerAdapter = RecyclerAdapter(
            arrayListOf<ScheduleModel>() as ArrayList<AbstractBaseItemModel>,
            ViewHolderTypeFactoryImpl(),
            rvListeners
        )
        rvSchedules.adapter = recyclerAdapter
    }

    override fun onStart() {
        super.onStart()
        val workTypeId = args.workTypeId
        if (workTypeId == 1)
            scheduleListEvent.value = ScheduleListEvent.OnStart
    }

    override fun loadSchedules(schedules: List<ScheduleModel>) {

        recyclerAdapter.setItems(schedules.toMutableList())
    }

    override fun startLoadingSchedule(loading: Loading) = showLoadingMessage(loading.title, loading.message)

    override fun dismissLoading() = hideLoading()

    override fun showError(t: Throwable, message: String?) {
        e(t)
        requireActivity().makeText("$message: ${t.message}", Toast.LENGTH_LONG)
    }

    override fun setObserver(observer: Observer<ScheduleListEvent>) = scheduleListEvent.observe(this, observer)

    private val rvListeners = mapOf(
        R.layout.item_schedule_parent to object: OnScheduleClickListener {

            override fun onSiteCheckInButtonClick(
                scheduleId: Int,
                workDate: String?,
                siteId: Int,
                siteName: String?,
                siteCode: String?,
                siteLatitude: Double?,
                siteLongitude: Double?,
                siteMonitorId: Int,
                operator: Operator?
            ) {
                navigateToCheckIn(scheduleId, workDate, siteId, siteName, siteCode, siteLatitude, siteLongitude, siteMonitorId, operator)
            }

            override fun onItemClick(model: ScheduleModel) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    )

    private fun navigateToCheckIn(scheduleId: Int,
                                  workDate: String?,
                                  siteId: Int,
                                  siteName: String?,
                                  siteCode: String?,
                                  siteLatitude: Double?,
                                  siteLongitude: Double?,
                                  siteMonitorId: Int,
                                  operator: Operator?) {

        d {"navigate checkin -> " +
                "scheduleId: $scheduleId, " +
                "workdate: $workDate, " +
                "site: $siteId, " +
                "sitename: $siteName, " +
                "sitecode: $siteCode, " +
                "sitelat: $siteLatitude, " +
                "siteLong: $siteLongitude, " +
                "siteMonitorId: $siteMonitorId, " +
                "operator: $operator"}
        val action = ScheduleListFragmentDirections.actionScheduleListFragmentToCheckInFragment(
            scheduleId,
            workDate,
            siteId,
            siteName,
            siteCode,
            siteLatitude.toString(),
            siteLongitude.toString(),
            siteMonitorId,
            Gson().toJson(operator, Operator::class.java)
        )
        findNavController().navigate(action)
    }
}
