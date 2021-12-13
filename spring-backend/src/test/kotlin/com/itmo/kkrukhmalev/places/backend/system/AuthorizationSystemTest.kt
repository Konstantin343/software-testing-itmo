package com.itmo.kkrukhmalev.places.backend.system

import com.itmo.kkrukhmalev.places.backend.base.BaseSystemTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

class AuthorizationSystemTest : BaseSystemTest() {
    @Test
    fun `all authorization operations`() {
        // Sign up succeed
        val signUp1Response = signUp("login", "password", "http://localhost/")
        Assertions.assertEquals(HttpStatus.FOUND, signUp1Response.statusCode)
        Assertions.assertEquals("http://localhost/", signUp1Response.headers.location.toString())

        // Sign up with same login failed
        val signUp2Response = signUp("login", "password2", "http://localhost/")
        Assertions.assertEquals(HttpStatus.FOUND, signUp2Response.statusCode)
        Assertions.assertEquals("http://localhost/sign-up", signUp2Response.headers.location.toString())

        // Sign in with wrong password
        val signIn1Response = signIn("login", "password2", "http://localhost/")
        Assertions.assertEquals(HttpStatus.FOUND, signIn1Response.statusCode)
        Assertions.assertEquals("http://localhost/sign-in", signIn1Response.headers.location.toString())

        // Sign in with correct password
        val signIn2Response = signIn("login", "password", "http://localhost/")
        Assertions.assertEquals(HttpStatus.FOUND, signIn2Response.statusCode)
        Assertions.assertEquals("http://localhost/", signIn2Response.headers.location.toString())

        // Get current user without session
        val currentUser1Response = currentUser()
        Assertions.assertEquals(HttpStatus.OK, currentUser1Response.statusCode)
        Assertions.assertEquals("{\"user\":null}", currentUser1Response.body)

        // Get current user with session
        val cookieFromSignIn = signIn2Response.headers.getFirst(HttpHeaders.SET_COOKIE)!!
        val currentUser2Response = currentUser(cookieFromSignIn)
        Assertions.assertEquals(HttpStatus.OK, currentUser2Response.statusCode)
        Assertions.assertEquals("{\"user\":\"login\"}", currentUser2Response.body)

        // Sign out
        val signOutResponse = signOut(cookieFromSignIn)
        Assertions.assertEquals(HttpStatus.OK, signOutResponse.statusCode)

        // Get current user after sign out
        val currentUser3Response = currentUser(cookieFromSignIn)
        Assertions.assertEquals(HttpStatus.OK, currentUser3Response.statusCode)
        Assertions.assertEquals("{\"user\":null}", currentUser3Response.body)
    }
}