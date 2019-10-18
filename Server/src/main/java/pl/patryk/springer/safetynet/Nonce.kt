package pl.patryk.springer.safetynet

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.security.SecureRandom

data class Nonce(val value: ByteArray) {
    companion object {
        fun newInstance(data: String, random: SecureRandom): Nonce? {
            val byteStream = ByteArrayOutputStream()
            val bytes = ByteArray(24)
            random.nextBytes(bytes)
            return try {
                byteStream.write(bytes)
                byteStream.write(data.toByteArray())
                Nonce(byteStream.toByteArray())
            } catch (e: IOException) {
                null
            }
        }
    }
}