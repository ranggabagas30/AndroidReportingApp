package com.domikado.bit.abstraction.recyclerview

interface IBaseRvListener<in Model> {
    fun onItemClick(model: Model)
}