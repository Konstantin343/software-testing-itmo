package com.itmo.kkrukhmalev.places.backend.system

import com.itmo.kkrukhmalev.places.backend.base.AllureHelper
import com.itmo.kkrukhmalev.places.backend.base.BaseSystemTest
import io.qameta.allure.Description
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import io.qameta.allure.junit4.DisplayName
import org.junit.jupiter.api.Assertions
import org.junit.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

@Epic("System Tests (Backend)")
@Feature("Authorization Tests")
@DisplayName("System Authorization Tests (Backend)")
class AuthorizationSystemTest : BaseSystemTest() {
    @Test
    @Story("User must sign up/sign in/sing out only with correct data")
    @DisplayName("All authorization operations")
    @Description("Sign up, sign up with same credentials, sign in and sign out")
    fun `all authorization operations`() {
        AllureHelper.step("Sign up") {
            val signUp1Response = signUp("login", "password", "http://localhost/")
            Assertions.assertEquals(HttpStatus.FOUND, signUp1Response.statusCode)
            Assertions.assertEquals("http://localhost/", signUp1Response.headers.location.toString())
        }

        AllureHelper.step("Sign up with same login") {
            val signUp2Response = signUp("login", "password2", "http://localhost/")
            Assertions.assertEquals(HttpStatus.FOUND, signUp2Response.statusCode)
            Assertions.assertEquals("http://localhost/sign-up", signUp2Response.headers.location.toString())
        }

        AllureHelper.step("Sign in with wrong login") {
            val signIn1Response = signIn("login", "password2", "http://localhost/")
            Assertions.assertEquals(HttpStatus.FOUND, signIn1Response.statusCode)
            Assertions.assertEquals("http://localhost/sign-in", signIn1Response.headers.location.toString())
        }

        val cookie = AllureHelper.step("Sign in with wrong login") {
            val signIn2Response = signIn("login", "password", "http://localhost/")
            Assertions.assertEquals(HttpStatus.FOUND, signIn2Response.statusCode)
            Assertions.assertEquals("http://localhost/", signIn2Response.headers.location.toString())
            signIn2Response.headers.getFirst(HttpHeaders.SET_COOKIE)!!
        }

        AllureHelper.step("Get current user without session") {
            val currentUser1Response = currentUser()
            Assertions.assertEquals(HttpStatus.OK, currentUser1Response.statusCode)
            Assertions.assertEquals("{\"user\":null}", currentUser1Response.body)
        }

        AllureHelper.step("Get current user with session") {
            val currentUser2Response = currentUser(cookie)
            Assertions.assertEquals(HttpStatus.OK, currentUser2Response.statusCode)
            Assertions.assertEquals("{\"user\":\"login\"}", currentUser2Response.body)
        }
        
        AllureHelper.step("Sign out") {
            val signOutResponse = signOut(cookie)
            Assertions.assertEquals(HttpStatus.OK, signOutResponse.statusCode)
        }

        AllureHelper.step("Get current user after sign out") {
            val currentUser3Response = currentUser(cookie)
            Assertions.assertEquals(HttpStatus.OK, currentUser3Response.statusCode)
            Assertions.assertEquals("{\"user\":null}", currentUser3Response.body)
        }
    }
}