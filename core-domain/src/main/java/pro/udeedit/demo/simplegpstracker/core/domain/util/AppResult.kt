package pro.udeedit.demo.simplegpstracker.core.domain.util

/**
 * Simple result wrapper used to represent success or failure of an operation.
 *
 * @param T Type of the successful result value.
 */
sealed class AppResult<out T> {

    /**
     * Represents a successful result containing [value].
     */
    data class Success<T>(val value: T) : AppResult<T>()

    /**
     * Represents a failed result containing the underlying [throwable].
     */
    data class Error(val throwable: Throwable) : AppResult<Nothing>()
}
