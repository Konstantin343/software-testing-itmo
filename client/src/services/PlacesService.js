export default class PlacesService {
    constructor(host, port) {
        this.server = host + ":" + port
    }

    async post(method, body) {
        return await fetch(`${this.server}/${method}`,
            {
                method: "POST",
                credentials: "include",
                body: JSON.stringify(body),
                headers: {
                    "Content-Type": "application/json"
                }
            })
    }

    async get(method) {
        return await fetch(`${this.server}/${method}`, {
            credentials: "include"
        })
    }

    async signOut() {
        const signOutResult = await this.post(`sign-out`)
        if (signOutResult.status !== 200) {
            return [null, (await signOutResult.json()).error]
        } else {
            return [null, null]
        }
    }

    async currentUser() {
        const currentUser = await this.get('current-user')
        if (currentUser.status !== 200) {
            return [null, await currentUser.json()]
        } else {
            const user = (await currentUser.json()).user
            if (user !== "")
                return [user, null]
            else
                return [null, null]
        }
    }

    async getPlacesLists(user) {
        const result = await this.get(`places-lists` + (user != null ? ('?user=' + user) : ''))
        if (result.status !== 200) {
            return [null, (await result.json()).error]
        } else {
            return [await result.json(), null]
        }
    }

    async getPlacesList(id) {
        const result = await this.get(`places-list` + (id != null ? ('?id=' + id) : ''))
        if (result.status !== 200) {
            return [null, (await result.json()).error]
        } else {
            return [await result.json(), null]
        }
    }

    async addPlaceList(name, description) {
        const result = await this.post(
            `add-places-list`,
            {
                name: name,
                description: description
            }
        )
        if (result.status !== 200) {
            return [null, (await result.json()).error]
        } else {
            return [null, null]
        }
    }

    async addPlace(listId, name, description, type, city, street, number) {
        const result = await this.post(
            `add-place`,
            {
                listId: listId,
                name: name,
                description: description,
                type: type,
                city: city,
                street: street,
                number: number,
            }
        )
        if (result.status !== 200) {
            return [null, (await result.json()).error]
        } else {
            return [null, null]
        }
    }

    async addListToAdded(id) {
        const result = await this.post(
            `add-list-to-added`,
            {
                id: id
            }
        )
        if (result.status !== 200) {
            return [null, (await result.json()).error]
        } else {
            return [null, null]
        }
    }

    async removeListFromAdded(id) {
        const result = await this.post(
            `remove-list-from-added`,
            {
                id: id
            }
        )
        if (result.status !== 200) {
            return [null, (await result.json()).error]
        } else {
            return [null, null]
        }
    }
}