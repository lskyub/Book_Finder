package com.bookfinder.custom

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView 의 스크롤 상태를 받기 위해 사용한 리스너
 * 페이지네이션을 사용하기 위해 사용
 * 내부에서 현재 보이는 아이템의 숫자와 마지막 아이템을 체크 하여 다음 리스트를 불러오도록함
 * 마지막 데이터에서 데이터를 불러오면 스크롤시 끊어지는 느낌을 제거하기 위해 마지막이 아닌
 * 현재 보이는 위치가 (마지막 위치 * 3) 영역에서 미리 데이터를 불러오도록 함
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