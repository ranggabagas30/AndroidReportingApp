package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.Operator
import io.reactivex.Completable
import io.reactivex.Single

interface ILocalOperatorRepository {

    fun delete(operators: List<Operator>): Completable

    fun addOrUpdate(operators: List<Operator>): Completable

    fun getOperatorsBySiteMonitorId(siteMonitorId: Int): Single<List<Operator>>
}