package com.bookfinder.custom

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bookfinder.Utils.hideKeyboard

/**
 * RecyclerView 의 스크롤 상태를 받기 위해 사용한 리스너
 * 페이지네이션을 사용하기 위해 사용
 * 내부에서 현재 보이는 아이템의 숫자와 마지막 아이템을 체크 하여 다음 리스트를 불러오도록함
 */
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

        if (totalItemCount > 1) {
            recyclerView.hideKeyboard()
            if (isLoadingMore) {
                if (totalItemCount > previousTotalItemCount) {
                    isLoadingMore = false
                    previousTotalItemCount = totalItemCount
                } else if (previousTotalItemCount > totalItemCount) {
                    isLoadingMore = false
                    previousTotalItemCount = totalItemCount
                }
            }

            if (!isLoadingMore && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleItemCount)) {
                onLoadMore()
                isLoadingMore = true
            }
        }
    }

    abstract fun onLoadMore()
}