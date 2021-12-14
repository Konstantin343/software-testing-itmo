package com.itmo.kkrukhmalev.places.tests

import com.itmo.kkrukhmalev.places.tests.utils.Steps
import com.itmo.kkrukhmalev.places.tests.utils.UserUtils
import org.junit.jupiter.api.Test

class AuthorizationTest : BaseSelenideTest() {
    @Test
    fun testSignUpAndSignIn() {
        val user = UserUtils.createRandomUser()

        openRelative("/sign-up")
        Steps.sendCredentials(user)
        Steps.checkHelloText(user)
        Steps.signOut(user)
        
        openRelative("/sign-in")
        Steps.sendCredentials(user)
        Steps.checkHelloText(user)
        Steps.signOut(user)
    }
}