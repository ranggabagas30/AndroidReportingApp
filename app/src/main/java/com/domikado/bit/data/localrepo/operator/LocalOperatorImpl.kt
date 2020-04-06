package com.domikado.bit.data.localrepo.operator

import com.domikado.bit.data.localrepo.database.dao.OperatorDao
import com.domikado.bit.domain.domainmodel.Operator
import com.domikado.bit.domain.domainmodel.toOperator
import com.domikado.bit.domain.domainmodel.toTbOperator
import com.domikado.bit.domain.repository.ILocalOperatorRepository
import io.reactivex.Completable
import io.reactivex.Single

class LocalOperatorImpl(private val operatorDao: OperatorDao): ILocalOperatorRepository {

    override fun delete(operators: List<Operator>): Completable =
        operatorDao.delete(operators.map { it.toTbOperator })

    override fun addOrUpdate(operators: List<Operator>): Completable =
        operatorDao.insertOrReplace(operators.map { it.toTbOperator })

    override fun getOperatorsBySiteMonitorId(siteMonitorId: Int): Single<List<Operator>> =
        operatorDao.getOperatorsBySiteMonitorId(siteMonitorId).map { data ->
            data.map {
                it.toOperator
            }
        }
}