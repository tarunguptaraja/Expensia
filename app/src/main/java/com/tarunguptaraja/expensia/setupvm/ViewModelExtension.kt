package com.tarunguptaraja.expensia.setupvm

import androidx.lifecycle.ViewModelProvider
import com.androidisland.vita.VitaOwner
import com.androidisland.vita.vita
import com.google.gson.JsonElement
import com.tarunguptaraja.expensia.base.*


inline fun <reified VM : BaseViewModel, T> T.viewModel(args: JsonElement? = null): Lazy<VM> where T : BaseActivity {
    return lazy {
        ViewModelProvider(
            this,
            if (args == null) ViewModelProvider.NewInstanceFactory() else ViewModelWithArgumentsFactory(
                args
            )
        ).get(VM::class.java).apply {
                activity = this@viewModel
                context = this@viewModel
            }
    }
}

inline fun <reified VM : BaseViewModel, T> T.sharedViewModel(
    args: JsonElement? = null, fullActivity: Boolean = false
): Lazy<VM> where T : BaseFragment {
    return lazy {
        if (!fullActivity && parentContainerFragment != null) {
            parentContainerFragment?.run {
                ViewModelProvider(this).get(VM::class.java).apply {
                        activity = (this@run.activity as BaseActivity)
                        context = this@sharedViewModel.context
                    }
            } ?: throw Exception("Container Fragment")
        } else {
            activity?.run {
                ViewModelProvider(this).get(VM::class.java).apply {
                        activity = (this@run as BaseActivity)
                        context = this@sharedViewModel.context
                    }

            } ?: throw Exception("Invalid Activity")
        }
    }
}

inline fun <reified VM : BaseViewModel, T> T.viewModel(args: JsonElement? = null): Lazy<VM> where T : BaseFragment {
    return lazy {
        ViewModelProvider(
            this,
            if (args == null) ViewModelProvider.NewInstanceFactory() else ViewModelWithArgumentsFactory(
                args
            )
        ).get(VM::class.java).apply {
            activity = this@viewModel.activity as BaseActivity
            context = this@viewModel.context
        }
    }
}


inline fun <reified VM : BaseViewModel, T> T.sharedViewModel(
    args: JsonElement? = null, fullActivity: Boolean = false
): Lazy<VM> where T : BaseBottomSheetFragment {
    return lazy {
        if (!fullActivity && parentContainerFragment != null) {
            parentContainerFragment?.run {
                ViewModelProvider(this).get(VM::class.java).apply {
                        activity = (this@run.activity as BaseActivity)
                        context = this@sharedViewModel.context
                    }
            } ?: throw Exception("Container Fragment")
        } else {
            activity?.run {
                ViewModelProvider(this).get(VM::class.java).apply {
                        activity = (this@run as BaseActivity)
                        context = this@sharedViewModel.context
                    }

            } ?: throw Exception("Invalid Activity")
        }
    }
}

inline fun <reified VM : BaseViewModel, T> T.sharedViewModel(
    args: JsonElement? = null, fullActivity: Boolean = false
): Lazy<VM> where T : BaseDialogFragment {
    return lazy {
        if (!fullActivity && parentContainerFragment != null) {
            parentContainerFragment?.run {
                ViewModelProvider(this).get(VM::class.java).apply {
                        activity = (this@run.activity as BaseActivity)
                        context = this@sharedViewModel.context
                    }
            } ?: throw Exception("Container Fragment")
        } else {
            activity?.run {
                ViewModelProvider(this).get(VM::class.java).apply {
                        activity = (this@run as BaseActivity)
                        context = this@sharedViewModel.context
                    }

            } ?: throw Exception("Invalid Activity")
        }
    }
}

inline fun <reified VM : BaseViewModel, T> T.viewModel(args: JsonElement? = null): Lazy<VM> where T : BaseBottomSheetFragment {
    return lazy {
        ViewModelProvider(
            this,
            if (args == null) ViewModelProvider.NewInstanceFactory() else ViewModelWithArgumentsFactory(
                args
            )
        ).get(VM::class.java).apply {
            activity = this@viewModel.activity as BaseActivity
            context = this@viewModel.context
        }
    }
}

inline fun <reified VM : BaseViewModel, T> T.viewModel(args: JsonElement? = null): Lazy<VM> where T : BaseDialogFragment {
    return lazy {
        ViewModelProvider(
            this,
            if (args == null) ViewModelProvider.NewInstanceFactory() else ViewModelWithArgumentsFactory(
                args
            )
        ).get(VM::class.java).apply {
            activity = this@viewModel.activity as BaseActivity
            context = this@viewModel.context
        }
    }
}

inline fun <reified VM : BaseViewModel, T> T.store(): Lazy<VM> where T : Any {
    return lazy {
        vita.with(VitaOwner.Multiple(currentActivity!!)).getViewModel<VM>().apply {
                activity = currentActivity
                isStore = true
            }
    }
}

inline fun <reified VM : BaseViewModel, T> T.store(): Lazy<VM> where T : BaseActivity {
    return lazy {
        vita.with(VitaOwner.Multiple(this)).getViewModel<VM>().apply {
            activity = this@store
            isStore = true
        }
    }
}

inline fun <reified VM : BaseViewModel, T> T.store(): Lazy<VM> where T : BaseFragment {
    return lazy {
        vita.with(VitaOwner.Multiple(this.viewLifecycleOwner)).getViewModel<VM>().apply {
            activity = this@store.activity as BaseActivity
            isStore = true
        }
    }
}

inline fun <reified VM : BaseViewModel, T> T.store(): Lazy<VM> where T : BaseBottomSheetFragment {
    return lazy {
        vita.with(VitaOwner.Multiple(this.viewLifecycleOwner)).getViewModel<VM>().apply {
            activity = this@store.activity as BaseActivity
            isStore = true
        }
    }
}

inline fun <reified VM : BaseViewModel, T> T.store(): Lazy<VM> where T : BaseDialogFragment {
    return lazy {
        vita.with(VitaOwner.Multiple(this.viewLifecycleOwner)).getViewModel<VM>().apply {
            activity = this@store.activity as BaseActivity
            isStore = true
        }
    }
}