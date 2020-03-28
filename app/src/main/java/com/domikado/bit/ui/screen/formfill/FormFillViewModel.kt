package com.domikado.bit.ui.screen.formfill

import android.app.Application
import com.domikado.bit.abstraction.base.BaseAndroidViewModel
import com.domikado.bit.abstraction.database.BitDatabase
import com.domikado.bit.data.local.database.dao.FormFillDataDao
import com.domikado.bit.data.local.formfill.LocalFormFillDataImpl
import com.domikado.bit.domain.interactor.FormFillSource
import com.domikado.bit.domain.servicelocator.FormFillServiceLocator

class FormFillViewModel(
    application: Application
): BaseAndroidViewModel(application) {

    private val formFillDataDao: FormFillDataDao by lazy {
        BitDatabase.getInstance(application).formFillDataDao()
    }

    private val localFormFillData by lazy {
        LocalFormFillDataImpl(formFillDataDao)
    }

    fun buildFormFillLogic(view: FormFillFragment) =
        FormFillLogic(
            view,
            FormFillSource(),
            FormFillServiceLocator(localFormFillData),
            this
        ).also { view.setObserver(it) }


}