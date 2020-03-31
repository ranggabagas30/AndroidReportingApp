package com.domikado.bit.ui.screen.formfill

import android.app.Application
import com.domikado.bit.abstraction.base.BaseAndroidViewModel
import com.domikado.bit.abstraction.database.BitDatabase
import com.domikado.bit.abstraction.network.BitAPI
import com.domikado.bit.data.local.auth.LocalAuthenticationImpl
import com.domikado.bit.data.local.database.dao.FormFillDataDao
import com.domikado.bit.data.local.database.dao.OperatorDao
import com.domikado.bit.data.local.formfill.LocalFormFillDataImpl
import com.domikado.bit.data.local.operator.LocalOperatorImpl
import com.domikado.bit.data.remote.auth.AuthenticationImpl
import com.domikado.bit.data.remote.formfill.RemoteFormFillImpl
import com.domikado.bit.domain.domainmodel.Operator
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.interactor.FormFillSource
import com.domikado.bit.domain.interactor.OperatorSource
import com.domikado.bit.domain.servicelocator.FormFillServiceLocator
import com.domikado.bit.domain.servicelocator.OperatorServiceLocator
import com.domikado.bit.domain.servicelocator.UserServiceLocator
import com.domikado.bit.ui.screen.formfill.recyclerview.FormFillModel

class FormFillViewModel(
    application: Application
): BaseAndroidViewModel(application) {

    var siteMonitorId: Int = -1
    var operator: Operator? = null
    var photoFormFillModel: FormFillModel? = null

    private val userDao by lazy {
        BitDatabase.getInstance(application).userDao()
    }

    private val formFillDataDao: FormFillDataDao by lazy {
        BitDatabase.getInstance(application).formFillDataDao()
    }

    private val operatorDao: OperatorDao by lazy {
        BitDatabase.getInstance(application).operatorDao()
    }

    private val localFormFillData by lazy {
        LocalFormFillDataImpl(formFillDataDao)
    }

    private val remoteFormFillData by lazy {
        RemoteFormFillImpl(BitAPI)
    }

    private val localAuth by lazy {
        LocalAuthenticationImpl(userDao)
    }

    private val auth by lazy {
        AuthenticationImpl(BitAPI)
    }

    private val localOperator by lazy {
        LocalOperatorImpl(operatorDao)
    }

    fun buildFormFillLogic(view: FormFillFragment) = FormFillLogic(
            view,
            AuthSource(),
            FormFillSource(),
            OperatorSource(),
            UserServiceLocator(localAuth, auth),
            FormFillServiceLocator(localFormFillData, remoteFormFillData),
            OperatorServiceLocator(localOperator),
            this
        ).also { view.setObserver(it) }


}