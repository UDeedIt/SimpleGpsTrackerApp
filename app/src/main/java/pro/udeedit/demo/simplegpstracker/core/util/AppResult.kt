package pro.udeedit.demo.simplegpstracker.core.util

sealed class AppResult<out T> {
    data class Success<T>(val value: T) : AppResult<T>()
    data class Error(val throwable: Throwable) : AppResult<Nothing>()
}