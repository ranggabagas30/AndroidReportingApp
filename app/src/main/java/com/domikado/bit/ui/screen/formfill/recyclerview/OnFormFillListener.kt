package com.domikado.bit.ui.screen.formfill.recyclerview

import com.domikado.bit.abstraction.recyclerview.IBaseRvListener

interface OnFormFillListener: IBaseRvListener<FormFillModel> {

    fun onCheckBoxChanged(checkBoxItems: MutableMap<String, Boolean>, position: Int)

    fun onTextChanged(formFillId: Int, s: String?, position: Int)

    fun onTakePicture(formFillId: Int, position: Int)
}