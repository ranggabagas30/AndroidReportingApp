package com.domikado.bit.abstraction.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    private var items: ArrayList<AbstractBaseItemModel>,
    private val viewHolderTypeFactory: ViewHolderTypeFactory,
    private val listeners: Map<Int, IBaseRvListener<*>>?
): RecyclerView.Adapter<AbstractViewHolder<AbstractBaseItemModel>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder<AbstractBaseItemModel> {

        return viewHolderTypeFactory.createViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(viewType, parent,false),
            viewType,
            listeners
        ) as AbstractViewHolder<AbstractBaseItemModel>
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].type(viewHolderTypeFactory)

    override fun onBindViewHolder(
        holder: AbstractViewHolder<AbstractBaseItemModel>,
        position: Int
    ) {
        holder.bind(items[position])
    }

    fun setItems(items: ArrayList<AbstractBaseItemModel>) {
        this.items.clear()
        if (this.items.addAll(items))
            notifyDataSetChanged()
    }

    fun removeItem(position: Int, item: AbstractBaseItemModel) {
        if (this.items.remove(item))
            notifyItemRemoved(position)
    }

    fun changeItem(position: Int, item: AbstractBaseItemModel) {
        this.items[position] = item
        notifyItemChanged(position)
    }
}
