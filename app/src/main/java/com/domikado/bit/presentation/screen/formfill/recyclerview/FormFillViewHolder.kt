package com.domikado.bit.presentation.screen.formfill.recyclerview

import android.text.InputType
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.domikado.bit.R
import com.domikado.bit.abstraction.recyclerview.AbstractViewHolder
import com.domikado.bit.external.glide.GlideApp
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

class FormFillViewHolder(itemView: View, private val onFormFillListener: OnFormFillListener): AbstractViewHolder<FormFillModel>(itemView) {

    private var model: FormFillModel? = null

    override fun bind(model: FormFillModel) {
        println("bind form fill model with id: ${model.id}")
        this.model = model

        val formHeaderView = itemView.findViewById(R.id.form_header) as RelativeLayout
        val formBodyView = itemView.findViewById(R.id.form_body) as LinearLayout
        setHeader(formHeaderView)
        setBody(formBodyView)
    }

    private fun setHeader(formHeaderView: RelativeLayout){
        val titleView = formHeaderView.findViewById(R.id.form_header_title) as AppCompatTextView
        titleView.text = model?.header?.title

        val uploadStatusView = formHeaderView.findViewById(R.id.form_header_upload_status) as AppCompatTextView
        uploadStatusView.text = model?.header?.uploadStatus
    }

    private fun setBody(formBodyView: LinearLayout) {
        model?.body?.sections?.also {
            for (section in it) {
                when(section) {
                    is SectionModel.CheckBoxModel -> {
                        val view: View? = formBodyView.findViewById(section.id)
                        if (view == null) addCheckBox(formBodyView, section)
                    }
                    is SectionModel.RadioModel -> {
                        val view: View? = formBodyView.findViewById(section.id)
                        if (view == null) addRadio(formBodyView, section)
                    }
                    is SectionModel.EditTextModel -> {
                        val view: View? = formBodyView.findViewById(section.id)
                        if (view == null) addEditText(formBodyView, section)
                    }
                    is SectionModel.PhotoLayoutModel -> {
                        val view: View? = formBodyView.findViewById(section.id)
                        if (view == null) addPhotoLayout(formBodyView, section) // if view with section id is not found (not added before), than add it to the parent
                        else getPhotolayout(formBodyView, section)
                    }
                }
            }
        }

    }

    private fun addCheckBox(formBodyView: LinearLayout, section: SectionModel.CheckBoxModel) {
        val formCheckBoxView = LayoutInflater.from(formBodyView.context).inflate(R.layout.item_form_checkbox, formBodyView, true) as LinearLayout

        // set id
        formCheckBoxView.id = section.id

        // define section column
        setColumn(formCheckBoxView, R.id.form_checkbox_column, section.column)

        // define section operator
        setOperator(formCheckBoxView, R.id.form_checkbox_operator, section.operator)

        // define section title
        setTitle(formCheckBoxView, R.id.form_checkbox_title, section.title)

        // define section mandatory
        setMandatory(formCheckBoxView, R.id.form_checkbox_mandatory, section.isMandatory)

        for (checkBox in section.items) {
            val checkBoxView = MaterialCheckBox(formBodyView.context)
            formCheckBoxView.addView(
                checkBoxView.apply {
                    text = checkBox.key
                    check(checkBox.value)
                    tag = checkBox.key
                    setOnCheckedChangeListener { buttonView, isChecked ->
                        // update checkbox in database
                        //section.items[checkBox.key] = isChecked
                        //onFormFillListener.onCheckBoxChangedListener(section.items)
                    }
                }
            )
        }
        formBodyView.addView(formCheckBoxView)
    }

    private fun addRadio(formBodyView: LinearLayout, section: SectionModel.RadioModel) {
        val formRadioView = LayoutInflater.from(formBodyView.context).inflate(R.layout.item_form_radio, formBodyView, true) as LinearLayout

        // set id
        formRadioView.id = section.id

        // define section column
        setColumn(formRadioView, R.id.form_radio_column, section.column)

        // define section operator
        setOperator(formRadioView, R.id.form_radio_operator, section.operator)

        // define section title
        setTitle(formRadioView, R.id.form_radio_title, section.title)

        // define section mandatory
        setMandatory(formRadioView, R.id.form_radio_mandatory, section.isMandatory)

        val radioGroupView = formRadioView.findViewById(R.id.form_radio_group) as RadioGroup
        for (radioButton in section.radioValue.items) {
            val radioButtonView = RadioButton(formBodyView.context)
            radioGroupView.addView(
                radioButtonView.apply {
                    text = radioButton.value.label
                    check(radioButton.value.isChecked)
                    tag = radioButton.value.label
                }
            )
        }
        formBodyView.addView(formRadioView)
    }

    private fun addEditText(formBodyView: LinearLayout, section: SectionModel.EditTextModel) {
        val formEditTextView = LayoutInflater.from(formBodyView.context).inflate(R.layout.item_form_edittext, formBodyView, true) as LinearLayout

        // set id
        formEditTextView.id = section.id

        // define section column
        setColumn(formEditTextView, R.id.form_radio_column, section.column)

        // define section operator
        setOperator(formEditTextView, R.id.form_radio_operator, section.operator)

        // define section title
        setTitle(formEditTextView, R.id.form_radio_title, section.title)

        // define section mandatory
        setMandatory(formEditTextView, R.id.form_radio_mandatory, section.isMandatory)

        val editTextView = formEditTextView.findViewById(R.id.form_edittext_textfield) as AppCompatEditText
        editTextView.apply {
            setText(section.defaultValue)
            hint = section.hint
            inputType = when(section.inputType) {
                0 -> InputType.TYPE_CLASS_TEXT
                1 -> InputType.TYPE_CLASS_NUMBER
                else -> InputType.TYPE_CLASS_TEXT
            }
            addTextChangedListener {
                //onFormFillListener.onTextChanged(formFillId, it.toString())
            }
        }

        val descriptionView = formEditTextView.findViewById(R.id.form_edittext_description) as AppCompatEditText
        descriptionView.setText(section.description)

        formBodyView.addView(formEditTextView)
    }

    private fun addPhotoLayout(formBodyView: LinearLayout, section: SectionModel.PhotoLayoutModel) {
        println("add photo layout: $section")
        val formPhotoLayoutView = LayoutInflater.from(formBodyView.context).inflate(R.layout.item_form_photolayout, formBodyView, false) as ConstraintLayout

        // set id
        formPhotoLayoutView.id = section.id

        // define section column
        setColumn(formPhotoLayoutView, R.id.form_photolayout_column, section.column)

        // define section operator
        setOperator(formPhotoLayoutView, R.id.form_photolayout_operator, section.operator)

        // define section title
        setTitle(formPhotoLayoutView, R.id.form_photolayout_title, section.title)

        // define section mandatory
        setMandatory(formPhotoLayoutView, R.id.form_photolayout_mandatory, section.isMandatory)

        // set no picture view
        val noPictureView = formPhotoLayoutView.findViewById(R.id.form_photolayout_no_picture) as AppCompatImageView
        noPictureView.visibility = if (TextUtils.isEmpty(section.photoPath)) View.VISIBLE else View.GONE

        // set picture view
        val pictureView = formPhotoLayoutView.findViewById(R.id.form_photolayout_picture) as AppCompatImageView
        pictureView.apply {
            println("load image into pictureview")
            if (!TextUtils.isEmpty(section.photoPath)){
                visibility = View.VISIBLE
                GlideApp.with(formBodyView.context)
                    .load(File(section.photoPath!!))
                    .into(pictureView)
            } else {
                visibility = View.GONE
            }
        }

        val takePictureView = formPhotoLayoutView.findViewById(R.id.form_photolayout_btn_camera) as FloatingActionButton
        takePictureView.setOnClickListener {
            onFormFillListener.onTakePicture(model)
        }

        val remarkView = formPhotoLayoutView.findViewById(R.id.form_photolayout_tf_remark) as AppCompatEditText
        remarkView.apply {
            setText(section.remark)
            addTextChangedListener(onTextChanged = {text, start, count, after ->
                onFormFillListener.onTextChanged(text.toString(), model)
            })
        }
        formBodyView.addView(formPhotoLayoutView)
    }

    private fun getPhotolayout(formBodyView: LinearLayout, section: SectionModel.PhotoLayoutModel) {
        val formPhotoLayoutView = formBodyView.findViewById(section.id) as ConstraintLayout

        // define section column
        setColumn(formPhotoLayoutView, R.id.form_photolayout_column, section.column)

        // define section operator
        setOperator(formPhotoLayoutView, R.id.form_photolayout_operator, section.operator)

        // define section title
        setTitle(formPhotoLayoutView, R.id.form_photolayout_title, section.title)

        // define section mandatory
        setMandatory(formPhotoLayoutView, R.id.form_photolayout_mandatory, section.isMandatory)

        // set no picture view
        val noPictureView = formPhotoLayoutView.findViewById(R.id.form_photolayout_no_picture) as AppCompatImageView
        noPictureView.visibility = if (TextUtils.isEmpty(section.photoPath)) View.VISIBLE else View.GONE

        // set picture view
        val pictureView = formPhotoLayoutView.findViewById(R.id.form_photolayout_picture) as AppCompatImageView
        pictureView.apply {
            println("load image into pictureview")
            if (!TextUtils.isEmpty(section.photoPath)){
                visibility = View.VISIBLE
                GlideApp.with(formBodyView.context)
                    .load(File(section.photoPath!!))
                    .into(pictureView)
            } else {
                visibility = View.GONE
            }
        }

        val remarkView = formPhotoLayoutView.findViewById(R.id.form_photolayout_tf_remark) as AppCompatEditText
        remarkView.apply {
            setText(section.remark)
        }
    }

    /* common functions */
    private fun setColumn(formSectionView: ViewGroup, columnLayoutResId: Int, column: String?) {
        val columnView = formSectionView.findViewById(columnLayoutResId) as AppCompatTextView
        if (TextUtils.isEmpty(column)) {
            columnView.visibility = View.GONE
        } else {
            columnView.visibility = View.VISIBLE
            columnView.text = column
        }
    }

    private fun setOperator(formSectionView: ViewGroup, operatorLayoutResId: Int, operator: String?) {
        val operatorView = formSectionView.findViewById(operatorLayoutResId) as AppCompatTextView
        operatorView.apply {
            visibility = if (TextUtils.isEmpty(operator)) View.GONE else View.VISIBLE
            text = "Operator : $operator"
        }
    }

    private fun setTitle(formSectionView: ViewGroup, titleLayoutResId: Int, title: String?){
        val titleView = formSectionView.findViewById(titleLayoutResId) as AppCompatTextView
        titleView.text = title
    }

    private fun setMandatory(formSectionView: ViewGroup, mandatoryLayoutResId: Int, isMandatory: Boolean) {
        val mandatoryView = formSectionView.findViewById(mandatoryLayoutResId) as AppCompatTextView
        mandatoryView.visibility = if (isMandatory) { View.VISIBLE } else View.GONE
    }
}