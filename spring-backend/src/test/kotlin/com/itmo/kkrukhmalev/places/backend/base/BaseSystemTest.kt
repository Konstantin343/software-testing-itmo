package com.itmo.kkrukhmalev.places.backend.base

import com.itmo.kkrukhmalev.places.backend.responseModel.ListsResponseModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.util.LinkedMultiValueMap
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Suppress("SameParameterValue")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [BaseSystemTest.Initializer::class])
abstract class BaseSystemTest {
    object Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        lateinit var postgreSQLContainer: PostgreSQLContainer<Nothing>
        
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:11.1")
                .apply {
                    withDatabaseName("integration-tests-db")
                    withExposedPorts(30030)
                    withUsername("sa")
                    withPassword("sa")
                }
            postgreSQLContainer.start()
            TestPropertyValues.of(
                "spring.datasource.url=" + postgreSQLContainer.jdbcUrl,
                "spring.datasource.username=" + postgreSQLContainer.username,
                "spring.datasource.password=" + postgreSQLContainer.password
            ).applyTo(configurableApplicationContext.environment)
        }
    }

    @Autowired
    protected lateinit var restTemplateBuilder: RestTemplateBuilder

    @LocalServerPort
    protected var port: Int = 0

    private inline fun <reified T, P> post(method: String, httpEntity: HttpEntity<P>) =
        restTemplateBuilder.build().postForEntity(
            "http://localhost:$port/$method",
            httpEntity,
            T::class.java
        )

    private inline fun <reified T> get(
        method: String,
        cookie: String? = null,
        params: Map<String, String> = mapOf(),
    ) =
        restTemplateBuilder.build().exchange(
            "http://localhost:$port/$method?" + params.entries.joinToString("&") { it.key + "=" + it.value },
            HttpMethod.GET,
            HttpEntity<Any>(
                HttpHeaders().apply {
                    if (cookie != null)
                        set(HttpHeaders.COOKIE, cookie)
                }
            ),
            T::class.java
        )

    protected fun auth(method: String, login: String, password: String, redirectTo: String) =
        post<String, LinkedMultiValueMap<String, String>>(
            method,
            HttpEntity(
                LinkedMultiValueMap<String, String>()
                    .apply {
                        add("login", login)
                        add("password", password)
                        add("redirectTo", redirectTo)
                    },
                HttpHeaders().apply { contentType = MediaType.APPLICATION_FORM_URLENCODED }
            )
        )

    protected fun signUp(login: String, password: String, redirectTo: String) =
        auth("sign-up", login, password, redirectTo)

    protected fun signIn(login: String, password: String, redirectTo: String) =
        auth("sign-in", login, password, redirectTo)

    protected fun signOut(cookie: String) =
        post<String, Any>("sign-out", HttpEntity(
            HttpHeaders().apply {
                set(HttpHeaders.COOKIE, cookie)
            }
        ))

    protected fun currentUser(cookie: String? = null) =
        get<String>("current-user", cookie)

    protected fun createPlacesList(name: String, description: String, cookie: String? = null) =
        post<String, Any>("add-places-list", HttpEntity(
            """
                {
                    "name":"$name",
                    "description":"$description"
                }
            """.trimIndent(),
            HttpHeaders().apply {
                if (cookie != null)
                    set(HttpHeaders.COOKIE, cookie)
                contentType = MediaType.APPLICATION_JSON
            }
        ))
    
    protected fun getPlacesLists(user: String? = null, cookie: String? = null) =
        get<ListsResponseModel>("places-lists", cookie, if (user != null) mapOf("user" to user) else mapOf())
}