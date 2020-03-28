package com.domikado.bit.domain.interactor

import com.domikado.bit.domain.domainmodel.FormFillData
import com.domikado.bit.domain.servicelocator.FormFillServiceLocator
import io.reactivex.Completable
import io.reactivex.Single

class FormFillSource {

    fun delete(listFormFillData: List<FormFillData>, locator: FormFillServiceLocator): Completable =
        locator.formFillDataRepository.delete(listFormFillData)

    fun addOrReplace(listFormFillData: List<FormFillData>, locator: FormFillServiceLocator): Completable =
        locator.formFillDataRepository.addOrUpdate(listFormFillData)

    fun getFormFillData(siteMonitorId: Int, locator: FormFillServiceLocator): Single<List<FormFillData>> =
        locator.formFillDataRepository.getFormFillData(siteMonitorId)
}