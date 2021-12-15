package com.itmo.kkrukhmalev.places.tests

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.open
import com.itmo.kkrukhmalev.places.tests.utils.PlacesUtils
import com.itmo.kkrukhmalev.places.tests.utils.Steps
import com.itmo.kkrukhmalev.places.tests.utils.UserUtils
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@Epic("Selenide Tests")
@Feature("Places Tests")
@DisplayName("Selenide Places Tests (E2E)")
class PlacesTest : BaseSelenideTest() {
    @Test
    @Story("Sign up and create places list")
    fun `Sign up and create places list`() {
        val user = UserUtils.createRandomUser()
        val placesList = PlacesUtils.createRandomPlacesList(user)

        open("/sign-up")
        Steps.sendCredentials(user)

        open("/add-places-list")
        Steps.createPlacesList(placesList)

        Steps.openFirstPlacesList()
        `$`("h3").shouldHave(text("Add Place"))
    }
}