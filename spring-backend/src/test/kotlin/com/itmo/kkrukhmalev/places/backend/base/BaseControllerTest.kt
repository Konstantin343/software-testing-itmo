package com.itmo.kkrukhmalev.places.backend.base

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

abstract class BaseControllerTest<T : Any> {
    @MockBean
    protected lateinit var service: T

    @Autowired
    protected lateinit var mockMvc: MockMvc
}