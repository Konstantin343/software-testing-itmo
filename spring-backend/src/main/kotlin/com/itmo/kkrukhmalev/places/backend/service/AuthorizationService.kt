package com.itmo.kkrukhmalev.places.backend.service

import com.itmo.kkrukhmalev.places.backend.domain.User
import com.itmo.kkrukhmalev.places.backend.repository.UsersRepository
import com.itmo.kkrukhmalev.places.backend.requestModel.AuthRequestModel
import com.itmo.kkrukhmalev.places.backend.utils.Sha256
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthorizationService(
    @Autowired
    var usersRepository: UsersRepository
) {

    fun signUp(authRequestModel: AuthRequestModel) =
        usersRepository.save(User().apply {
            login = authRequestModel.login
            password = Sha256.passwordHash(authRequestModel.password)
        })

    fun signIn(authRequestModel: AuthRequestModel) =
        usersRepository.findUserByLoginAndPassword(
            authRequestModel.login,
            Sha256.passwordHash(authRequestModel.password)
        )
}