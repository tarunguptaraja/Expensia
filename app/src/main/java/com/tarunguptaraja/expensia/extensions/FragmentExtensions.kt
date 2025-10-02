package com.tarunguptaraja.expensia.extensions

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun AppCompatActivity.replaceFragmentIfNoFragment(
    fragment: Fragment, frameId: Int, tag: String = fragment.javaClass.simpleName
) {
    Log.d("$$$ launchfragment", tag)
    if (supportFragmentManager.fragments.isEmpty()) {
        supportFragmentManager.inTransaction {
            val oldFragment = supportFragmentManager.findFragmentByTag(tag)
            supportFragmentManager.fragments.forEach {
                if (it != fragment && it.tag != "force") hide(
                    it
                )
            }
            if (oldFragment == null) {
                add(frameId, fragment, tag)
            } else {
                if (fragment.isAdded) show(fragment)
                else add(frameId, fragment, tag)
            }
        }
    }
}

fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    frameId: Int,
    force: Boolean = false,
    tag: String = fragment.javaClass.simpleName,
    addToBackStack: Boolean = false
) {
    Log.d("$$$ launchfragment", tag)
    if (force) {
        supportFragmentManager.inTransaction { replace(frameId, fragment, "force") }
    } else {
        supportFragmentManager.inTransaction {
            try {
                if (addToBackStack) {
                    addToBackStack(fragment.javaClass.simpleName)
                }
                val oldFragment = supportFragmentManager.findFragmentByTag(tag)
                supportFragmentManager.fragments.forEach {
                    if (it != fragment && it.tag != "force") hide(
                        it
                    )
                }
                Log.d("replaceFragment", "${tag}")
                Log.d("replaceFragment", "${oldFragment}")
                if (oldFragment == null) {
                    add(frameId, fragment, tag)
                } else {
                    if (fragment.isAdded) show(fragment)
                    else add(frameId, fragment, tag)
                }
            } catch (ex: IllegalStateException) {
                ex.printStackTrace()
                show(fragment)
            }
        }
    }
}

fun Fragment.clearFragments() {
    childFragmentManager.fragments.forEach {
        childFragmentManager.inTransaction { remove(it) }
        childFragmentManager.popBackStackImmediate()
    }
    Log.d("childFragments", childFragmentManager.fragments.size.toString())
}

fun Fragment.replaceChildFragment(
    fragment: Fragment,
    frameId: Int,
    force: Boolean = false,
    tag: String = fragment.javaClass.simpleName
) {
    Log.d("$$$ launchfragment", tag)
    if (force) {
        childFragmentManager.inTransaction { replace(frameId, fragment, "force") }
    } else {
        childFragmentManager.inTransaction {
            val oldFragment = childFragmentManager.findFragmentByTag(tag)
            childFragmentManager.fragments.forEach {
                if (it != fragment && it.tag != "force") hide(
                    it
                )
            }
            if (oldFragment == null) {
                add(frameId, fragment, tag)
            } else {
                if (fragment.isAdded) show(fragment)
                else add(frameId, fragment, tag)
            }
        }
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commitAllowingStateLoss()
}

fun Fragment.isActive(): Boolean = isAdded && !(isRemoving || isDetached)

fun FragmentManager.removeAllFragments() {
    fragments.forEach {
        beginTransaction().remove(it).commitNow()
    }
}