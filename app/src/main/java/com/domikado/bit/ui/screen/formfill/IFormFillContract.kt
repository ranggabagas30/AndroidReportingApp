package com.domikado.bit.ui.screen.formfill

import androidx.lifecycle.Observer
import com.domikado.bit.ui.screen.formfill.recyclerview.FormFillModel
import java.io.File

interface IFormFillContract {

    interface View {
        fun loadFormFill(formFillItems: List<FormFillModel>)
        fun setObserver(observer: Observer<FormFillEvent>)
    }
}

sealed class FormFillEvent {
    object OnStart: FormFillEvent()
    data class OnTakePictureClick(val formFillId: Int): FormFillEvent()
    data class OnSuccessTakePicture(val formFillId: Int, val position: Int, val imageFile: File): FormFillEvent()
    object OnFailedTakePicture: FormFillEvent()
    object OnTextChanged: FormFillEvent()
}