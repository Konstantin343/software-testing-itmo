package com.itmo.kkrukhmalev.places.backend.domain;

import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name = "Lists", uniqueConstraints = [
    UniqueConstraint(name = "unique_name_ownerId", columnNames = ["name", "ownerId"])
])
class PlacesList : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(name = "name")
    lateinit var name: String

    @Column(name = "description")
    lateinit var description: String

    @ManyToOne
    @JoinColumn(
        name = "ownerId",
        referencedColumnName = "id",
        foreignKey = ForeignKey(
            name = "fk_Lists_ownerId",
        )
    )
    lateinit var owner: User

    @OneToMany(cascade = [CascadeType.PERSIST], mappedBy = "placesList")
    lateinit var places: List<Place>
//
//    @ManyToMany
//    lateinit var users: HashSet<User>
}