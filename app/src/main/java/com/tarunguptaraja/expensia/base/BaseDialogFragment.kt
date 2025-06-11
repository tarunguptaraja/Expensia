package com.tarunguptaraja.expensia.base

import android.content.Context
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment(), ContainerFragmentUtils {

    override var parentContainerFragment: ContainerFragment? = null

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