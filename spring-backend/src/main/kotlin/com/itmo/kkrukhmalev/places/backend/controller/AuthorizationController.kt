package com.itmo.kkrukhmalev.places.backend.controller

import com.itmo.kkrukhmalev.places.backend.requestModel.AuthRequestModel
import com.itmo.kkrukhmalev.places.backend.responseModel.UserResponseModel
import com.itmo.kkrukhmalev.places.backend.service.AuthorizationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@RestController
class AuthorizationController {
    companion object {
        const val USER_SESSION = "user"
    }

    @Autowired
    lateinit var authorizationService: AuthorizationService

    @PostMapping("/sign-up")
    fun signUp(httpServletResponse: HttpServletResponse, httpSession: HttpSession, authRequestModel: AuthRequestModel) {
        try {
            val user = authorizationService.signUp(authRequestModel)
            httpSession.setAttribute(USER_SESSION, user.login)
            httpServletResponse.status = 301
            httpServletResponse.sendRedirect(authRequestModel.redirectTo)
        } catch (e: Exception) {
            httpServletResponse.status = 301
            httpServletResponse.sendRedirect(authRequestModel.redirectTo + "sign-up")
        }
    }

    @PostMapping("/sign-in")
    fun signIn(httpServletResponse: HttpServletResponse, httpSession: HttpSession, authRequestModel: AuthRequestModel) {
        try {
            val user = authorizationService.signIn(authRequestModel)
            httpSession.setAttribute(USER_SESSION, user.login)
            httpServletResponse.status = 301
            httpServletResponse.sendRedirect(authRequestModel.redirectTo)
        } catch (e: Exception) {
            httpServletResponse.status = 301
            httpServletResponse.sendRedirect(authRequestModel.redirectTo + "sign-in")
        }
    }

    @PostMapping("/sign-out")
    fun signOut(httpSession: HttpSession) = httpSession.setAttribute(USER_SESSION, null)

    @GetMapping("/current-user")
    fun currentUser(httpSession: HttpSession): UserResponseModel {
        return UserResponseModel(httpSession.getAttribute(USER_SESSION) as? String)
    }
}