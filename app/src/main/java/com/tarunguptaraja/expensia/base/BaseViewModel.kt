package com.tarunguptaraja.expensia.base

import android.content.Context
import androidx.lifecycle.ViewModel
import com.tarunguptaraja.expensia.Expensia
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import kotlin.getValue

abstract class BaseViewModel : ViewModel(), KodeinAware {
    override val kodein by kodein(Expensia.appContext!!)
    var activity: BaseActivity? = null
    var context: Context? = null

    // An identifier for global store.
    var isStore: Boolean = false
}