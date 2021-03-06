package com.itmo.kkrukhmalev.places.backend.service

import com.itmo.kkrukhmalev.places.backend.base.BaseServiceTest
import com.itmo.kkrukhmalev.places.backend.domain.User
import com.itmo.kkrukhmalev.places.backend.requestModel.AuthRequestModel
import com.itmo.kkrukhmalev.places.backend.utils.Sha256
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.qameta.allure.junit4.DisplayName
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@Epic("Service Tests (Backend)")
@Feature("Authorization Tests")
@DisplayName("Service Authorization Tests (Backend)")
class AuthorizationServiceTest : BaseServiceTest() {
    private val authorizationService = AuthorizationService(mockUsersRepo)

    @Test
    @Story("Sign up")
    @DisplayName("Sign up")
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
    @Story("Sign in")
    @DisplayName("Sign in")
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