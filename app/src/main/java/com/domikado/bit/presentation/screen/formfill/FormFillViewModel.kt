package com.domikado.bit.presentation.screen.formfill

import android.app.Application
import com.domikado.bit.abstraction.base.BaseAndroidViewModel
import com.domikado.bit.data.localrepo.database.BitDatabase
import com.domikado.bit.data.remoterepo.retrofit.BitAPI
import com.domikado.bit.data.localrepo.auth.LocalAuthenticationImpl
import com.domikado.bit.data.localrepo.database.dao.FormFillDataDao
import com.domikado.bit.data.localrepo.database.dao.OperatorDao
import com.domikado.bit.data.localrepo.formfill.LocalFormFillDataImpl
import com.domikado.bit.data.localrepo.operator.LocalOperatorImpl
import com.domikado.bit.data.remoterepo.auth.AuthenticationImpl
import com.domikado.bit.data.remoterepo.formfill.RemoteFormFillImpl
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.interactor.FormFillSource
import com.domikado.bit.domain.interactor.OperatorSource
import com.domikado.bit.domain.servicelocator.FormFillServiceLocator
import com.domikado.bit.domain.servicelocator.OperatorServiceLocator
import com.domikado.bit.domain.servicelocator.UserServiceLocator
import com.domikado.bit.presentation.screen.formfill.recyclerview.FormFillModel

class FormFillViewModel(
    application: Application
): BaseAndroidViewModel(application) {

    var args: FormFillFragmentArgs? = null

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