package com.itmo.kkrukhmalev.places.tests.utils

import com.itmo.kkrukhmalev.places.tests.model.PlacesList
import com.itmo.kkrukhmalev.places.tests.model.User
import kotlin.random.Random

object PlacesUtils {
    fun createRandomPlacesList(user: User) =
        Random.nextInt().let { 
            PlacesList(
                "name$it",
                "description$it",
                user.login
            )
        }
}