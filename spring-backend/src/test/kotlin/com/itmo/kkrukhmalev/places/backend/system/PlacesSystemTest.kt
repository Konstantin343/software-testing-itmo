package com.itmo.kkrukhmalev.places.backend.system

import com.itmo.kkrukhmalev.places.backend.base.BaseSystemTest
import com.itmo.kkrukhmalev.places.backend.responseModel.ListResponseModel
import com.itmo.kkrukhmalev.places.backend.responseModel.ListsResponseModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

class PlacesSystemTest : BaseSystemTest() {
    @Test
    fun `create and get places lists`() {
        // Sign up succeed
        val signUpResponse = signUp("user", "password", "http://localhost/")
        Assertions.assertEquals(HttpStatus.FOUND, signUpResponse.statusCode)
        Assertions.assertEquals("http://localhost/", signUpResponse.headers.location.toString())

        // Create places list with session
        val cookie = signUpResponse.headers.getFirst(HttpHeaders.SET_COOKIE)!!
        val createPlacesListResponse = createPlacesList("listName", "listDescription", cookie)
        Assertions.assertEquals(HttpStatus.OK, createPlacesListResponse.statusCode)
        
        // Get my places lists with session
        val getMyPlacesListsResponse = getPlacesLists("user", cookie)
        Assertions.assertEquals(HttpStatus.OK, getMyPlacesListsResponse.statusCode)
        getMyPlacesListsResponse.body!!.lists.single().id = 0
        Assertions.assertEquals(ListsResponseModel(
            lists = listOf(
                ListResponseModel(0, "listName", "listDescription", "user")
            ),
            addedLists = listOf()
        ), getMyPlacesListsResponse.body)

        // Get all places lists with session
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