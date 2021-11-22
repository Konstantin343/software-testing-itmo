package com.itmo.kkrukhmalev.places.backend.configuration

import com.itmo.kkrukhmalev.places.backend.service.AuthorizationService
import com.itmo.kkrukhmalev.places.backend.service.PlacesService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean

@TestConfiguration
@ConditionalOnProperty(value = ["test.configuration"], havingValue = "springMock", matchIfMissing = true)
class SpringMockBeanTestConfiguration : BaseTestConfiguration {

    @MockBean
    private lateinit var placesService: PlacesService

    @MockBean
    private lateinit var authorizationService: AuthorizationService
    
    override fun getPlacesService() = placesService
    
    override fun getAuthorizationService() = authorizationService
}