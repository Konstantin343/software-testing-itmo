package com.itmo.kkrukhmalev.places.backend.service

import com.itmo.kkrukhmalev.places.backend.domain.Place
import com.itmo.kkrukhmalev.places.backend.domain.PlacesList
import com.itmo.kkrukhmalev.places.backend.domain.User
import com.itmo.kkrukhmalev.places.backend.repository.PlacesListsRepository
import com.itmo.kkrukhmalev.places.backend.repository.PlacesRepository
import com.itmo.kkrukhmalev.places.backend.repository.UsersRepository
import com.itmo.kkrukhmalev.places.backend.requestModel.AddPlaceRequestModel
import com.itmo.kkrukhmalev.places.backend.requestModel.AddPlacesListRequestModel
import com.itmo.kkrukhmalev.places.backend.responseModel.ListResponseModel
import com.itmo.kkrukhmalev.places.backend.responseModel.ListsResponseModel
import com.itmo.kkrukhmalev.places.backend.responseModel.PlaceResponseModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PlacesService(
    @Autowired
    var usersRepository: UsersRepository,
    @Autowired
    var placesListsRepository: PlacesListsRepository,
    @Autowired
    var placesRepository: PlacesRepository,
) {

    fun getPlacesLists(user: String?): ListsResponseModel {
        val lists = if (user == null) placesListsRepository.findAll()
        else placesListsRepository.findAllByOwnerLogin(user)
        val addedLists = if (user == null) listOf()
        else usersRepository.findUserByLogin(user).addedLists
        return ListsResponseModel(
            lists = lists.map { ListResponseModel(it.id, it.name, it.description, it.owner.login) },
            addedLists = addedLists.map { ListResponseModel(it.id, it.name, it.description, it.owner.login) }
        )
    }

    fun getPlacesList(id: Long?): ListResponseModel {
        val placesList = placesListsRepository.findPlacesListById(id!!)
        return ListResponseModel(
            placesList.id,
            placesList.name,
            placesList.description,
            placesList.owner.login,
            placesList.places.map {
                PlaceResponseModel(
                    it.id,
                    it.name,
                    it.description,
                    it.type,
                    it.city,
                    it.street,
                    it.number
                )
            }
        )
    }

    fun addListToAdded(sessionUser: String, id: Long) {
        val user = usersRepository.findUserByLogin(sessionUser)
        val list = placesListsRepository.findPlacesListById(id)
        user.addedLists.add(list)
        usersRepository.save(user)
    }

    fun removeListFromAdded(sessionUser: String, id: Long) {
        val user = usersRepository.findUserByLogin(sessionUser)
        user.addedLists.removeIf { it.id == id }
        usersRepository.save(user)
    }

    fun addPlacesList(sessionUser: String, addPlacesListRequestModel: AddPlacesListRequestModel) {
        val user = usersRepository.findUserByLogin(sessionUser)
        placesListsRepository.save(PlacesList().apply {
            name = addPlacesListRequestModel.name
            description = addPlacesListRequestModel.description
            owner = User().apply { id = user.id }
        })
    }

    fun addPlace(addPlaceRequestModel: AddPlaceRequestModel) {
        val list = placesListsRepository.findPlacesListById(addPlaceRequestModel.listId)
        placesRepository.save(Place().apply {
            placesList = list
            name = addPlaceRequestModel.name
            description = addPlaceRequestModel.description
            type = addPlaceRequestModel.type
            city = addPlaceRequestModel.city
            street = addPlaceRequestModel.street
            number = addPlaceRequestModel.number
        })
    }
}