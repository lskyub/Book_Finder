package com.bookfinder.custom

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class BookScrollListener(private val lm: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    var previousTotalItemCount = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var firstVisibleItem: Int = 0

    var isLoadingMore = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = lm.itemCount
        firstVisibleItem = lm.findFirstVisibleItemPosition()

        if (isLoadingMore) {
            if (totalItemCount > previousTotalItemCount) {
                isLoadingMore = false
                previousTotalItemCount = totalItemCount
            } else if (previousTotalItemCount > totalItemCount) {
                isLoadingMore = false
                previousTotalItemCount = totalItemCount
            }
        }

        if (!isLoadingMore && (totalItemCount - visibleItemCount) <= (firstVisibleItem + (visibleItemCount * 3))) {
            onLoadMore()
            isLoadingMore = true
        }
    }

    abstract fun onLoadMore()
}