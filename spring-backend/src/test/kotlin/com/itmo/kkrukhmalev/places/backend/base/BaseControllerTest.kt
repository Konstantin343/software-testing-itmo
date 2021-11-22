package com.itmo.kkrukhmalev.places.backend.base

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl

@AutoConfigureRestDocs(outputDir = "target/snippets")
abstract class BaseControllerTest<T : Any> {
    @MockBean
    protected lateinit var service: T

    @Autowired
    protected lateinit var mockMvc: MockMvc

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