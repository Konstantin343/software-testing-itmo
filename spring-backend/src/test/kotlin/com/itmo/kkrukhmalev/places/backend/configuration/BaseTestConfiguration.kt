package com.itmo.kkrukhmalev.places.backend.configuration

import com.itmo.kkrukhmalev.places.backend.service.AuthorizationService
import com.itmo.kkrukhmalev.places.backend.service.PlacesService
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

interface BaseTestConfiguration : WebMvcConfigurer {
    @Bean
    fun getPlacesService(): PlacesService
    
    @Bean
    fun getAuthorizationService(): AuthorizationService
}