package com.domikado.bit.ui.screen.schedulelist

import androidx.lifecycle.Observer
import com.domikado.bit.abstraction.base.BaseLogic
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.interactor.LocalScheduleSource
import com.domikado.bit.domain.interactor.RemoteScheduleSource
import com.domikado.bit.domain.servicelocator.ScheduleServiceLocator
import com.domikado.bit.domain.servicelocator.UserServiceLocator
import com.domikado.bit.ui.screen.schedulelist.recyclerview.ScheduleModel
import com.domikado.bit.ui.screen.schedulelist.recyclerview.ScheduleSiteModel

/**
 * - mengunduh data schedule dari remote server
 * - menyimpan data schedule ke dalam database lokal
 * - meload list data schedule ke halaman ScheduleListFragment
 *
 * */
class ScheduleListLogic(
    private val view: IScheduleListContract.View,
    private val authSource: AuthSource,
    private val localScheduleSource: LocalScheduleSource,
    private val remoteScheduleSource: RemoteScheduleSource,
    private val scheduleServiceLocator: ScheduleServiceLocator,
    private val authServiceLocator: UserServiceLocator
): BaseLogic(), Observer<ScheduleListEvent> {

    private lateinit var schedules: List<ScheduleModel>

    override fun onChanged(t: ScheduleListEvent?) {
        when(t) {
            is ScheduleListEvent.OnStart -> onStart()
            is ScheduleListEvent.OnScheduleItemClick -> onScheduleItemClick()
        }
    }

    private fun onStart() {
        schedules = generateDummyList()
        view.loadSchedules(schedules as ArrayList<ScheduleModel>)
    }

    private fun onScheduleItemClick() {

    }

    private fun generateDummyList(): ArrayList<ScheduleModel> {

        return arrayListOf(
            ScheduleModel(
                1,
                "50%",
                "On Progress",
                "FO MONITORING",
                "2020-11-22",
                listOf(
                    ScheduleSiteModel(
                        1,
                        "PT. Domikado",
                        0,
                        -6.21312312,
                        108.123121231231,
                        "DMKD",
                        1
                    ),
                    ScheduleSiteModel(
                        2,
                        "PT. Mandiri",
                        0,
                        -6.21312312,
                        108.123121231231,
                        "MNDR",
                    2
                    )
                )
            ),
            ScheduleModel(
                2,
                "100%",
                "Approved",
                "FO MONITORING",
                "2020-05-10",
                listOf(
                    ScheduleSiteModel(
                        3,
                        "Rs. Medika",
                        2,
                        -6.21312312,
                        108.123121231231,
                        "MDKA",
                        3

                    ),
                    ScheduleSiteModel(
                        4,
                        "Grha Cimb Niaga",
                        2,
                        -6.21312312,
                        108.123121231231,
                        "CIMB",
                        4
                    )
                )
            )
        )
    }
}