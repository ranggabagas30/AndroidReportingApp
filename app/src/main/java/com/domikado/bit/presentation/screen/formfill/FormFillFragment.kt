package com.domikado.bit.presentation.screen.formfill


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.domikado.bit.R
import com.domikado.bit.abstraction.base.BaseFragment
import com.domikado.bit.abstraction.recyclerview.AbstractBaseItemModel
import com.domikado.bit.abstraction.recyclerview.RecyclerAdapter
import com.domikado.bit.abstraction.recyclerview.ViewHolderTypeFactoryImpl
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.external.makeText
import com.domikado.bit.external.utils.DebugUtil
import com.domikado.bit.presentation.common.camera.CameraActivity
import com.domikado.bit.presentation.screen.formfill.recyclerview.FormFillModel
import com.domikado.bit.presentation.screen.formfill.recyclerview.OnFormFillListener
import com.github.ajalt.timberkt.d
import kotlinx.android.synthetic.main.fragment_form.*
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class FormFillFragment : BaseFragment(), IFormFillContract.View {

    private val args: FormFillFragmentArgs by navArgs()
    private lateinit var recyclerAdapter: RecyclerAdapter
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false) }
    private val formFillEvent by lazy { MutableLiveData<FormFillEvent>() }
    private val formFillViewModel: FormFillViewModel by viewModels()
    private val RC_TAKE_PICTURE = 1

    override fun setObserver(observer: Observer<FormFillEvent>) = formFillEvent.observe(viewLifecycleOwner, observer)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        formFillViewModel.buildFormFillLogic(this)
        formFillViewModel.args = args

        recyclerAdapter = RecyclerAdapter(
            arrayListOf<FormFillModel>() as ArrayList<AbstractBaseItemModel>,
            ViewHolderTypeFactoryImpl(),
            rvListener
        )

        formfill_rv.layoutManager = linearLayoutManager
        formfill_rv.adapter = recyclerAdapter

        formfill_upload.setOnClickListener {
            formFillEvent.value = FormFillEvent.OnUploadClick
        }

        formFillEvent.value = FormFillEvent.OnViewCreated
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            RC_TAKE_PICTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    formFillViewModel.photoFormFillModel?.also {
                        val imageFile = data?.getSerializableExtra(CameraActivity.RESULT_IMAGE_FILE) as File
                        val latitude  = data.getDoubleExtra(CameraActivity.RESULT_PHOTO_LATITUDE, 0.0)
                        val longitude = data.getDoubleExtra(CameraActivity.RESULT_PHOTO_LONGITUDE, 0.0)

                        d {"imagefile: $imageFile"}
                        d {"latitude: $latitude"}
                        d {"longitude: $longitude"}
                        formFillEvent.value = FormFillEvent.OnSuccessTakePicture(imageFile, latitude, longitude)
                    }
                }
            }
        }
    }

    override fun loadFormFill(formFillItems: List<FormFillModel>) {
        formFillItems.forEach {
            d { "-> item header: ${it.header.title}" }
            d { "item body: ${it.body.sections[0]}"}
        }
        recyclerAdapter.setItems(formFillItems.toMutableList())
    }

    override fun showLoadingData(loading: Loading) = showLoadingMessage(loading.title, loading.message)

    override fun dismissLoading() = hideLoading()

    override fun showError(t: Throwable, message: String?) {
        requireActivity().makeText("${message?: TERJADI_KESALAHAN}. ${DebugUtil.getReadableErrorMessage(t)}", Toast.LENGTH_LONG)
    }

    override fun uploadSuccess() {
        requireActivity().makeText("Upload data berhasil", Toast.LENGTH_LONG)
    }

    private val rvListener = mapOf(
        R.layout.item_form_fill to object : OnFormFillListener {
            override fun onCheckBoxChanged(
                checkBoxItems: MutableMap<String, Boolean>,
                position: Int
            ) {

            }

            override fun onTextChanged(text: String?, formFillModel: FormFillModel?) {
                d {"text changed -> text: $text, formFillModel: $formFillModel"}
                formFillEvent.value = FormFillEvent.OnTextChanged(text,  formFillModel)
            }

            override fun onTakePicture(formFillModel: FormFillModel?) {
                d {"take picture -> formFillModel: $formFillModel"}

                if (formFillModel == null) {
                    showError(NullPointerException(FORM_FILL_KOSONG))
                    return
                }

                if (formFillViewModel.args == null) {
                    showError(NullPointerException(GAGAL_MENGAMBIL_FOTO))
                    return
                }

                d {"schedule id: ${formFillViewModel.args!!.scheduleId}"}
                formFillViewModel.photoFormFillModel = formFillModel
                Intent(requireActivity(), CameraActivity::class.java).apply {
                    putExtra(CameraActivity.EXTRA_SCHEDULE_ID, formFillViewModel.args!!.scheduleId)
                    putExtra(CameraActivity.EXTRA_SITE_MONITOR_ID, formFillViewModel.args!!.siteMonitorId)
                    putExtra(CameraActivity.EXTRA_SITE_LATITUDE, formFillViewModel.args!!.siteLatitude)
                    putExtra(CameraActivity.EXTRA_SITE_LONGITUDE, formFillViewModel.args!!.siteLongitude)
                    startActivityForResult(this, RC_TAKE_PICTURE)
                }
            }

            override fun onItemClick(model: FormFillModel) {

            }
        }
    )
}
