package com.domikado.bit.ui.screen.formfill

import android.text.TextUtils
import androidx.lifecycle.Observer
import com.domikado.bit.abstraction.base.BaseLogic
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.domain.domainmodel.toFormFillData
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.interactor.FormFillSource
import com.domikado.bit.domain.interactor.OperatorSource
import com.domikado.bit.domain.servicelocator.FormFillServiceLocator
import com.domikado.bit.domain.servicelocator.OperatorServiceLocator
import com.domikado.bit.domain.servicelocator.UserServiceLocator
import com.domikado.bit.ui.screen.formfill.recyclerview.BodyModel
import com.domikado.bit.ui.screen.formfill.recyclerview.FormFillModel
import com.domikado.bit.ui.screen.formfill.recyclerview.HeaderModel
import com.domikado.bit.ui.screen.formfill.recyclerview.SectionModel
import com.domikado.bit.utility.PREF_KEY_FIREBASE_ID
import com.github.ajalt.timberkt.d
import com.pixplicity.easyprefs.library.Prefs
import java.io.File

class FormFillLogic(
    private val view: IFormFillContract.View,
    private val authSource: AuthSource,
    private val formFillSource: FormFillSource,
    private val operatorSource: OperatorSource,
    private val userServiceLocator: UserServiceLocator,
    private val formFillServiceLocator: FormFillServiceLocator,
    private val operatorServiceLocator: OperatorServiceLocator,
    private val formFillViewModel: FormFillViewModel
): BaseLogic(), Observer<FormFillEvent> {

    private val formFillModels by lazy { ArrayList<FormFillModel>() }

    override fun onChanged(t: FormFillEvent?) {
        when(t) {
            is FormFillEvent.OnViewCreated -> onViewCreated()
            is FormFillEvent.OnSuccessTakePicture -> onSuccessTakePicture(t.imageFile)
            is FormFillEvent.OnFailedTakePicture -> onFailedTakePicture()
            is FormFillEvent.OnTextChanged -> onTextChanged(t.text, t.model)
            is FormFillEvent.OnUploadClick -> onUploadClick()
        }
    }

    private fun onViewCreated() {
        if (formFillViewModel.args == null) {
            view.showError(NullPointerException(SITE_MONITOR_ID_KOSONG))
            return
        }
        initForm()
        loadFormFillData()
    }

    private fun initForm() {
        formFillModels.addAll(listOf(
            FormFillModel(
                0,
                formFillViewModel.args!!.siteMonitorId,
                null,
                HeaderModel("Photo 1"),
                BodyModel(
                    arrayListOf(
                        SectionModel.PhotoLayoutModel(
                            0,
                            "OTDR",
                            formFillViewModel.args!!.operator?: "No operator", // first phase of development, using only one operato
                            "Photo 1",
                            true
                        )
                    )
                )
            ),
            FormFillModel(
                1,
                formFillViewModel.args!!.siteMonitorId,
                null,
                HeaderModel("Photo 2"),
                BodyModel(
                    arrayListOf(
                        SectionModel.PhotoLayoutModel(
                            1,
                            "OTDR",
                            formFillViewModel.args!!.operator?: "No operator",
                            "Photo 2",
                            true
                        )
                    )
                )
            )
        ))
    }

    private fun loadFormFillData() {
        view.showLoadingData(Loading(message = "Memuat form"))
        formFillViewModel.async(
            formFillSource.getFormFillData(formFillViewModel.args!!.siteMonitorId, formFillServiceLocator)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ data ->
                    if (data != null && data.isNotEmpty()) {
                        for (formFillData in data) {
                            (formFillModels[formFillData.formFillModelId].body.sections[0] as SectionModel.PhotoLayoutModel).photoPath = formFillData.image
                            (formFillModels[formFillData.formFillModelId].body.sections[0] as SectionModel.PhotoLayoutModel).remark = formFillData.remark
                        }
                    }
                    view.dismissLoading()
                    view.loadFormFill(formFillModels)
                }, { t ->
                    view.showError(t, GAGAL_MEMUAT_FORM)
                })
        )
    }

    private fun onSuccessTakePicture(imageFile: File) {
        formFillViewModel.photoFormFillModel?.also {
            (it.body.sections[0] as SectionModel.PhotoLayoutModel).photoPath = imageFile.path
            val formFillData = it.toFormFillData
            view.showLoadingData(Loading(message = "Menyimpan photo"))
            formFillViewModel.async(
                formFillSource.addOrReplace(listOf(formFillData), formFillServiceLocator)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({
                        (formFillModels[formFillData.formFillModelId].body.sections[0] as SectionModel.PhotoLayoutModel).photoPath = formFillData.image
                        view.dismissLoading()
                        view.loadFormFill(formFillModels)
                    }, { t->
                        view.dismissLoading()
                        view.showError(t, GAGAL_MENYIMPAN_FOTO)
                    })
            )
        }

    }

    private fun onFailedTakePicture() {}

    private fun onTextChanged(text: String?, model: FormFillModel?) {
        formFillViewModel.args?.also { args ->
            model?.also {
                (it.body.sections[0] as SectionModel.PhotoLayoutModel).remark = text
                it.toFormFillData.also { formFillData ->
                    if (TextUtils.isEmpty(text)) {
                        if (TextUtils.isEmpty(formFillData.itemValue) && TextUtils.isEmpty(formFillData.image)) {
                            formFillViewModel.async(
                                formFillSource.delete(it.id, args.siteMonitorId, formFillServiceLocator)
                                    .subscribeOn(schedulerProvider.io())
                                    .observeOn(schedulerProvider.ui())
                                    .subscribe({
                                        d {"delete form fill data: $formFillData"}
                                    }, { t ->
                                        view.showError(t, "$GAGAL_MENGHAPUS_DATA_TEXT_ITEM ${formFillData.title}")
                                    })
                            )
                        }
                    } else {
                        formFillViewModel.async(
                            formFillSource.addOrReplace(listOf(formFillData), formFillServiceLocator)
                                .subscribeOn(schedulerProvider.io())
                                .observeOn(schedulerProvider.ui())
                                .subscribe({
                                    d {"saved form fill data: $formFillData"}
                                }, { t ->
                                    view.showError(t, "$GAGAL_MENYIMPAN_DATA_TEXT $text")
                                })
                        )
                    }
                }
            }
        }
    }

    private fun onUploadClick() {
        uploadData()
    }

    private fun uploadData() {
        if (formFillViewModel.args == null) {
            view.showError(NullPointerException(SITE_MONITOR_ID_KOSONG))
            return
        }

        authSource.getCurrentUser(userServiceLocator)?.also {
            val firebaseId = Prefs.getString(PREF_KEY_FIREBASE_ID, null)
            view.showLoadingData(Loading("Mengupload data form", "Mulai mengupload. Jangan menghentikan aplikasi"))
            formFillViewModel.async(
                formFillSource.uploadData(it.id, it.accessToken, firebaseId, formFillViewModel.args!!.siteMonitorId, formFillServiceLocator)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({
                        view.dismissLoading()
                        view.uploadSuccess()
                    }, { t ->
                        view.dismissLoading()
                        view.showError(t, GAGAL_MENGUPLOAD_DATA)
                    })
            )
        }

    }
}

internal const val SITE_MONITOR_ID_KOSONG = "Site monitor id kosong"
internal const val FORM_FILL_KOSONG = "Tidak dapat mengambil gambar. Form fill model item ini kosong"
internal const val TERJADI_KESALAHAN = "Terjadi kesalahan"
internal const val GAGAL_MEMUAT_FORM = "Gagal memuat form"
internal const val GAGAL_MENGUPLOAD_DATA = "Gagal mengupload data"
internal const val GAGAL_MENYIMPAN_DATA_TEXT = "Gagal menyimpan data text: "
internal const val GAGAL_MENGHAPUS_DATA_TEXT_ITEM = "Gagal menghapus data text item"
internal const val GAGAL_MENYIMPAN_FOTO = "Gagal menyimpan foto"
internal const val GAGAL_MENGAMBIL_FOTO = "Tidak dapat mengambil foto. Data scheduleid, site latitude, dan longitude kosong"
