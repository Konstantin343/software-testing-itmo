package com.itmo.kkrukhmalev.places.backend.service

import com.itmo.kkrukhmalev.places.backend.base.BaseServiceTest
import com.itmo.kkrukhmalev.places.backend.base.TestModels
import com.itmo.kkrukhmalev.places.backend.domain.Place
import com.itmo.kkrukhmalev.places.backend.domain.PlacesList
import com.itmo.kkrukhmalev.places.backend.domain.User
import com.itmo.kkrukhmalev.places.backend.requestModel.AddPlaceRequestModel
import com.itmo.kkrukhmalev.places.backend.requestModel.AddPlacesListRequestModel
import com.itmo.kkrukhmalev.places.backend.responseModel.ListsResponseModel
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.qameta.allure.junit4.DisplayName
import org.junit.jupiter.api.Assertions
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@Epic("Service Tests (Backend)")
@Feature("Places Tests")
@DisplayName("Service Places Tests (Backend)")
class PlacesServiceTest : BaseServiceTest() {

    private val placesService = PlacesService(
        mockUsersRepo,
        mockPlacesListsRepo,
        mockPlacesRepo
    )

    @Test
    @Story("Get all places lists")
    @DisplayName("Get all places lists")
    fun `get all places lists`() {
        whenever(mockPlacesListsRepo.findAll())
            .thenReturn(listOf(
                TestModels.placesList(1, 1, 0),
                TestModels.placesList(2, 2, 0)
            ))

        Assertions.assertEquals(
            ListsResponseModel(
                lists = listOf(
                    TestModels.placesListResponseModel(1, 1, 0),
                    TestModels.placesListResponseModel(2, 2, 0)
                ),
                addedLists = listOf()
            ),
            placesService.getPlacesLists(null)
        )

        verify(mockPlacesListsRepo).findAll()
    }

    @Test
    @Story("Get places lists by user")
    @DisplayName("Get places lists by user")
    fun `get places lists by user`() {
        val login = "user1"

        whenever(mockPlacesListsRepo.findAllByOwnerLogin(login))
            .thenReturn(listOf(TestModels.placesList(1, 1, 0)))
        whenever(mockUsersRepo.findUserByLogin(login))
            .thenReturn(TestModels.user(1,
                TestModels.placesList(2, 2, 0),
                TestModels.placesList(3, 3, 0)
            ))

        Assertions.assertEquals(
            ListsResponseModel(
                lists = listOf(TestModels.placesListResponseModel(1, 1, 0)),
                addedLists = listOf(
                    TestModels.placesListResponseModel(2, 2, 0),
                    TestModels.placesListResponseModel(3, 3, 0),
                )
            ),
            placesService.getPlacesLists(login)
        )

        verify(mockPlacesListsRepo).findAllByOwnerLogin(login)
        verify(mockUsersRepo).findUserByLogin(login)
    }

    @Test
    @Story("Get places list with place by id")
    @DisplayName("Get places list with place by id")
    fun `get places list with place by id`() {
        whenever(mockPlacesListsRepo.findPlacesListById(1))
            .thenReturn(TestModels.placesList(1, 1, 1))

        Assertions.assertEquals(
            TestModels.placesListResponseModel(1, 1, 1),
            placesService.getPlacesList(1)
        )

        verify(mockPlacesListsRepo).findPlacesListById(1)
    }

    @Test
    @Story("Add list to added")
    @DisplayName("Add list to added")
    fun `add list to added`() {
        val login = "user1"
        val users = mutableListOf<User>()

        val placesList = TestModels.placesList(1, 1, 0)
        val user = TestModels.user(1)
        whenever(mockUsersRepo.findUserByLogin(login))
            .thenReturn(user)
        whenever(mockUsersRepo.save(user))
            .then { users.add(user); return@then user }
        whenever(mockPlacesListsRepo.findPlacesListById(1))
            .thenReturn(placesList)

        placesService.addListToAdded(login, 1)

        Assertions.assertTrue(users.contains(user))
        Assertions.assertTrue(user.addedLists.contains(placesList))

        verify(mockUsersRepo).findUserByLogin(login)
        verify(mockUsersRepo).save(user)
        verify(mockPlacesListsRepo).findPlacesListById(1)
    }

    @Test
    @Story("Remove list from added")
    @DisplayName("Remove list from added")
    fun `remove list from added`() {
        val login = "user1"
        val users = mutableListOf<User>()

        val placesList = TestModels.placesList(1, 1, 0)
        val user = TestModels.user(1, placesList)
        whenever(mockUsersRepo.findUserByLogin(login))
            .thenReturn(user)
        whenever(mockUsersRepo.save(user))
            .then { users.add(user); return@then user }

        placesService.removeListFromAdded(login, 1)

        Assertions.assertTrue(users.contains(user))
        Assertions.assertTrue(!user.addedLists.contains(placesList))

        verify(mockUsersRepo).findUserByLogin(login)
        verify(mockUsersRepo).save(user)
    }

    @Test
    @Story("Add places list")
    @DisplayName("Add places list")
    fun `add places list`() {
        val login = "user2"
        lateinit var placesList: PlacesList

        val user = TestModels.user(2)
        whenever(mockUsersRepo.findUserByLogin(login))
            .thenReturn(user)
        // workaround for kotlin not nullable types
        `when`(mockPlacesListsRepo.save(any()))
            .then { placesList = it.arguments[0] as PlacesList; return@then placesList }

        placesService.addPlacesList(login,
            AddPlacesListRequestModel(
                name = "listName",
                description = "listDescription"
            )
        )

        Assertions.assertEquals(0, placesList.id)
        Assertions.assertEquals("listName", placesList.name)
        Assertions.assertEquals("listDescription", placesList.description)
        Assertions.assertEquals(2, placesList.owner.id)

        verify(mockUsersRepo).findUserByLogin(login)
        verify(mockPlacesListsRepo).save(placesList)
    }

    @Test
    @Story("Add place")
    @DisplayName("Add place")
    fun `add place`() {
        val placesList = TestModels.placesList(1, 1, 0)
        lateinit var place: Place

        whenever(mockPlacesListsRepo.findPlacesListById(1))
            .thenReturn(placesList)
        // workaround for kotlin not nullable types
        `when`(mockPlacesRepo.save(any()))
            .then { place = it.arguments[0] as Place; return@then place }

        placesService.addPlace(
            AddPlaceRequestModel(
                listId = 1,
                name = "placeName",
                description = "placeDescription",
                type = "placeType",
                city = "placeCity",
                street = "placeStreet",
                number = "placeNumber"
            )
        )

        Assertions.assertEquals(0, place.id)
        Assertions.assertEquals("placeName", place.name)
        Assertions.assertEquals("placeDescription", place.description)
        Assertions.assertEquals("placeType", place.type)
        Assertions.assertEquals("placeCity", place.city)
        Assertions.assertEquals("placeStreet", place.street)
        Assertions.assertEquals("placeNumber", place.number)
        Assertions.assertEquals(1, place.placesList.id)
        Assertions.assertEquals("listName1", place.placesList.name)
        Assertions.assertEquals("listDescription1", place.placesList.description)

        verify(mockPlacesListsRepo).findPlacesListById(1)
        verify(mockPlacesRepo).save(place)
    }
}