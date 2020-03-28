package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.FormFillData
import io.reactivex.Completable
import io.reactivex.Single

interface IFormFillDataRepository {

    fun delete(listFormFillData: List<FormFillData>): Completable

    fun addOrUpdate(listFormFillData: List<FormFillData>): Completable

    fun getFormFillData(siteMonitorId: Int): Single<List<FormFillData>>
}