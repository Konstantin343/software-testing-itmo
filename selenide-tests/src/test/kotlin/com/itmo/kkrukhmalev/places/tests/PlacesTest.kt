package com.itmo.kkrukhmalev.places.tests

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Selenide.`$`
import com.itmo.kkrukhmalev.places.tests.utils.PlacesUtils
import com.itmo.kkrukhmalev.places.tests.utils.Steps
import com.itmo.kkrukhmalev.places.tests.utils.UserUtils
import org.junit.jupiter.api.Test

class PlacesTest : BaseSelenideTest() {
    @Test
    fun testCreatePlacesList() {
        val user = UserUtils.createRandomUser()
        val placesList = PlacesUtils.createRandomPlacesList(user)
        
        openRelative("/sign-up")
        Steps.sendCredentials(user)
        
        openRelative("/add-places-list")
        Steps.createPlacesList(placesList)
        
        Steps.openFirstPlacesList()
        `$`("h3").shouldHave(text("Add Place"))
    }
}