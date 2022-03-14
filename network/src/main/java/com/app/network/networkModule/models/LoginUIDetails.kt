package com.app.network.networkModule.models

import android.text.InputType
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginUIDetails(
    @SerializedName("logo-url")
    var logoImage: String?,
    @SerializedName("heading-text")
    var headerText: String?,
    @SerializedName("uidata")
    var uiDataList: ArrayList<UIData>?
) : Serializable {
    class UIData(
        @SerializedName("uitype")
        var uiType: String?,
        @SerializedName("value")
        var value: String?,
        @SerializedName("key")
        var key: String?,
        @SerializedName("hint")
        var hint: String?
    ) : Serializable {


        fun getInputType(): Int {
            return when {
                key.equals("text_name") -> {
                    InputType.TYPE_CLASS_TEXT
                }
                key.equals("text_city") -> {
                    InputType.TYPE_CLASS_TEXT
                }
                key.equals("text_phone") -> {
                    InputType.TYPE_CLASS_NUMBER
                }
                else ->
                    InputType.TYPE_CLASS_TEXT

            }
        }
    }

}
