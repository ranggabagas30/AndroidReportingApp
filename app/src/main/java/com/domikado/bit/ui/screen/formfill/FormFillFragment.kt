package com.domikado.bit.ui.screen.formfill


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.domikado.bit.R
import com.domikado.bit.abstraction.base.BaseFragment
import com.domikado.bit.abstraction.recyclerview.AbstractBaseItemModel
import com.domikado.bit.abstraction.recyclerview.RecyclerAdapter
import com.domikado.bit.abstraction.recyclerview.ViewHolderTypeFactoryImpl
import com.domikado.bit.ui.screen.formfill.recyclerview.FormFillModel
import com.domikado.bit.ui.screen.formfill.recyclerview.OnFormFillListener
import kotlinx.android.synthetic.main.fragment_form.*

/**
 * A simple [Fragment] subclass.
 */
class FormFillFragment : BaseFragment(), IFormFillContract.View {

    private val RC_TAKE_PICTURE = 1
    private var formFillId: Int? = null
    private var position: Int? = null

    private lateinit var recyclerAdapter: RecyclerAdapter

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
    }

    private val formFillEvent by lazy {
        MutableLiveData<FormFillEvent>()
    }

    private val formFillViewModel: FormFillViewModel by viewModels()

    override fun setObserver(observer: Observer<FormFillEvent>) = formFillEvent.observe(viewLifecycleOwner, observer)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        formFillViewModel.buildFormFillLogic(this)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerAdapter = RecyclerAdapter(
            arrayListOf<FormFillModel>() as ArrayList<AbstractBaseItemModel>,
            ViewHolderTypeFactoryImpl(),
            mapOf(
                R.layout.item_form_fill to object : OnFormFillListener {
                    override fun onCheckBoxChanged(
                        checkBoxItems: MutableMap<String, Boolean>,
                        position: Int
                    ) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onTextChanged(formFillId: Int, s: String?, position: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onTakePicture(formFillId: Int, position: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onItemClick(model: FormFillModel) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }
            ))

        formfill_rv.layoutManager = linearLayoutManager
        formfill_rv.adapter = recyclerAdapter
    }

    override fun onStart() {
        super.onStart()
        formFillEvent.value = FormFillEvent.OnStart
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            RC_TAKE_PICTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    // save picture
                    // reload list
                    position?.also {

                        //recyclerAdapter.changeItem(it, )
                    }
                }
            }
        }
    }

    override fun loadFormFill(formFillItems: List<FormFillModel>) =
        recyclerAdapter.setItems(formFillItems.toMutableList() as ArrayList<AbstractBaseItemModel>)
}
