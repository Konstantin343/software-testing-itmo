package com.itmo.kkrukhmalev.places.backend.base

import com.itmo.kkrukhmalev.places.backend.configuration.MockitoKotlinMockTestConfiguration
import com.itmo.kkrukhmalev.places.backend.configuration.SpringMockBeanTestConfiguration
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.context.annotation.Import
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl

@AutoConfigureRestDocs(outputDir = "target/snippets")
@Import(MockitoKotlinMockTestConfiguration::class, SpringMockBeanTestConfiguration::class)
abstract class BaseControllerTest<T : Any> {
    @Autowired
    protected lateinit var service: T

    @Autowired
    protected lateinit var mockMvc: MockMvc
    
    @AfterEach
    fun resetMocks() = Mockito.reset(service)

    protected fun ResultActionsDsl.createDocs() {
        this.andDo {
            handle(document(
                "{ClassName}/{method_name}",
                preprocessRequest(
                    prettyPrint()
                ),
                preprocessResponse(prettyPrint())
            ))
        }
    }
}