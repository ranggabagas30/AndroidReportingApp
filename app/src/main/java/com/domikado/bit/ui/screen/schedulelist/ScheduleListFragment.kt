package com.domikado.bit.ui.screen.schedulelist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.domikado.bit.R
import com.domikado.bit.abstraction.base.BaseFragment
import com.domikado.bit.abstraction.recyclerview.AbstractBaseItemModel
import com.domikado.bit.abstraction.recyclerview.RecyclerAdapter
import com.domikado.bit.abstraction.recyclerview.ViewHolderTypeFactoryImpl
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.ui.screen.login.recyclerview.OnScheduleClickListener
import com.domikado.bit.ui.screen.login.recyclerview.ScheduleModel
import kotlinx.android.synthetic.main.fragment_schedule.*

/**
 * A simple [Fragment] subclass.
 */
class ScheduleListFragment : BaseFragment(), IScheduleListContract.View {

    private val args: ScheduleListFragmentArgs by navArgs()
    private val scheduleListEvent = MutableLiveData<ScheduleListEvent>()
    private lateinit var rvSchedules: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var schedules: ArrayList<ScheduleModel>

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

        schedules = arrayListOf()
        val listeners = mapOf(
            R.layout.item_schedule_parent to object: OnScheduleClickListener {

                override fun onSiteCheckInButtonClick(
                    scheduleId: Int,
                    workDate: String?,
                    siteId: Int,
                    siteName: String?,
                    siteIdCustomer: String?,
                    siteLatitude: Double?,
                    siteLongitude: Double?
                ) {
                    navigateToCheckIn(scheduleId, workDate, siteId, siteName, siteIdCustomer, siteLatitude, siteLongitude)
                }

                override fun onItemClick(model: ScheduleModel) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            }
        )
        recyclerAdapter = RecyclerAdapter(schedules as ArrayList<AbstractBaseItemModel>, ViewHolderTypeFactoryImpl(), listeners)
        rvSchedules.adapter = recyclerAdapter
    }

    override fun onStart() {
        super.onStart()
        val workTypeId = args.workTypeId
        if (workTypeId == 1)
            scheduleListEvent.value = ScheduleListEvent.OnStart
    }

    override fun loadSchedules(schedules: ArrayList<ScheduleModel>) = recyclerAdapter.setItems(schedules as ArrayList<AbstractBaseItemModel>)

    override fun uploadScheduleData(schedule: ScheduleModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startLoadingSchedule(loading: Loading) = showLoadingMessage(loading.title, loading.message)

    override fun dismissLoading() = hideLoading()

    override fun setObserver(observer: Observer<ScheduleListEvent>) = scheduleListEvent.observe(this, observer)

    private fun navigateToCheckIn(scheduleId: Int,
                                  workDate: String?,
                                  siteId: Int,
                                  siteName: String?,
                                  siteIdCustomer: String?,
                                  siteLatitude: Double?,
                                  siteLongitude: Double?) {
//        val action = ScheduleListFragmentDirections.actionScheduleListFragmentToCheckInFragment(
//            scheduleId,
//            workDate,
//            siteId,
//            siteName,
//            siteIdCustomer,
//            siteLatitude.toString(),
//            siteLongitude.toString()
//        )
        //findNavController().navigate(action)
    }
}
