package com.domikado.bit.ui.screen.formfill


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
import com.domikado.bit.domain.domainmodel.Operator
import com.domikado.bit.ui.common.camera.CameraActivity
import com.domikado.bit.ui.screen.formfill.recyclerview.FormFillModel
import com.domikado.bit.ui.screen.formfill.recyclerview.OnFormFillListener
import com.domikado.bit.utility.makeText
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.google.gson.Gson
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
        formFillViewModel.siteMonitorId = args.siteMonitorId
        formFillViewModel.operator = Gson().fromJson(args.operator, Operator::class.java)

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

    override fun onStart() {
        super.onStart()
        println("on start")
        //formFillEvent.value = FormFillEvent.OnStart
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            RC_TAKE_PICTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    formFillViewModel.photoFormFillModel?.also {
                        val imageFile = data?.getSerializableExtra(CameraActivity.RESULT_IMAGE_FILE) as File
                        d {"imagefile: $imageFile"}
                        formFillEvent.value = FormFillEvent.OnSuccessTakePicture(imageFile)
                    }
                }
            }
        }
    }

    override fun loadFormFill(formFillItems: List<FormFillModel>) {
        println("refresh data: $formFillItems")
        recyclerAdapter.setItems(formFillItems.toMutableList())
    }

    override fun showLoadingData(loading: Loading) = showLoadingMessage(loading.title, loading.message)

    override fun dismissLoading() = hideLoading()

    override fun showError(t: Throwable, message: String?) {
        message?.also {
            e(t) { message }
            requireActivity().makeText("$it: ${t.message}", Toast.LENGTH_LONG)
        }
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
                    requireActivity().makeText("Tidak dapat mengambil gambar. Form fill model item ini kosong", Toast.LENGTH_LONG)
                    return
                }

                formFillViewModel.photoFormFillModel = formFillModel
                Intent(requireActivity(), CameraActivity::class.java).also {
                    startActivityForResult(it, RC_TAKE_PICTURE)
                }
            }

            override fun onItemClick(model: FormFillModel) {

            }
        }
    )
}
