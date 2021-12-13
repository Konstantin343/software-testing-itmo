package com.itmo.kkrukhmalev.places.backend.base

import com.itmo.kkrukhmalev.places.backend.domain.Place
import com.itmo.kkrukhmalev.places.backend.domain.PlacesList
import com.itmo.kkrukhmalev.places.backend.domain.User
import com.itmo.kkrukhmalev.places.backend.responseModel.ListResponseModel
import com.itmo.kkrukhmalev.places.backend.responseModel.PlaceResponseModel

object TestModels {
    fun placesListResponseModel(id: Long, userId: Long, placesCount: Long) = ListResponseModel(
        id = id,
        name = "listName$id",
        description = "listDescription$id",
        owner = "user$userId",
        places = (1..placesCount).map { placeResponseModel(it) }
    )

    fun placeResponseModel(id: Long) = PlaceResponseModel(
        id = id,
        name = "placeName$id",
        description = "placeDescription$id",
        type = "placeType$id",
        city = "placeCity$id",
        street = "placeStreet$id",
        number = "placeNumber$id"
    )

    fun placesList(id: Long, userId: Long, placesCount: Long) = PlacesList().apply {
        this.id = id
        name = "listName$id"
        description = "listDescription$id"
        owner = User().apply { login = "user$userId" }
        places = (1..placesCount).map { place(it) }
    }

    fun place(id: Long) = Place().apply {
        this.id = id
        name = "placeName$id"
        description = "placeDescription$id"
        type = "placeType$id"
        city = "placeCity$id"
        street = "placeStreet$id"
        number = "placeNumber$id"
    }

    fun user(id: Long, vararg addedLists: PlacesList) = User().apply {
        this.id = id
        login = "user$id"
        password = "password$id"
        this.addedLists = addedLists.toMutableSet()
    }
}