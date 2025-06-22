package com.tarunguptaraja.expensia.extensions

import android.app.Activity
import androidx.core.content.edit
import com.tarunguptaraja.expensia.Expensia

fun putString(key: String, value: String) {
    val sp = Expensia.sharedPreferences
    sp.edit { putString(key, value) }
}

fun putResponse(key: String, value: String?) {
    val sp = Expensia.sharedPreferences
    sp.edit { putString(key, value) }
}

fun putModel(key: String, value: Any) {
    val json = gson.toJson(value)
    val sp = Expensia.sharedPreferences
    sp.edit { putString(key, json) }
}

fun putInt(key: String, value: Int) {
    val sp = Expensia.sharedPreferences
    sp.edit { putInt(key, value) }
}

fun putLong(key: String, value: Long) {
    val sp = Expensia.sharedPreferences
    sp.edit { putLong(key, value) }
}

fun putBoolean(key: String, value: Boolean) {
    val sp = Expensia.sharedPreferences
    sp.edit { putBoolean(key, value) }
}

fun retrieveString(key: String, default: String = ""): String {
    val sp = Expensia.sharedPreferences
    return sp.getString(key, default)!!
}

fun retrieveResponse(key: String): String? {
    val sp = Expensia.sharedPreferences
    return sp.getString(key, "")
}

fun putPermanentModel(key: String, value: Any) {
    val json = gson.toJson(value)
    val sp = Expensia.sharedPreferencesPermanent
    sp.edit { putString(key, json) }
}

fun retrieveInt(key: String, default: Int = -1): Int {
    val sp = Expensia.sharedPreferences
    return sp.getInt(key, default)
}

fun retrieveLong(key: String): Long {
    val sp = Expensia.sharedPreferences
    return sp.getLong(key, 0)
}

fun retrieveBoolean(key: String, default: Boolean = false): Boolean {
    val sp = Expensia.sharedPreferences
    return sp.getBoolean(key, default)
}

fun retrievePermanentString(key: String, default: String = ""): String {
    val sp = Expensia.sharedPreferencesPermanent
    return sp.getString(key, default)!!
}

fun putPermanentString(key: String, value: String) {
    val sp = Expensia.sharedPreferencesPermanent
    sp.edit { putString(key, value) }
}

fun removePermanent(key: String) {
    val sp = Expensia.sharedPreferencesPermanent
    sp.edit { remove(key) }
}

fun putPermanentBoolean(key: String, value: Boolean) {
    val sp = Expensia.sharedPreferencesPermanent
    sp.edit { putBoolean(key, value) }
}

fun retrievePermanentBoolean(key: String, default: Boolean = false): Boolean {
    val sp = Expensia.sharedPreferencesPermanent
    return sp.getBoolean(key, default)
}

fun putPermanentInt(key: String, value: Int) {
    val sp = Expensia.sharedPreferencesPermanent
    sp.edit { putInt(key, value) }
}

fun retrievePermanentInt(key: String, default: Int = 0): Int {
    val sp = Expensia.sharedPreferencesPermanent
    return sp.getInt(key, default)
}

fun Activity.removeSharedPrefByKey(key: String) {
    val sp = Expensia.sharedPreferences
    sp.edit { remove(key) }
}

fun putStringSet(key: String, value: HashSet<String>) {
    val sp = Expensia.sharedPreferences
    sp.edit { putStringSet(key, value) }
}

fun retrieveStringSet(key: String, default: HashSet<String>): MutableSet<String>? {
    val sp = Expensia.sharedPreferences
    return sp.getStringSet(key, default)
}