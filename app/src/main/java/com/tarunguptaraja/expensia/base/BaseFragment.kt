package com.tarunguptaraja.expensia.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.tarunguptaraja.expensia.Expensia
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import kotlin.getValue

abstract class BaseFragment : Fragment(), ContainerFragmentUtils, KodeinAware {
    override val kodein by kodein(Expensia.appContext!!)
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