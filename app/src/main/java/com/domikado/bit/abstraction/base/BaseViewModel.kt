package com.domikado.bit.abstraction.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class BaseViewModel : ViewModel() {
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    fun subscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}