package com.domikado.bit.presentation.screen.formfill

import android.text.TextUtils
import androidx.lifecycle.Observer
import com.domikado.bit.abstraction.base.BaseLogic
import com.domikado.bit.domain.domainmodel.FormFillData
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.domain.domainmodel.Operator
import com.domikado.bit.domain.domainmodel.toFormFillData
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.interactor.FormFillSource
import com.domikado.bit.domain.interactor.OperatorSource
import com.domikado.bit.domain.servicelocator.FormFillServiceLocator
import com.domikado.bit.domain.servicelocator.OperatorServiceLocator
import com.domikado.bit.domain.servicelocator.UserServiceLocator
import com.domikado.bit.external.PREF_KEY_FIREBASE_ID
import com.domikado.bit.external.utils.DateUtil
import com.domikado.bit.presentation.screen.formfill.recyclerview.BodyModel
import com.domikado.bit.presentation.screen.formfill.recyclerview.FormFillModel
import com.domikado.bit.presentation.screen.formfill.recyclerview.HeaderModel
import com.domikado.bit.presentation.screen.formfill.recyclerview.SectionModel
import com.github.ajalt.timberkt.d
import com.google.gson.Gson
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
    private var operator: Operator? = null

    override fun onChanged(t: FormFillEvent?) {
        when(t) {
            is FormFillEvent.OnViewCreated -> onViewCreated()
            is FormFillEvent.OnSuccessTakePicture -> onSuccessTakePicture(t.imageFile, t.latitude, t.longitude)
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
        operator = Gson().fromJson(formFillViewModel.args!!.operator, Operator::class.java)

        formFillModels.addAll(listOf(
            FormFillModel(
                0,
                formFillViewModel.args!!.siteMonitorId,
                null,
                null,
                HeaderModel("Photo 1"),
                BodyModel(
                    arrayListOf(
                        SectionModel.PhotoLayoutModel(
                            0,
                            "OTDR",
                            operator!!.name?: "No operator", // first phase of development, using only one operato
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
                null,
                HeaderModel("Photo 2"),
                BodyModel(
                    arrayListOf(
                        SectionModel.PhotoLayoutModel(
                            0,
                            "OTDR",
                            operator!!.name?: "No operator",
                            "Photo 2",
                            true
                        )
                    )
                )
            ),
            FormFillModel(
                2,
                formFillViewModel.args!!.siteMonitorId,
                null,
                null,
                HeaderModel("Photo 3"),
                BodyModel(
                    arrayListOf(
                        SectionModel.PhotoLayoutModel(
                            0,
                            "OTDR",
                            operator!!.name?: "No operator",
                            "Photo 3",
                            true
                        )
                    )
                )
            ),
            FormFillModel(
                3,
                formFillViewModel.args!!.siteMonitorId,
                null,
                null,
                HeaderModel("Photo 4"),
                BodyModel(
                    arrayListOf(
                        SectionModel.PhotoLayoutModel(
                            0,
                            "OTDR",
                            operator!!.name?: "No operator",
                            "Photo 4",
                            true
                        )
                    )
                )
            ),
            FormFillModel(
                4,
                formFillViewModel.args!!.siteMonitorId,
                null,
                null,
                HeaderModel("Photo 5"),
                BodyModel(
                    arrayListOf(
                        SectionModel.PhotoLayoutModel(
                            0,
                            "OTDR",
                            operator!!.name?: "No operator",
                            "Photo 5",
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
                            val model = formFillModels[formFillData.formFillModelId]
                            model.modifiedAt = formFillData.modifiedAt?: DateUtil.getNowEpochMilli()
                            (model.body.sections[0] as SectionModel.PhotoLayoutModel).photoPath = formFillData.image
                            (model.body.sections[0] as SectionModel.PhotoLayoutModel).remark = formFillData.remark
                            (model.body.sections[0] as SectionModel.PhotoLayoutModel).latitude = formFillData.latitude
                            (model.body.sections[0] as SectionModel.PhotoLayoutModel).longitude = formFillData.longitude
                        }
                    }
                    view.dismissLoading()
                    view.loadFormFill(formFillModels)
                }, { t ->
                    view.showError(t, GAGAL_MEMUAT_FORM)
                })
        )
    }

    private fun onSuccessTakePicture(imageFile: File, latitude: Double, longitude: Double) {
        formFillViewModel.photoFormFillModel?.also {

            // update form fill model value
            it.modifiedAt = DateUtil.getNowEpochMilli()
            (it.body.sections[0] as SectionModel.PhotoLayoutModel).photoPath = imageFile.path
            (it.body.sections[0] as SectionModel.PhotoLayoutModel).latitude = latitude
            (it.body.sections[0] as SectionModel.PhotoLayoutModel).longitude = longitude

            // assign to form fill data in order to insert/update the data on the table
            it.toFormFillData.apply {

                // assign item value = item title
                this.itemValue = it.header.title

            }.also { formFillData ->
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

    }

    private fun onFailedTakePicture() {}

    private fun onTextChanged(text: String?, model: FormFillModel?) {
        model?.also {
            // update form fill model value
            it.modifiedAt = DateUtil.getNowEpochMilli()
            (it.body.sections[0] as SectionModel.PhotoLayoutModel).remark = text

            it.toFormFillData.apply {

                // assign item value = item title
                this.itemValue = it.header.title

            }.also { formFillData ->
                if (TextUtils.isEmpty(formFillData.remark) &&
                    TextUtils.isEmpty(formFillData.image)) { // only delete form fill data when no image and no remark
                    deleteData(formFillData)
                } else {
                    saveData(formFillData)
                }

            }
        }
    }

    private fun deleteData(formFillData: FormFillData) {
        formFillViewModel.async(
            formFillSource.delete(formFillData.formFillModelId, formFillData.siteMonitorId, formFillServiceLocator)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    d {"delete form fill data: $formFillData"}
                }, { t ->
                    view.showError(t, "$GAGAL_MENGHAPUS_DATA_TEXT_ITEM ${formFillData.title}")
                })
        )
    }

    private fun saveData(formFillData: FormFillData) {
        formFillViewModel.async(
            formFillSource.addOrReplace(listOf(formFillData), formFillServiceLocator)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    d {"saved form fill data: $formFillData"}
                }, { t ->
                    view.showError(t, "$GAGAL_MENYIMPAN_DATA_TEXT ${formFillData.remark}")
                })
        )
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
                    .map { upload ->
                        val finishAt = upload.site?.finishAt
                        formFillModels.map { model ->
                            model.toFormFillData.apply {
                                this.lastUploadAt = finishAt // update last upload time
                            }
                        }.filter { data ->
                            !TextUtils.isEmpty(data.remark) ||
                            !TextUtils.isEmpty(data.image)
                        }
                    }
                    .flatMapCompletable { data ->
                        d { "update upload data: $data"}
                        formFillSource.addOrReplace(data, formFillServiceLocator) // update data table
                    }
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
internal const val GAGAL_MENYIMPAN_DATA_TEXT = "Gagal menyimpan data text"
internal const val GAGAL_MENGHAPUS_DATA_TEXT_ITEM = "Gagal menghapus data text item"
internal const val GAGAL_MENYIMPAN_FOTO = "Gagal menyimpan foto"
internal const val GAGAL_MENGAMBIL_FOTO = "Tidak dapat mengambil foto. Data scheduleid, site latitude, dan longitude kosong"
