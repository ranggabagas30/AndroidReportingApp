package com.domikado.bit.ui.screen.formfill

import androidx.lifecycle.Observer
import com.domikado.bit.ui.screen.formfill.recyclerview.FormFillModel

interface IFormFillContract {

    interface View {
        fun loadFormFill(formFillItems: List<FormFillModel>)
        fun setObserver(observer: Observer<FormFillEvent>)
    }
}

sealed class FormFillEvent {
    object OnStart: FormFillEvent()
    data class OnTakePictureClick(val formFillId: Int): FormFillEvent()
    object OnSuccessTakePicture: FormFillEvent()
    object OnFailedTakePicture: FormFillEvent()
    object OnTextChanged: FormFillEvent()
}