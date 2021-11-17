package com.itmo.kkrukhmalev.places.backend.requestModel

data class AddPlaceRequestModel(
    var listId: Long = 0,
    var name: String,
    var description: String,
    var type: String,
    var city: String,
    var street: String,
    var number: String
)