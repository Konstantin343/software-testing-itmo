package com.itmo.kkrukhmalev.places.tests.utils

import com.itmo.kkrukhmalev.places.tests.model.User
import kotlin.random.Random

object UserUtils {
    fun createRandomUser() =
        Random.nextInt().let {
            User(
                "login$it",
                "password$it"
            )
        }
}