package com.tarunguptaraja.expensia.base

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tarunguptaraja.expensia.Expensia
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import kotlin.getValue

abstract class BaseBottomSheetFragment : BottomSheetDialogFragment(), ContainerFragmentUtils, KodeinAware {

    override var parentContainerFragment: ContainerFragment? = null
    override val kodein by kodein(Expensia.appContext!!)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment

        if (parent != null && parent is ContainerFragmentUtils) {
            if (parent is ContainerFragment) {
                parentContainerFragment = parent
            } else {
                parentContainerFragment = parent.parentContainerFragment
            }
        }
    }
}