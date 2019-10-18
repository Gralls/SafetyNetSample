package pl.patryk.springer.safetynetsample.data.verification

import com.google.gson.annotations.SerializedName

data class Nonce(
    @SerializedName("value")
    val value: ByteArray
)
