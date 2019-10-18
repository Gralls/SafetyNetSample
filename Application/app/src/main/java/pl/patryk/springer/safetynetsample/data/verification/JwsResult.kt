package pl.patryk.springer.safetynetsample.data.verification

import com.google.gson.annotations.SerializedName

data class JwsResult(
    @SerializedName("result")
    val result: String,
    @SerializedName("verifyOnline")
    val verifyOnline: Boolean = false
)