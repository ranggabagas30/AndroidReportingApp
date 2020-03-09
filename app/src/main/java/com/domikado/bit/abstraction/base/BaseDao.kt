package com.domikado.bit.abstraction.base

import io.reactivex.Completable

abstract class BaseDao<T> {

    abstract fun insert(vararg obj: T): Completable

    abstract fun delete(vararg obj: T): Completable

    abstract fun update(vararg obj: T): Completable
}