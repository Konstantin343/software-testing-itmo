package com.itmo.kkrukhmalev.places.backend.service

import com.itmo.kkrukhmalev.places.backend.domain.User
import com.itmo.kkrukhmalev.places.backend.repository.UsersRepository
import com.itmo.kkrukhmalev.places.backend.requestModel.AuthRequestModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthorizationService {
    @Autowired
    lateinit var usersRepository: UsersRepository

    fun signUp(authRequestModel: AuthRequestModel) =
        usersRepository.save(User().apply {
            login = authRequestModel.login
            password = authRequestModel.password
        })

    fun signIn(authRequestModel: AuthRequestModel) =
        usersRepository.findUserByLoginAndPassword(
            authRequestModel.login,
            authRequestModel.password
        )
}