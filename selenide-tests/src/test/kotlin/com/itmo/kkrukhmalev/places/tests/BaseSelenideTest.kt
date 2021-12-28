package com.itmo.kkrukhmalev.places.tests

import com.itmo.kkrukhmalev.places.tests.utils.BrowserDisplayNameGenerator
import io.qameta.allure.Allure
import io.qameta.allure.util.ResultsUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayNameGeneration

@DisplayNameGeneration(BrowserDisplayNameGenerator::class)
abstract class BaseSelenideTest {
    @BeforeEach
    fun setUpHistoryId() {
        Allure.getLifecycle().updateTestCase {
            it.historyId = ResultsUtils.md5(it.name)
        }
    }
}