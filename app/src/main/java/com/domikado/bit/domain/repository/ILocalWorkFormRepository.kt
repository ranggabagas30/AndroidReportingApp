package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.WorkForm
import com.domikado.bit.domain.domainmodel.WorkFormGroup
import com.domikado.bit.domain.domainmodel.WorkFormItem
import com.domikado.bit.domain.domainmodel.WorkFormRowColumn
import io.reactivex.Completable
import io.reactivex.Single

interface ILocalWorkFormRepository {

    fun getWorkForms(workTypeId: Int): Single<List<WorkForm>>

    fun getWorkFormGroups(workFormId: Int): Single<List<WorkFormGroup>>

    fun getWorkFormRowColumns(workFormGroupId: Int): Single<List<WorkFormRowColumn>>

    fun getWorkFormItems(workFormRowColumnId: Int): Single<List<WorkFormItem>>


    fun addWorkForms(workForms: List<WorkForm>): Completable


}