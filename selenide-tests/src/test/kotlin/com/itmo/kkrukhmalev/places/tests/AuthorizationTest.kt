package com.itmo.kkrukhmalev.places.tests

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.open
import com.itmo.kkrukhmalev.places.tests.utils.Steps
import com.itmo.kkrukhmalev.places.tests.utils.UserUtils
import org.junit.jupiter.api.Test

class AuthorizationTest {

    @Test
    fun a() {
        open("https://google.com")
    }

//    @Test
    fun testSignUpAndSignIn() {
        val user = UserUtils.createRandomUser()

        open("/sign-up")
        Steps.sendCredentials(user)
        Steps.checkHelloText(user)
        Steps.signOut(user)

        open("/sign-in")
        Steps.sendCredentials(user)
        Steps.checkHelloText(user)
        Steps.signOut(user)
    }
}