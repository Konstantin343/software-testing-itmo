package com.itmo.kkrukhmalev.places.backend.responseModel

data class ListsResponseModel(
    val lists: List<ListResponseModel>,
    val addedLists: List<ListResponseModel>
)

data class ListResponseModel(
    val id: Long,
    val name: String,
    val description: String,
    val owner: String,
    val places: List<PlaceResponseModel> = listOf()
)

data class PlaceResponseModel(
    val id: Long,
    val name: String,
    val description: String,
    val type: String,
    val city: String,
    val street: String,
    val number: String
)