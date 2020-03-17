package com.domikado.bit.utility

import android.content.Context
import android.util.TypedValue

object UIUtil {
    fun dpToPx(context: Context, dp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp, context.resources.displayMetrics)
    fun pxToDp(context: Context, px: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.resources.displayMetrics)
}