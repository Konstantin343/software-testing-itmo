package com.itmo.kkrukhmalev.places.backend.configuration

import com.itmo.kkrukhmalev.places.backend.service.AuthorizationService
import com.itmo.kkrukhmalev.places.backend.service.PlacesService
import org.mockito.kotlin.mock
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
@ConditionalOnProperty(value = ["test.configuration"], havingValue = "mockitoMock")
class MockitoKotlinMockTestConfiguration : BaseTestConfiguration {
    
    @Bean
    override fun getPlacesService(): PlacesService = mock()

    @Bean
    override fun getAuthorizationService(): AuthorizationService = mock()
}