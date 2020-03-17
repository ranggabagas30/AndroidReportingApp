package com.domikado.bit.abstraction.base

import com.domikado.bit.abstraction.rx.AppSchedulerProvider
import com.domikado.bit.abstraction.rx.ISchedulerProvider

abstract class BaseLogic {

    protected val schedulerProvider: ISchedulerProvider by lazy {
        AppSchedulerProvider()
    }
}