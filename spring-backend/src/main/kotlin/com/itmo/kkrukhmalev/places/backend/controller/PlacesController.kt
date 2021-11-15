package com.itmo.kkrukhmalev.places.backend.controller

import com.itmo.kkrukhmalev.places.backend.domain.Place
import com.itmo.kkrukhmalev.places.backend.domain.PlacesList
import com.itmo.kkrukhmalev.places.backend.domain.User
import com.itmo.kkrukhmalev.places.backend.repository.PlacesListsRepository
import com.itmo.kkrukhmalev.places.backend.repository.PlacesRepository
import com.itmo.kkrukhmalev.places.backend.repository.UsersRepository
import com.itmo.kkrukhmalev.places.backend.requestModel.AddPlaceRequestModel
import com.itmo.kkrukhmalev.places.backend.requestModel.AddPlacesListRequestModel
import com.itmo.kkrukhmalev.places.backend.requestModel.IdRequestModel
import com.itmo.kkrukhmalev.places.backend.responseModel.ErrorResponseModel
import com.itmo.kkrukhmalev.places.backend.responseModel.ListResponseModel
import com.itmo.kkrukhmalev.places.backend.responseModel.ListsResponseModel
import com.itmo.kkrukhmalev.places.backend.responseModel.PlaceResponseModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@RestController
class PlacesController {
    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var placesListsRepository: PlacesListsRepository

    @Autowired
    lateinit var placesRepository: PlacesRepository

    @GetMapping("/places-lists")
    fun placesLists(httpServletResponse: HttpServletResponse, @RequestParam user: String?): Any {
        return try {
            val lists = if (user == null) placesListsRepository.findAll()
            else placesListsRepository.findAllByOwnerLogin(user)
            val addedLists = if (user == null) listOf()
            else usersRepository.findUserByLogin(user).addedLists
            ListsResponseModel(
                lists = lists.map { ListResponseModel(it.id, it.name, it.description, it.owner.login) },
                addedLists = addedLists.map { ListResponseModel(it.id, it.name, it.description, it.owner.login) }
            )
        } catch (e: Exception) {
            httpServletResponse.status = 500
            ErrorResponseModel(e.message)
        }
    }

    @GetMapping("/places-list")
    fun placesList(httpServletResponse: HttpServletResponse, @RequestParam id: Long?): Any {
        return try {
            val placesList = placesListsRepository.findPlacesListById(id!!)
            return ListResponseModel(
                placesList.id,
                placesList.name,
                placesList.description,
                placesList.owner.login,
                placesList.places.map {
                    PlaceResponseModel(
                        it.id,
                        it.name,
                        it.description,
                        it.type,
                        it.city,
                        it.street,
                        it.number
                    )
                }
            )
        } catch (e: Exception) {
            httpServletResponse.status = 500
            ErrorResponseModel(e.message)
        }
    }

    @PostMapping("/add-list-to-added")
    fun addListToAdded(
        httpServletResponse: HttpServletResponse,
        httpSession: HttpSession,
        @RequestBody idRequestModel: IdRequestModel
    ): Any {
        try {
            val sessionUser = httpSession.getAttribute("user")
            if (sessionUser == null) {
                httpServletResponse.status = 401
                return ErrorResponseModel("Sign in to add places lists")
            }
            val user = usersRepository.findUserByLogin(sessionUser.toString())
            val list = placesListsRepository.findPlacesListById(idRequestModel.id)
            user.addedLists.add(list)
            usersRepository.save(user)
            return ""
        } catch (e: Exception) {
            httpServletResponse.status = 400
            return ErrorResponseModel(e.message)
        }
    }

    @PostMapping("/remove-list-from-added")
    fun removeListFromAdded(
        httpServletResponse: HttpServletResponse,
        httpSession: HttpSession,
        @RequestBody idRequestModel: IdRequestModel
    ): Any {
        try {
            val sessionUser = httpSession.getAttribute("user")
            if (sessionUser == null) {
                httpServletResponse.status = 401
                return ErrorResponseModel("Sign in to remove places lists")
            }
            val user = usersRepository.findUserByLogin(sessionUser.toString())
            user.addedLists.removeIf { it.id == idRequestModel.id }
            usersRepository.save(user)
            return ""
        } catch (e: Exception) {
            httpServletResponse.status = 400
            return ErrorResponseModel(e.message)
        }
    }

    @PostMapping("/add-places-list")
    fun addPlacesList(
        httpServletResponse: HttpServletResponse,
        httpSession: HttpSession,
        @RequestBody addPlacesListRequestModel: AddPlacesListRequestModel,
    ): Any {
        try {
            val sessionUser = httpSession.getAttribute("user")
            if (sessionUser == null) {
                httpServletResponse.status = 401
                return ErrorResponseModel("Sign in to add places lists")
            }
            val user = usersRepository.findUserByLogin(sessionUser.toString())
            placesListsRepository.save(PlacesList().apply {
                name = addPlacesListRequestModel.name
                description = addPlacesListRequestModel.description
                owner = User().apply { id = user.id }
            })
            return ""
        } catch (e: Exception) {
            httpServletResponse.status = 400
            return ErrorResponseModel(e.message)
        }
    }

    @PostMapping("/add-place")
    fun addPlace(
        httpServletResponse: HttpServletResponse,
        httpSession: HttpSession,
        @RequestBody addPlaceRequestModel: AddPlaceRequestModel,
    ): Any {
        try {
            if (httpSession.getAttribute("user") == null) {
                httpServletResponse.status = 401
                return ErrorResponseModel("Sign in to add places")
            }
            val list = placesListsRepository.findPlacesListById(addPlaceRequestModel.listId)
            return placesRepository.save(Place().apply {
                placesList = list
                name = addPlaceRequestModel.name
                description = addPlaceRequestModel.description
                type = addPlaceRequestModel.type
                city = addPlaceRequestModel.city
                street = addPlaceRequestModel.street
                number = addPlaceRequestModel.number
            })
        } catch (e: Exception) {
            httpServletResponse.status = 400
            return ErrorResponseModel(e.message)
        }
    }
}