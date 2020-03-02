package com.domikado.bit.abstraction.rx

import io.reactivex.Scheduler

interface ISchedulerProvider {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
}