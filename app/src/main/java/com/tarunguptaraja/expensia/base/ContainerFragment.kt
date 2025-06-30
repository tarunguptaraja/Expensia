package com.tarunguptaraja.expensia.base

/**
 * Passes parentContainerFragment reference to all the deeply nested fragment.
 * Basically it is used to scope the sharedViewModel within the ContainerFragment.
 */
abstract class ContainerFragment: BaseFragment() {

}

interface ContainerFragmentUtils {
    var parentContainerFragment: ContainerFragment?
}