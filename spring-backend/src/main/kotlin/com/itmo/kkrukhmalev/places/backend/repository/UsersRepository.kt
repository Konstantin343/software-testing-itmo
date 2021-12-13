package com.itmo.kkrukhmalev.places.backend.repository

import com.itmo.kkrukhmalev.places.backend.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : CrudRepository<User, Long> {
    fun findUserByLoginAndPassword(login: String, password: String): User

    fun findUserByLogin(login: String): User
}