package com.domikado.bit.abstraction.state

sealed class LoadingState {
    object ShowLoading: LoadingState()
    object HideLoading: LoadingState()
}