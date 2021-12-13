package com.itmo.kkrukhmalev.places.backend.repository

import com.itmo.kkrukhmalev.places.backend.domain.PlacesList
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PlacesListsRepository : CrudRepository<PlacesList, Long> {
    fun findAllByOwnerLogin(login: String): List<PlacesList>

    fun findPlacesListById(id: Long): PlacesList
}