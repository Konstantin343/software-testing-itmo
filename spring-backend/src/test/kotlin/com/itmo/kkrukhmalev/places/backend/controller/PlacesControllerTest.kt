package com.itmo.kkrukhmalev.places.backend.controller

import com.itmo.kkrukhmalev.places.backend.base.BaseControllerTest
import com.itmo.kkrukhmalev.places.backend.base.TestModels
import com.itmo.kkrukhmalev.places.backend.requestModel.AddPlacesListRequestModel
import com.itmo.kkrukhmalev.places.backend.responseModel.ListsResponseModel
import com.itmo.kkrukhmalev.places.backend.service.PlacesService
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.qameta.allure.junit4.DisplayName
import org.junit.Test
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.Mockito.doThrow
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(PlacesController::class)
@Epic("Controller Tests (Backend)")
@Feature("Places Tests")
@DisplayName("Controller Places Tests (Backend)")
class PlacesControllerTest : BaseControllerTest<PlacesService>() {
    @Test
    @Story("Get all places lists")
    @DisplayName("Get all places lists")
    fun `get all places lists`() {
        given(service.getPlacesLists(null))
            .willReturn(ListsResponseModel(
                lists = listOf(
                    TestModels.placesListResponseModel(1, 1, 0)
                ),
                addedLists = listOf(
                    TestModels.placesListResponseModel(2, 2, 0)
                )
            ))

        mockMvc.get("/places-lists")
            .andExpect {
                status { isOk() }
                content {
                    json("""
                        {
                            "lists":[
                                {
                                    "id":1,
                                    "name":"listName1",
                                    "description":"listDescription1",
                                    "owner":"user1",
                                    "places":[]
                                }
                            ],
                            "addedLists":[
                                {
                                    "id":2,
                                    "name":"listName2",
                                    "description":"listDescription2",
                                    "owner":"user2",
                                    "places":[]
                                }
                            ]
                        }
                    """.trimIndent())
                }
            }.createDocs()

        verify(service).getPlacesLists(null)
    }

    @Test
    @Story("Get all places lists")
    @DisplayName("Get all places lists (exception)")
    fun `get places lists with exception`() {
        given(service.getPlacesLists(null))
            .willThrow(RuntimeException("errorText"))

        mockMvc.get("/places-lists")
            .andExpect {
                status { isInternalServerError() }
                content {
                    json("{\"error\":\"errorText\"}")
                }
            }

        verify(service).getPlacesLists(null)
    }

    @Test
    @Story("Get place lists")
    @DisplayName("Get place lists")
    fun `get places list`() {
        given(service.getPlacesList(1))
            .willReturn(TestModels.placesListResponseModel(1, 1, 1))

        mockMvc.get("/places-list") {
            contentType = MediaType.APPLICATION_JSON
            param("id", "1")
        }.andExpect {
            status { isOk() }
            content {
                json("""
                        {
                            "id":1,
                            "name":"listName1",
                            "description":"listDescription1",
                            "owner":"user1",
                            "places":[
                                {
                                    "id":1,
                                    "name":"placeName1",
                                    "description":"placeDescription1",
                                    "type":"placeType1",
                                    "city":"placeCity1",
                                    "street":"placeStreet1",
                                    "number":"placeNumber1"
                                }
                            ]
                        }
                    """.trimIndent())
            }
        }.createDocs()

        verify(service).getPlacesList(1)
    }

    @Test
    @Story("Get place lists")
    @DisplayName("Get place lists (error)")
    fun `get places list with error`() {
        given(service.getPlacesList(1))
            .willThrow(RuntimeException("errorText"))

        mockMvc.get("/places-list") {
            contentType = MediaType.APPLICATION_JSON
            param("id", "1")
        }.andExpect {
            status { isInternalServerError() }
            content {
                json("{\"error\":\"errorText\"}".trimIndent())
            }
        }

        verify(service).getPlacesList(1)
    }

    @Test
    @Story("Add list to added")
    @DisplayName("Add list to added")
    fun `add list to added`() {
        doNothing()
            .whenever(service).addListToAdded("user", 1)

        mockMvc.post("/add-list-to-added") {
            session = MockHttpSession().apply {
                setAttribute("user", "user")
            }
            contentType = MediaType.APPLICATION_JSON
            content = "{\"id\":1}"
        }.andExpect {
            status { isOk() }
            content {
                string("")
            }
        }.createDocs()

        verify(service).addListToAdded("user", 1)
    }

    @Test
    @Story("Add list to added")
    @DisplayName("Add list to added (exception)")
    fun `add list to added with exception`() {
        doThrow(RuntimeException("errorText"))
            .whenever(service).addListToAdded("user", 1)

        mockMvc.post("/add-list-to-added") {
            session = MockHttpSession().apply {
                setAttribute("user", "user")
            }
            contentType = MediaType.APPLICATION_JSON
            content = "{\"id\":1}"
        }.andExpect {
            status { isBadRequest() }
            content {
                json("{\"error\":\"errorText\"}")
            }
        }

        verify(service).addListToAdded("user", 1)
    }

    @Test
    @Story("Add list to added")
    @DisplayName("Add list to added (unauthorized)")
    fun `add list to added when unauthorized`() {
        mockMvc.post("/add-list-to-added") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\"id\":1}"
        }.andExpect {
            status { isUnauthorized() }
            content {
                json("{\"error\":\"Sign in to add places lists\"}")
            }
        }
    }

    @Test
    @Story("Remove list from added")
    @DisplayName("Remove list from added")
    fun `remove list from added`() {
        doNothing()
            .whenever(service).removeListFromAdded("user", 1)

        mockMvc.post("/remove-list-from-added") {
            session = MockHttpSession().apply {
                setAttribute("user", "user")
            }
            contentType = MediaType.APPLICATION_JSON
            content = "{\"id\":1}"
        }.andExpect {
            status { isOk() }
            content {
                string("")
            }
        }.createDocs()

        verify(service).removeListFromAdded("user", 1)
    }

    @Test
    @Story("Remove list from added")
    @DisplayName("Remove list from added (exception)")
    fun `remove list from added with exception`() {
        doThrow(RuntimeException("errorText"))
            .whenever(service).removeListFromAdded("user", 1)

        mockMvc.post("/remove-list-from-added") {
            session = MockHttpSession().apply {
                setAttribute("user", "user")
            }
            contentType = MediaType.APPLICATION_JSON
            content = "{\"id\":1}"
        }.andExpect {
            status { isBadRequest() }
            content {
                json("{\"error\":\"errorText\"}")
            }
        }

        verify(service).removeListFromAdded("user", 1)
    }

    @Test
    @Story("Remove list from added")
    @DisplayName("Remove list from added (unauthorized)")
    fun `remove list from added when unauthorized`() {
        mockMvc.post("/remove-list-from-added") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\"id\":1}"
        }.andExpect {
            status { isUnauthorized() }
            content {
                json("{\"error\":\"Sign in to remove places lists\"}")
            }
        }
    }

    @Test
    @Story("Add places list")
    @DisplayName("Add places list")
    fun `add places list`() {
        val placesList = AddPlacesListRequestModel(
            "listName",
            "listDescription"
        )
        doNothing()
            .whenever(service).addPlacesList("user", placesList)

        mockMvc.post("/add-places-list") {
            session = MockHttpSession().apply {
                setAttribute("user", "user")
            }
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "name":"listName",
                    "description":"listDescription"
                }
            """.trimIndent()
        }.andExpect {
            status { isOk() }
            content {
                string("")
            }
        }.createDocs()

        verify(service).addPlacesList("user", placesList)
    }

    @Test
    @Story("Add places list")
    @DisplayName("Add places list (exception)")
    fun `add places list with exception`() {
        val placesList = AddPlacesListRequestModel(
            "listName",
            "listDescription"
        )
        doThrow(RuntimeException("errorText"))
            .whenever(service).addPlacesList("user", placesList)

        mockMvc.post("/add-places-list") {
            session = MockHttpSession().apply {
                setAttribute("user", "user")
            }
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "name":"listName",
                    "description":"listDescription"
                }
            """.trimIndent()
        }.andExpect {
            status { isBadRequest() }
            content {
                string("{\"error\":\"errorText\"}")
            }
        }

        verify(service).addPlacesList("user", placesList)
    }

    @Test
    @Story("Add places list")
    @DisplayName("Add places list (unauthorized)")
    fun `add places list when unauthorized`() {
        mockMvc.post("/add-places-list") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "name":"listName",
                    "description":"listDescription"
                }
            """.trimIndent()
        }.andExpect {
            status { isUnauthorized() }
            content {
                string("{\"error\":\"Sign in to add places lists\"}")
            }
        }
    }
}