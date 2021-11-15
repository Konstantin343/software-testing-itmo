package com.itmo.kkrukhmalev.places.backend.requestModel

class AddPlaceRequestModel {
    var listId: Long = 0
    lateinit var name: String
    lateinit var description: String
    lateinit var type: String
    lateinit var city: String
    lateinit var street: String
    lateinit var number: String
}