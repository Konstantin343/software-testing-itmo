package com.itmo.kkrukhmalev.places.tests.utils

import com.codeborne.selenide.Condition.attribute
import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Selectors.byAttribute
import com.codeborne.selenide.Selectors.byText
import com.codeborne.selenide.Selenide.`$`
import com.itmo.kkrukhmalev.places.tests.model.PlacesList
import com.itmo.kkrukhmalev.places.tests.model.User

object Steps {
    fun sendCredentials(user: User) {
        `$`("#login").shouldHave(attribute("placeholder", "Login"))
        `$`("#password").shouldHave(attribute("placeholder", "Password"))

        `$`("#login").sendKeys(user.login)
        `$`("#password").sendKeys(user.password)
        `$`(".login-button").click()
    }

    fun signOut(user: User) {
        `$`(byAttribute("href", "#"))
            .shouldHave(text("Sign out (${user.login})"))
        `$`(byAttribute("href", "#")).click()
    }
    
    fun checkHelloText(user: User) =
        `$`("h1").shouldHave(text("Hello, ${user.login}! This is Places App."))
    
    fun createPlacesList(placesList: PlacesList) {
        `$`("#name").shouldHave(attribute("placeholder", "Name"))
        `$`("#description").shouldHave(attribute("placeholder", "Description"))

        `$`("#name").sendKeys(placesList.name)
        `$`("#description").sendKeys(placesList.description)
        `$`(".login-button").click()
    }
    
    fun openFirstPlacesList() {
        `$`(byText("My lists")).parent().find("a").click()
    }
}