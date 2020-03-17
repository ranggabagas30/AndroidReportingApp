package com.domikado.bit.abstraction.state

sealed class LoadingState<out Loading> {
    data class ShowLoading<out Loading>(val loading: Loading): LoadingState<Loading>()
    object HideLoading: LoadingState<Nothing>()
}