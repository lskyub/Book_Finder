package com.bookfinder.custom

import android.graphics.Rect
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookDecoration(val size: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val last = parent.adapter?.itemCount ?: -1
        val layoutManager = parent.layoutManager

        if (layoutManager is LinearLayoutManager) {
            if (layoutManager.orientation == LinearLayout.VERTICAL) {
                when (position) {
                    0 -> {
                        outRect.top = size
                        outRect.bottom = size / 2
                    }
                    last -> {
                        outRect.top = size / 2
                        outRect.bottom = size
                    }
                    else -> {
                        outRect.top = size / 2
                        outRect.bottom = size / 2
                    }
                }
            }
        }
    }
}