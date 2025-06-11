package com.tarunguptaraja.expensia.base

import android.content.Context
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    var activity: BaseActivity? = null
    var context: Context? = null

    // An identifier for global store.
    var isStore: Boolean = false
}