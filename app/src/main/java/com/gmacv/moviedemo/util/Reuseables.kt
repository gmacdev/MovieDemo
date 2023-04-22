package com.gmacv.moviedemo.util

class Reuse {
    companion object {
        fun roundTheNumber(num: Float): String {
            return "%.2f".format(num)
        }

        fun getImagePrefixUrl(): String {
            return "https://image.tmdb.org/t/p/original/"
        }
    }
}