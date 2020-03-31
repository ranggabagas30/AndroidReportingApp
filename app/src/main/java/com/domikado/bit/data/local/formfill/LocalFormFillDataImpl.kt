package com.domikado.bit.data.local.formfill

import com.domikado.bit.data.local.database.dao.FormFillDataDao
import com.domikado.bit.domain.domainmodel.FormFillData
import com.domikado.bit.domain.domainmodel.toFormFillData
import com.domikado.bit.domain.domainmodel.toTbFormFillData
import com.domikado.bit.domain.repository.IFormFillDataRepository
import io.reactivex.Completable
import io.reactivex.Single

class LocalFormFillDataImpl(private val formFillDataDao: FormFillDataDao) : IFormFillDataRepository {

    override fun delete(listFormFillData: List<FormFillData>): Completable =
        formFillDataDao.delete(listFormFillData.map { it.toTbFormFillData })

    override fun delete(formFillModelId: Int, siteMonitorId: Int): Completable =
        formFillDataDao.delete(formFillModelId, siteMonitorId)

    override fun addOrUpdate(listFormFillData: List<FormFillData>): Completable =
        formFillDataDao.insertOrReplace(listFormFillData.map { it.toTbFormFillData })

    override fun getFormFillData(siteMonitorId: Int): Single<List<FormFillData>> {
        return formFillDataDao.getFormFillData(siteMonitorId).map {
            data -> data.map { it.toFormFillData }
        }
    }
}