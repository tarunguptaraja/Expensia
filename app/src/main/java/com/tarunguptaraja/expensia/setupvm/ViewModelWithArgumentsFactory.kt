package com.tarunguptaraja.expensia.setupvm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonElement
import java.lang.reflect.Constructor

class ViewModelWithArgumentsFactory() : ViewModelProvider.NewInstanceFactory() {
    var args: JsonElement? = null
//    var socketIo: SocketIoInstance? = null

    constructor(args: JsonElement) : this() {
        this.args = args
    }

//    constructor(socketIo: SocketIoInstance) : this() {
//        this.socketIo = socketIo
//    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            return when {
//                socketIo != null -> {
//                    val constructor: Constructor<T> =
//                        modelClass.getDeclaredConstructor(SocketIoInstance::class.java)
//                    constructor.newInstance(socketIo)
//                }
                args == null -> {
                    val constructor: Constructor<T> = modelClass.getDeclaredConstructor()
                    constructor.newInstance()
                }

                else -> {
                    val constructor: Constructor<T> =
                        modelClass.getDeclaredConstructor(JsonElement::class.java)
                    constructor.newInstance(args)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ExceptionCause", e.cause.toString())
            throw e
        }
    }
}