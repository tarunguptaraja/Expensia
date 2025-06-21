package com.tarunguptaraja.expensia.extensions

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject


val gson = Gson()
val gsonPretty = GsonBuilder().setPrettyPrinting().create()

fun Any.toJson() = gson.toJson(this)
fun Any.toPrettyJson() = gsonPretty.toJson(this)

fun Any.toJsonTree() = gson.toJsonTree(this)

fun Bundle.toJson(): JSONObject {
    val json = json()
    val keys = keySet()
    for (key in keys) {
        try {
            json.put(key, get(key))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    return json
}

fun json(vararg entries: Pair<String, Any>): JSONObject {
    val jsonObject = JSONObject()
    entries.forEach {
        jsonObject.put(it.first, it.second)
    }
    return jsonObject
}
