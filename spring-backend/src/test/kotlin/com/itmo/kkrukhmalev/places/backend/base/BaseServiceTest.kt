package com.itmo.kkrukhmalev.places.backend.base

import com.itmo.kkrukhmalev.places.backend.repository.PlacesListsRepository
import com.itmo.kkrukhmalev.places.backend.repository.PlacesRepository
import com.itmo.kkrukhmalev.places.backend.repository.UsersRepository
import org.junit.jupiter.api.AfterEach
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyNoMoreInteractions

abstract class BaseServiceTest {
    protected val mockUsersRepo = mock<UsersRepository>()
    protected val mockPlacesListsRepo = mock<PlacesListsRepository>()
    protected val mockPlacesRepo = mock<PlacesRepository>()

    @AfterEach
    fun noMoreInteractions() {
        verifyNoMoreInteractions(mockUsersRepo)
        verifyNoMoreInteractions(mockPlacesRepo)
        verifyNoMoreInteractions(mockPlacesListsRepo)
    }
}