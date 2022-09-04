package com.bookfinder.constants

object Constants {
    /**
     * Google Book api를 사용하기 위한 Key 정보
     */
    val GOOGLE_KEY = "AIzaSyBLFtwgLTMrBmk5WZjWosYlUJiDZviSAqw"

    /**
     * 네트워크 타임 아웃 값 정의
     */
    object NetWork {
        const val CONNECT_TIMEOUT: Long = 30
        const val WRITE_TIMEOUT: Long = 30
        const val READ_TIMEOUT: Long = 30
    }
}