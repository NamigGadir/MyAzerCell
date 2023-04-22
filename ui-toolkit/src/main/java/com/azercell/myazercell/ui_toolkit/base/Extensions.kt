package com.azercell.myazercell.ui_toolkit.base

import android.graphics.drawable.ColorDrawable
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addDivider(
    orientation: Int = LinearLayoutManager.VERTICAL,
    @Px insetStart: Int = 0,
    @Px insetEnd: Int = 0,
    @Px height: Int = 0,
    @ColorInt color: Int? = null,
) {
    ColorDrawable(color ?: 0x000000).let {
        val dividerItemDecoration = InsetItemDecoration(
            divider = it,
            leftInset = insetStart,
            rightInset = insetEnd,
            color = color,
            height = height
        ).apply {
            this.orientation = orientation
        }
        addItemDecoration(dividerItemDecoration)
    }
}