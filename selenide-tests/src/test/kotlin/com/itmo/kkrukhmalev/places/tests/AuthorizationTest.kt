package com.itmo.kkrukhmalev.places.tests

import com.codeborne.selenide.Selenide.open
import com.itmo.kkrukhmalev.places.tests.utils.BrowserDisplayNameGenerator
import com.itmo.kkrukhmalev.places.tests.utils.Steps
import com.itmo.kkrukhmalev.places.tests.utils.UserUtils
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DisplayNameGeneration
import org.junit.jupiter.api.Test

@Epic("Selenide Tests")
@Feature("Authorization Tests")
@DisplayName("Selenide Authorization Tests (E2E)")

@DisplayNameGeneration(BrowserDisplayNameGenerator::class)
class AuthorizationTest {
    @Test
    @Story("Sign up and sign in")
    fun `Sign up and sign in`() {
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