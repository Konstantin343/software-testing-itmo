package com.itmo.kkrukhmalev.places.tests

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.open
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseSelenideTest {
    private val properties = Properties()

    @BeforeAll
    fun loadProperties() {
        properties.load(this.javaClass.classLoader.getResourceAsStream("test.properties"))
    }

    @BeforeEach
    open fun setUp() {
        Configuration.driverManagerEnabled = false
        Configuration.remote = "http://localhost:4444/wd/hub"
    }
    
    protected fun openRelative(relativeUrl: String) {
        open("${properties.getProperty("test.url")}$relativeUrl")
    } 
}