package com.itmo.kkrukhmalev.places.backend.controller

import com.itmo.kkrukhmalev.places.backend.requestModel.AddPlaceRequestModel
import com.itmo.kkrukhmalev.places.backend.requestModel.AddPlacesListRequestModel
import com.itmo.kkrukhmalev.places.backend.requestModel.IdRequestModel
import com.itmo.kkrukhmalev.places.backend.responseModel.ErrorResponseModel
import com.itmo.kkrukhmalev.places.backend.service.PlacesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@RestController
class PlacesController {
    @Autowired
    lateinit var placesService: PlacesService

    @GetMapping("/places-lists")
    fun placesLists(httpServletResponse: HttpServletResponse, @RequestParam user: String?): Any {
        return try {
            return placesService.getPlacesLists(user)
        } catch (e: Exception) {
            httpServletResponse.status = 500
            ErrorResponseModel(e.message)
        }
    }

    @GetMapping("/places-list")
    fun placesList(httpServletResponse: HttpServletResponse, @RequestParam id: Long?): Any {
        return try {
            return placesService.getPlacesList(id)
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
            val sessionUser = httpSession.getAttribute(AuthorizationController.USER_SESSION)
            if (sessionUser == null) {
                httpServletResponse.status = 401
                return ErrorResponseModel("Sign in to add places lists")
            }
            httpServletResponse.status = 200
            placesService.addListToAdded(sessionUser.toString(), idRequestModel.id)
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
            placesService.removeListFromAdded(sessionUser.toString(), idRequestModel.id)
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
            placesService.addPlacesList(sessionUser.toString(), addPlacesListRequestModel)
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
            placesService.addPlace(addPlaceRequestModel)
            return ""
        } catch (e: Exception) {
            httpServletResponse.status = 400
            return ErrorResponseModel(e.message)
        }
    }
}