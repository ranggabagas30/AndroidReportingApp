package com.domikado.bit.abstraction.recyclerview

abstract class AbstractBaseItemModel {
    abstract fun type(viewHolderTypeFactory: ViewHolderTypeFactory): Int
}