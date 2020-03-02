package com.domikado.bit.abstraction.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractViewHolder<in Model>(itemView: View) : RecyclerView.ViewHolder(itemView){
    abstract fun bind(model: Model)
}