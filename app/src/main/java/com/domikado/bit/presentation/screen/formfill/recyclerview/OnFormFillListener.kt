package com.domikado.bit.presentation.screen.formfill.recyclerview

import com.domikado.bit.abstraction.recyclerview.IBaseRvListener

interface OnFormFillListener: IBaseRvListener<FormFillModel> {

    fun onCheckBoxChanged(checkBoxItems: MutableMap<String, Boolean>, position: Int)

    fun onTextChanged(text: String?, formFillModel: FormFillModel?)

    fun onTakePicture(formFillModel: FormFillModel?)
}