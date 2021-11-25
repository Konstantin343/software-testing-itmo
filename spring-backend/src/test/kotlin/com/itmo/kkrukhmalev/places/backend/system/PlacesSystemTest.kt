package com.itmo.kkrukhmalev.places.backend.system

import com.itmo.kkrukhmalev.places.backend.base.AllureHelper
import com.itmo.kkrukhmalev.places.backend.base.BaseSystemTest
import com.itmo.kkrukhmalev.places.backend.responseModel.ListResponseModel
import com.itmo.kkrukhmalev.places.backend.responseModel.ListsResponseModel
import io.qameta.allure.Description
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.qameta.allure.junit4.DisplayName
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

@Epic("System Tests")
@Feature("Places Tests")
@DisplayName("System Places Tests")
class PlacesSystemTest : BaseSystemTest() {
    @Test
    @Story("User must see created lists")
    @DisplayName("Create and get places lists")
    @Description("Sign up, creates empty list and check that list exists")
    fun `create and get places lists`() {
        val cookie = AllureHelper.step("Sign up") {
            val signUpResponse = signUp("user", "password", "http://localhost/")
            Assertions.assertEquals(HttpStatus.FOUND, signUpResponse.statusCode)
            Assertions.assertEquals("http://localhost/", signUpResponse.headers.location.toString())
            signUpResponse.headers.getFirst(HttpHeaders.SET_COOKIE)!!
        }

        AllureHelper.step("Create places list") {
            val createPlacesListResponse = createPlacesList("listName", "listDescription", cookie)
            Assertions.assertEquals(HttpStatus.OK, createPlacesListResponse.statusCode)
        }
        
        AllureHelper.step("Get my places lists with session") {
            val getMyPlacesListsResponse = getPlacesLists("user", cookie)
            Assertions.assertEquals(HttpStatus.OK, getMyPlacesListsResponse.statusCode)
            getMyPlacesListsResponse.body!!.lists.single().id = 0
            Assertions.assertEquals(ListsResponseModel(
                lists = listOf(
                    ListResponseModel(0, "listName", "listDescription", "user")
                ),
                addedLists = listOf()
            ), getMyPlacesListsResponse.body)
        }
        
        AllureHelper.step("Get all places lists with session") {
            val getPlacesListsResponse = getPlacesLists(cookie = cookie)
            Assertions.assertEquals(HttpStatus.OK, getPlacesListsResponse.statusCode)
            getPlacesListsResponse.body!!.lists.single().id = 0
            Assertions.assertEquals(ListsResponseModel(
                lists = listOf(
                    ListResponseModel(0, "listName", "listDescription", "user")
                ),
                addedLists = listOf()
            ), getPlacesListsResponse.body)
        }
    }
}