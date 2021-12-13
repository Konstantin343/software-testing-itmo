package com.itmo.kkrukhmalev.places.backend.utils

import java.security.MessageDigest

object Sha256 {
    private val sha256 = MessageDigest.getInstance("SHA-256")

    fun passwordHash(password: String) =
        sha256.digest(password.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }
}