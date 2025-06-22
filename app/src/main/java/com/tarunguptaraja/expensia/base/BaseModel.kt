package com.tarunguptaraja.expensia.base

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

/*inline fun <reified T : BaseModel> blankModel(): T {
    return jsonObject("success" to false, "description" to "").to()
}*/

open class BaseModel {
    var success: Boolean = false
    val errorCode: Int = 0

    @SerializedName("description_translations")
    val descriptionTranslations: JsonElement? = null
    val description: String = ""
        get() {
            return try {
                return if (descriptionTranslations != null) {
                    field
//                    descriptionTranslations[retrieveString(Constants.ACCEPT_LANGUAGE)].string
                } else {
                    field
                }
            } catch (ex: Exception) {
                field
            }
        }
}

data class AnyModel(val info: JsonElement) : BaseModel()