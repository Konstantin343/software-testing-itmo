package com.itmo.kkrukhmalev.places.backend.requestModel

data class AuthRequestModel(
    var login: String,
    var password: String,
    var redirectTo: String
)