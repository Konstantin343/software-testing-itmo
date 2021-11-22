const sqlite = require('sqlite3')

class PlacesDatabase {
    constructor(db_path) {
        this.db = new sqlite.Database(db_path)

        this.query(
            'CREATE TABLE IF NOT EXISTS Users (' +
            '   userId INTEGER PRIMARY KEY AUTOINCREMENT,' +
            '   login VARCHAR(64) UNIQUE,' +
            '   password VARCHAR(64)' +
            ')'
        )
        this.query(
            'CREATE TABLE IF NOT EXISTS Lists (' +
            '   listId INTEGER PRIMARY KEY AUTOINCREMENT,' +
            '   name VARCHAR(64),' +
            '   description VARCHAR(1024),' +
            '   ownerId INTEGER,' +
            '   FOREIGN KEY (ownerId) REFERENCES Users(userId),' +
            '   CONSTRAINT list_owner UNIQUE(name,ownerId)' +
            ')'
        )
        this.query(
            'CREATE TABLE IF NOT EXISTS ListsToUsers (' +
            '   listId INTEGER,' +
            '   userId INTEGER,' +
            '   PRIMARY KEY (listId, userId),' +
            '   FOREIGN KEY (userId) REFERENCES Users(userId),' +
            '   FOREIGN KEY (listId) REFERENCES Lists(listId)' +
            ')'
        )
        this.query(
            'CREATE TABLE IF NOT EXISTS Places (' +
            '   placeId INTEGER PRIMARY KEY AUTOINCREMENT,' +
            '   listId INTEGER,' +
            '   name VARCHAR(64),' +
            '   description VARCHAR(1024),' +
            '   type VARCHAR(64),' +
            '   city VARCHAR(64),' +
            '   street VARCHAR(64),' +
            '   number VARCHAR(10),' +
            '   FOREIGN KEY (listId) REFERENCES Lists(listId)' +
            '   CONSTRAINT list_place UNIQUE(listId,name)' +
            ')');
    }

    addPlace(listId, place, onSuccess, onError) {
        this.query(
            `INSERT INTO Places (listId, name, description, type, city, street, number) 
                        VALUES (${listId}, \'${place.name}\', \'${place.description}\', \'${place.type}\', \'${place.city}\', \'${place.street}\', \'${place.number}\')`,
            onSuccess, onError
        )
    }

    getPlacesLists(user, onSuccess, onError) {
        let query = `SELECT * FROM Lists AS l JOIN Users AS u ON l.ownerId = u.userId`
        if (user) {
            query += ` AND u.login = \'${user}\'`
        }
        this.query(
            query,
            onSuccess, onError
        )
    }

    getAddedPlacesLists(user, onSuccess, onError) {
        this.query(
            `SELECT l.listId, l.name, l.description, o.login
                        FROM Lists AS l 
                        NATURAL JOIN ListsToUsers AS lt
                        JOIN Users AS o ON l.ownerId = o.userId 
                        JOIN Users AS u ON lt.userId = u.userId 
                        WHERE u.login = \'${user}\'`,
            onSuccess, onError
        )
    }

    getPlacesList(id, onSuccess, onError) {
        this.query(
            `SELECT * 
                        FROM Lists AS l 
                        JOIN Users AS u ON l.ownerId = u.userId 
                        WHERE l.listId = ${id}`,
            onSuccess, onError
        )
    }

    addPlaceList(list, ownerId, onSuccess, onError) {
        this.query(
            `INSERT INTO Lists (name, description, ownerId) 
                        VALUES (\'${list.name}\', \'${list.description}\', ${ownerId})`,
            onSuccess, onError
        )
    }

    addListToAdded(listId, ownerId, onSuccess, onError) {
        this.query(
            `INSERT INTO ListsToUsers
                        VALUES (${listId}, ${ownerId})`,
            onSuccess, onError
        )
    }

    removeListFromAdded(listId, ownerId, onSuccess, onError) {
        this.query(
            `DELETE FROM ListsToUsers
                        WHERE listId = ${listId} AND userId = ${ownerId}`,
            onSuccess, onError
        )
    }

    getPlacesFromList(listId, onSuccess, onError) {
        this.query(
            `SELECT * FROM Places WHERE listId = ${listId}`,
            onSuccess, onError
        )
    }

    getUserByLogin(login, onSuccess, onError) {
        this.query(
            `SELECT * FROM Users WHERE login = \'${login}\'`,
            onSuccess, onError
        )
    }

    getUserByLoginAndPassword(user, onSuccess, onError) {
        this.query(
            `SELECT * FROM Users WHERE login = \'${user.login}\' AND password = \'${user.password}\'`,
            onSuccess, onError
        )
    }

    createUser(user, onSuccess, onError) {
        this.query(
            `INSERT INTO Users (login, password) VALUES ('${user.login}', '${user.password}')`,
            onSuccess, onError
        )
    }

    query(queryString, onSuccess, onError) {
        this.db.all(queryString, [], (e, r) => {
            if (r && onSuccess) onSuccess(r)
            else if (e) {
                console.log(e)
                if (onError) {
                    onError(e)
                }
            }
        })
    }
}

exports.PlacesDatabase = PlacesDatabase