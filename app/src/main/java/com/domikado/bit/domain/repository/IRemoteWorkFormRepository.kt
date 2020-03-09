package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.WorkForm
import io.reactivex.Single

interface IRemoteWorkFormRepository {

    fun getWorkForms(workTypeId: Int): Single<List<WorkForm>>

}