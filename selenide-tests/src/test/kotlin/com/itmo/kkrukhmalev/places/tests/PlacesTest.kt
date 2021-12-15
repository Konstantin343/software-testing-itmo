package com.itmo.kkrukhmalev.places.tests

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.open
import com.itmo.kkrukhmalev.places.tests.utils.PlacesUtils
import com.itmo.kkrukhmalev.places.tests.utils.Steps
import com.itmo.kkrukhmalev.places.tests.utils.UserUtils
import org.junit.jupiter.api.Test

class PlacesTest {
    //    @Test
    fun testCreatePlacesList() {
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