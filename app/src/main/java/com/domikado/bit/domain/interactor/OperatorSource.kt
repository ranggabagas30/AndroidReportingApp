package com.domikado.bit.domain.interactor

import com.domikado.bit.domain.domainmodel.Operator
import com.domikado.bit.domain.servicelocator.OperatorServiceLocator
import io.reactivex.Completable
import io.reactivex.Single

class OperatorSource {

    fun delete(operators: List<Operator>, locator: OperatorServiceLocator): Completable =
        locator.localOperatorRepository.delete(operators)

    fun addOrUpdate(operators: List<Operator>, locator: OperatorServiceLocator): Completable =
        locator.localOperatorRepository.addOrUpdate(operators)

    fun getOperatorsBySiteMonitorId(siteMonitorId: Int, locator: OperatorServiceLocator): Single<List<Operator>> =
        locator.localOperatorRepository.getOperatorsBySiteMonitorId(siteMonitorId)
}