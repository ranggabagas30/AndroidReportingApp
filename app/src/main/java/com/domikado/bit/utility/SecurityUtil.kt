package com.domikado.bit.utility

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object SecurityUtil {

    fun getSHA256bytes(originalString: String): ByteArray {
        val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
        return digest.digest(
            originalString.toByteArray(StandardCharsets.UTF_8)
        )
    }

    fun bytesToHex(hash: ByteArray): String {
        val hexString = StringBuffer()
        for (i in hash.indices) {
            val hex = Integer.toHexString(0xff and hash[i].toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }
        return hexString.toString()
    }
}