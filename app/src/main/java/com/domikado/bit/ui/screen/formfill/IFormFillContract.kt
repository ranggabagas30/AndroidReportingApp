package com.domikado.bit.ui.screen.formfill

import androidx.lifecycle.Observer
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.ui.screen.formfill.recyclerview.FormFillModel
import java.io.File

interface IFormFillContract {

    interface View {
        fun loadFormFill(formFillItems: List<FormFillModel>)
        fun showLoadingData(loading: Loading)
        fun dismissLoading()
        fun showError(t: Throwable, message: String? = null)
        fun uploadSuccess()
        fun setObserver(observer: Observer<FormFillEvent>)
    }
}

sealed class FormFillEvent {
    object OnViewCreated: FormFillEvent()
    data class OnSuccessTakePicture(val imageFile: File): FormFillEvent()
    object OnFailedTakePicture: FormFillEvent()
    data class OnTextChanged(val text: String?, val model: FormFillModel?): FormFillEvent()
    object OnUploadClick: FormFillEvent()
}