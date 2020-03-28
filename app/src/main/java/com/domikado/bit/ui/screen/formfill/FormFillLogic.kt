package com.domikado.bit.ui.screen.formfill

import androidx.lifecycle.Observer
import com.domikado.bit.abstraction.base.BaseLogic
import com.domikado.bit.domain.interactor.FormFillSource
import com.domikado.bit.domain.servicelocator.FormFillServiceLocator
import com.domikado.bit.ui.screen.formfill.recyclerview.BodyModel
import com.domikado.bit.ui.screen.formfill.recyclerview.FormFillModel
import com.domikado.bit.ui.screen.formfill.recyclerview.HeaderModel
import com.domikado.bit.ui.screen.formfill.recyclerview.SectionModel

class FormFillLogic(
    private val view: IFormFillContract.View,
    private val formFillSource: FormFillSource,
    private val formFillServiceLocator: FormFillServiceLocator,
    private val formFillViewModel: FormFillViewModel
): BaseLogic(), Observer<FormFillEvent> {

    override fun onChanged(t: FormFillEvent?) {
        when(t) {
            is FormFillEvent.OnStart -> onStart()
            is FormFillEvent.OnTakePictureClick -> onTakePictureClick(t.formFillId)
            is FormFillEvent.OnSuccessTakePicture -> onSuccessTakePicture()
            is FormFillEvent.OnFailedTakePicture -> onFailedTakePicture()
        }
    }

    private fun onStart() {
        view.loadFormFill(listOf(
            FormFillModel(
                0,
                HeaderModel("Photo 1"),
                BodyModel(
                    arrayListOf(
                        SectionModel.PhotoLayoutModel(
                            0,
                            "OTDR",
                            "Indosat",
                            "Photo 1"
                        )
                    )
                )
            ),
            FormFillModel(
                1,
                HeaderModel("Photo 2"),
                BodyModel(
                    arrayListOf(
                        SectionModel.PhotoLayoutModel(
                            1,
                            "OTDR",
                            "Indosat",
                            "Photo 2"
                        )
                    )
                )
            )
        ))
    }

    private fun onTakePictureClick(formFillId: Int) {

    }

    private fun onSuccessTakePicture() {

    }

    private fun onFailedTakePicture() {

    }
}