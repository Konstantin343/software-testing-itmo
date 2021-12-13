package com.itmo.kkrukhmalev.places.backend.repository

import com.itmo.kkrukhmalev.places.backend.domain.Place
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PlacesRepository : CrudRepository<Place, Long>