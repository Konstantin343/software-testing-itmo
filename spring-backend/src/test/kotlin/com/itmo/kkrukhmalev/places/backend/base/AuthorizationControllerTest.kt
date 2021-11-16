package com.itmo.kkrukhmalev.places.backend.base

import com.itmo.kkrukhmalev.places.backend.controller.AuthorizationController
import com.itmo.kkrukhmalev.places.backend.domain.User
import com.itmo.kkrukhmalev.places.backend.requestModel.AuthRequestModel
import com.itmo.kkrukhmalev.places.backend.service.AuthorizationService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.lang.RuntimeException

@WebMvcTest(AuthorizationController::class)
class AuthorizationControllerTest {
    @MockBean
    lateinit var authorizationService: AuthorizationService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `current user when unauthorized`() {
        mockMvc.get("/current-user")
            .andExpect {
                status { isOk() }
                content { json("{\"user\":null}") }
            }
    }

    @Test
    fun `current user when authorized`() {
        mockMvc.get("/current-user") {
            session = MockHttpSession().apply { setAttribute("user", "user123") }
        }.andExpect {
            status { isOk() }
            content { json("{\"user\":\"user123\"}") }
        }
    }

    @Test
    fun `sign in success`() {
        val authModel = AuthRequestModel("login", "password", "redirectTo")
        given(authorizationService.signIn(authModel))
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
        }
    }

    @Test
    fun `sign up success`() {
        val authModel = AuthRequestModel("login", "password", "redirectTo")
        given(authorizationService.signUp(authModel))
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
        }
    }

    @Test
    fun `sign up failed`() {
        val authModel = AuthRequestModel("login", "password", "redirectTo/")
        given(authorizationService.signUp(authModel))
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