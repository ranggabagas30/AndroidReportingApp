package com.domikado.bit.ui.screen.formfill

import androidx.lifecycle.Observer
import com.domikado.bit.abstraction.base.BaseLogic
import com.domikado.bit.domain.domainmodel.FormFillData
import com.domikado.bit.domain.interactor.FormFillSource
import com.domikado.bit.domain.servicelocator.FormFillServiceLocator
import com.domikado.bit.ui.screen.formfill.recyclerview.BodyModel
import com.domikado.bit.ui.screen.formfill.recyclerview.FormFillModel
import com.domikado.bit.ui.screen.formfill.recyclerview.HeaderModel
import com.domikado.bit.ui.screen.formfill.recyclerview.SectionModel
import com.github.ajalt.timberkt.Timber
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.disposables.Disposable
import java.io.File

class FormFillLogic(
    private val view: IFormFillContract.View,
    private val formFillSource: FormFillSource,
    private val formFillServiceLocator: FormFillServiceLocator,
    private val formFillViewModel: FormFillViewModel
): BaseLogic(), Observer<FormFillEvent> {

    private val formFillData by lazy {
        PublishRelay.create<MutableList<FormFillData>>()
    }

    private val dataObserver by lazy {
        object: io.reactivex.Observer<MutableList<FormFillData>> {
            override fun onComplete() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSubscribe(d: Disposable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onNext(t: MutableList<FormFillData>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(e: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    override fun onChanged(t: FormFillEvent?) {
        when(t) {
            //is FormFillEvent.OnStart -> onStart()
            is FormFillEvent.OnTakePictureClick -> onTakePictureClick(t.formFillId)
            is FormFillEvent.OnSuccessTakePicture -> onSuccessTakePicture(t.formFillId, t.position, t.imageFile)
            is FormFillEvent.OnFailedTakePicture -> onFailedTakePicture()
        }
    }

    private fun onStart(siteMonitorId: Int) {

        formFillViewModel.async(
            formFillSource.getFormFillData(siteMonitorId, formFillServiceLocator)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ data ->
                    formFillData.accept(data.toMutableList())
                }, { t ->
                    Timber.e(t)
                })
        )


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

    private fun onSuccessTakePicture(formFillId: Int, position: Int, imageFile: File) {

    }

    private fun onFailedTakePicture() {

    }
}