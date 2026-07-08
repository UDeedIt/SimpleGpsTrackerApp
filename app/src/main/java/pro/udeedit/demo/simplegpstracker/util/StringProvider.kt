package pro.udeedit.demo.simplegpstracker.util

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Thin wrapper around [Context.getString] so ViewModels
 * don't depend directly on Android framework APIs.
 */
class StringProvider @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    fun get(@StringRes resId: Int): String = context.getString(resId)
}
