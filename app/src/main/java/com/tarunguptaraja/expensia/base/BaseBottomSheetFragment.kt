package com.tarunguptaraja.expensia.base

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment : BottomSheetDialogFragment(), ContainerFragmentUtils {

    override var parentContainerFragment: ContainerFragment? = null
//    val profileViewModel: ProfileViewModel by store()

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