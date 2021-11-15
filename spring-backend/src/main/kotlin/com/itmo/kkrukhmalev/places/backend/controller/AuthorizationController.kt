package com.itmo.kkrukhmalev.places.backend.controller

import com.itmo.kkrukhmalev.places.backend.domain.User
import com.itmo.kkrukhmalev.places.backend.repository.UsersRepository
import com.itmo.kkrukhmalev.places.backend.requestModel.AuthRequestModel
import com.itmo.kkrukhmalev.places.backend.responseModel.UserResponseModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@RestController
class AuthorizationController {
    @Autowired
    lateinit var usersRepository: UsersRepository

    @PostMapping("/sign-up")
    fun signUp(httpServletResponse: HttpServletResponse, httpSession: HttpSession, authRequestModel: AuthRequestModel) {
        try {
            val user = usersRepository.save(User().apply {
                login = authRequestModel.login
                password = authRequestModel.password
            })
            httpSession.setAttribute("user", user.login)
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
            val user = usersRepository.findUserByLoginAndPassword(
                authRequestModel.login,
                authRequestModel.password
            )
            httpSession.setAttribute("user", user.login)
            httpServletResponse.status = 301
            httpServletResponse.sendRedirect(authRequestModel.redirectTo)
        } catch (e: Exception) {
            httpServletResponse.status = 301
            httpServletResponse.sendRedirect(authRequestModel.redirectTo + "sign-in")
        }
    }

    @PostMapping("/sign-out")
    fun signOut(httpSession: HttpSession) {
        httpSession.setAttribute("user", null)
    }

    @GetMapping("/current-user")
    fun currentUser(httpSession: HttpSession): UserResponseModel {
        return UserResponseModel(httpSession.getAttribute("user") as? String)
    }
}