package com.itmo.kkrukhmalev.places.backend.controller

import com.itmo.kkrukhmalev.places.backend.base.BaseControllerTest
import com.itmo.kkrukhmalev.places.backend.domain.User
import com.itmo.kkrukhmalev.places.backend.requestModel.AuthRequestModel
import com.itmo.kkrukhmalev.places.backend.service.AuthorizationService
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.qameta.allure.junit4.DisplayName
import org.junit.Test
import org.mockito.kotlin.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(AuthorizationController::class)
@Epic("Controller Tests (Backend)")
@Feature("Authorization Tests")
@DisplayName("Controller Authorization Tests (Backend)")
class AuthorizationControllerTest : BaseControllerTest<AuthorizationService>() {

    @Test
    @Story("Get current user")
    @DisplayName("Current user when unauthorized")
    fun `current user when unauthorized`() {
        mockMvc.get("/current-user")
            .andExpect {
                status { isOk() }
                content { json("{\"user\":null}") }
            }
    }

    @Test
    @Story("Get current user")
    @DisplayName("Current user when authorized")
    fun `current user when authorized`() {
        mockMvc.get("/current-user") {
            session = MockHttpSession().apply {
                setAttribute("user", "user123")
            }
        }.andExpect {
            status { isOk() }
            content { json("{\"user\":\"user123\"}") }
        }.createDocs()
    }

    @Test
    @Story("Sign in")
    @DisplayName("Sign in with correct data")
    fun `sign in success`() {
        val authModel = AuthRequestModel("login", "password", "redirectTo")
        given(service.signIn(authModel))
            .willReturn(User().apply {
                login = "login"
                password = "hashPassword"
            })

        mockMvc.post("/sign-in") {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            param("login", "login")
            param("password", "password")
            param("redirectTo", "redirectTo")
        }.andExpect {
            status {
                is3xxRedirection()
                redirectedUrl("redirectTo")
            }
            content { string("") }
            request { sessionAttribute("user", "login") }
        }.createDocs()
    }

    @Test
    @Story("Sign in")
    @DisplayName("Sign in with incorrect data")
    fun `sign in failed`() {
        val authModel = AuthRequestModel("login", "password", "redirectTo/")
        given(service.signIn(authModel))
            .willThrow(RuntimeException())

        mockMvc.post("/sign-in") {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            param("login", "login")
            param("password", "password")
            param("redirectTo", "redirectTo/")
        }.andExpect {
            status {
                is3xxRedirection()
                redirectedUrl("redirectTo/sign-in")
            }
            content { string("") }
            request { sessionAttribute("user", null) }
        }
    }

    @Test
    @Story("Sign up")
    @DisplayName("Sign up with correct data")
    fun `sign up success`() {
        val authModel = AuthRequestModel("login", "password", "redirectTo")
        given(service.signUp(authModel))
            .willReturn(User().apply {
                login = "login"
                password = "hashPassword"
            })

        mockMvc.post("/sign-up") {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            param("login", "login")
            param("password", "password")
            param("redirectTo", "redirectTo")
        }.andExpect {
            status {
                is3xxRedirection()
                redirectedUrl("redirectTo")
            }
            content { string("") }
            request { sessionAttribute("user", "login") }
        }.createDocs()
    }

    @Test
    @Story("Sign up")
    @DisplayName("Sign up with incorrect data")
    fun `sign up failed`() {
        val authModel = AuthRequestModel("login", "password", "redirectTo/")
        given(service.signUp(authModel))
            .willThrow(RuntimeException())

        mockMvc.post("/sign-up") {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            param("login", "login")
            param("password", "password")
            param("redirectTo", "redirectTo/")
        }.andExpect {
            status {
                is3xxRedirection()
                redirectedUrl("redirectTo/sign-up")
            }
            content { string("") }
            request { sessionAttribute("user", null) }
        }
    }
}