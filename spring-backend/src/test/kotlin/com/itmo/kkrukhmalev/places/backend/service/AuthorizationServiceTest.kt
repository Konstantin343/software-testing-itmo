package com.itmo.kkrukhmalev.places.backend.service

import com.itmo.kkrukhmalev.places.backend.base.BaseServiceTest
import com.itmo.kkrukhmalev.places.backend.domain.User
import com.itmo.kkrukhmalev.places.backend.requestModel.AuthRequestModel
import com.itmo.kkrukhmalev.places.backend.utils.Sha256
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify

class AuthorizationServiceTest : BaseServiceTest() {
    private val authorizationService = AuthorizationService(mockUsersRepo)

    @Test
    fun `sign up user`() {
        val hashPass = Sha256.passwordHash("password")
        lateinit var user: User

        // workaround for kotlin not nullable types
        `when`(mockUsersRepo.save(any()))
            .then { user = it.arguments[0] as User; return@then user }

        val newUser = authorizationService.signUp(
            AuthRequestModel("login", "password", "")
        )

        Assertions.assertEquals("login", newUser.login)
        Assertions.assertEquals(hashPass, newUser.password)

        verify(mockUsersRepo).save(user)
    }

    @Test
    fun `sign in user`() {
        val hashPass = Sha256.passwordHash("password")
        whenever(mockUsersRepo.findUserByLoginAndPassword("login", hashPass))
            .thenReturn(User().apply {
                id = 1
                login = "login"
                password = hashPass
            })

        val newUser = authorizationService.signIn(
            AuthRequestModel("login", "password", "")
        )

        Assertions.assertEquals("login", newUser.login)
        Assertions.assertEquals(hashPass, newUser.password)

        verify(mockUsersRepo).findUserByLoginAndPassword("login", hashPass)
    }
}