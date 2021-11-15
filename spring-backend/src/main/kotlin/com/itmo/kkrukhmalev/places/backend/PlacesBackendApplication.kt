package com.itmo.kkrukhmalev.places.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlacesBackendApplication

fun main(args: Array<String>) {
    runApplication<PlacesBackendApplication>(*args)
}
