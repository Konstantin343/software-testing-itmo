package com.itmo.kkrukhmalev.places.backend.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Places")
class Place : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(name = "name")
    lateinit var name: String

    @Column(name = "description")
    lateinit var description: String

    @Column(name = "type")
    lateinit var type: String

    @Column(name = "city")
    lateinit var city: String

    @Column(name = "street")
    lateinit var street: String

    @Column(name = "number")
    lateinit var number: String

    @ManyToOne
    @JoinColumn(
        name = "listId",
        referencedColumnName = "id",
        foreignKey = ForeignKey(
            name = "fk_Places_listId",
        )
    )
    lateinit var placesList: PlacesList
}