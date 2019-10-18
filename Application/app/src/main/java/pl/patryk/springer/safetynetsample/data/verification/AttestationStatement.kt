package pl.patryk.springer.safetynetsample.data.verification

import com.google.gson.annotations.SerializedName

data class AttestationStatement(
    @SerializedName("nonce")
    val nonce: String,
    @SerializedName("timestampMs")
    val timestampMs: Long,
    @SerializedName("apkPackageName")
    val apkPackageName: String,
    @SerializedName("apkDigestSha256")
    val apkDigestSha256: String,
    @SerializedName("ctsProfileMatch")
    val ctsProfileMatch: Boolean,
    @SerializedName("apkCertificateDigestSha256")
    val apkCertificateDigestSha256: List<String>,
    @SerializedName("basicIntegrity")
    val basicIntegrity: Boolean
)

