// noinspection JSCheckFunctionSignatures

const express = require('express')
const cookieParser = require('cookie-parser')
const session = require('express-session');
const bodyParser = require('body-parser');
const cors = require('cors')
const {PlacesDatabase} = require("./database");
const app = express()
const sjcl = require('sjcl')

function start(port, dbString, sessionSecret, allowedHosts) {
    const placesDb = new PlacesDatabase(dbString)

    app.use(bodyParser.json());
    app.use(express.urlencoded());

    app.use(cookieParser());
    app.use(session({
        secret: sessionSecret,
        name: 'session',
        cookie: {
            expires: new Date(Date.now() + 60 * 60 * 1000),
            sameSite: true
        }
    }));
    app.options('http://localhost:' + port);
    app.use(function (req, res, next) {
        res.setHeader('Access-Control-Allow-Origin', allowedHosts);
        res.setHeader('Access-Control-Allow-Credentials', 'true');
        res.setHeader('Access-Control-Allow-Methods', 'GET,POST');
        res.setHeader('Access-Control-Allow-Headers', 'Content-Type');
        res.setHeader('Cache-Control', 'no-store');

        next();
    });

    app.post('/sign-up', (req, res) => {
        const passwordBitArray = sjcl.hash.sha256.hash(req.body.password)
        const passwordHash = sjcl.codec.hex.fromBits(passwordBitArray)
        placesDb.createUser({
            login: req.body.login,
            password: passwordHash
        }, rows => {
            req.session.user = req.body.login
            res.redirect(301, req.body.redirectTo)
        }, err => {
            res.redirect(301, req.body.redirectTo + 'sign-up')
        });
    })

    app.post('/sign-in', (req, res) => {
        const passwordBitArray = sjcl.hash.sha256.hash(req.body.password)
        const passwordHash = sjcl.codec.hex.fromBits(passwordBitArray)
        placesDb.getUserByLoginAndPassword({
            login: req.body.login,
            password: passwordHash
        }, rows => {
            if (rows.length !== 1) {
                res.redirect(301, req.body.redirectTo + 'sign-in')
            } else {
                req.session.user = rows[0].login
                res.redirect(301, req.body.redirectTo)
            }
        }, err => {
            res.redirect(301, req.body.redirectTo + 'sign-in')
        });
    })

    app.post('/sign-out', (req, res) => {
        res.status(200)
        req.session.user = null
        res.end()
    })

    app.get('/current-user', (req, res) => {
        res.status(200)
        res.send(JSON.stringify({user: req.session.user}))
    })

    app.get('/places-lists', (req, res) => {
        placesDb.getPlacesLists(req.query.user,
            lists => {
                placesDb.getAddedPlacesLists(req.query.user ?? -1, added => {
                    res.status(200)
                    res.contentType("application/json")
                    res.send(
                        JSON.stringify({
                            lists: lists.map((row) => {
                                return {
                                    id: row.listId,
                                    name: row.name,
                                    description: row.description,
                                    owner: row.login
                                };
                            }),
                            addedLists: added.map((row) => {
                                return {
                                    id: row.listId,
                                    name: row.name,
                                    description: row.description,
                                    owner: row.login
                                };
                            })
                        })
                    );
                }, err => {
                    res.status(500)
                    res.send(JSON.stringify({error: err.message}))
                })
            }, err => {
                res.status(500)
                res.send(JSON.stringify({error: err.message}))
            });
    })

    app.get('/places-list', (req, res) => {
        placesDb.getPlacesList(req.query.id,
            lists => {
                placesDb.getPlacesFromList(req.query.id, places => {
                    res.status(200)
                    res.contentType("application/json")
                    res.send(
                        JSON.stringify(
                            {
                                name: lists[0]?.name,
                                description: lists[0]?.description,
                                owner: lists[0]?.login,
                                places: places.map((row) => {
                                    return {
                                        name: row.name,
                                        description: row.description,
                                        type: row.type,
                                        city: row.city,
                                        street: row.street,
                                        number: row.number
                                    };
                                })
                            })
                    );
                }, err => {
                    res.status(500)
                    res.send(JSON.stringify({error: err.message}))
                })
            }, err => {
                res.status(500)
                res.send(JSON.stringify({error: err.message}))
            });
    })

    app.post('/add-list-to-added',
        (req, res) => {
            if (req.session.user) {
                placesDb.getUserByLogin(req.session.user, u => {
                    placesDb.addListToAdded(req.body.id, u[0].userId,
                        _ => {
                            res.status(200)
                            res.end()
                        }, error => {
                            res.status(400)
                            res.send(JSON.stringify({error: "Can not add list"}))
                        })
                }, error => {
                    res.status(400)
                    res.send(JSON.stringify({error: "Can not find user"}))
                })
            } else {
                res.status(401)
                res.send(JSON.stringify({error: "Sign in to add lists"}))
            }
        }
    )

    app.post('/remove-list-from-added',
        (req, res) => {
            if (req.session.user) {
                placesDb.getUserByLogin(req.session.user, u => {
                    placesDb.removeListFromAdded(req.body.id, u[0].userId,
                        _ => {
                            res.status(200)
                            res.end()
                        }, error => {
                            res.status(400)
                            res.send(JSON.stringify({error: "Can not remove list"}))
                        })
                }, error => {
                    res.status(400)
                    res.send(JSON.stringify({error: "Can not find user"}))
                })
            } else {
                res.status(401)
                res.send(JSON.stringify({error: "Sign in to add lists"}))
            }
        }
    )

    app.post('/add-places-list',
        (req, res) => {
            if (req.session.user) {
                placesDb.getUserByLogin(req.session.user, u => {
                    placesDb.addPlaceList(req.body, u[0].userId,
                        _ => {
                            res.status(200)
                            res.end()
                        }, error => {
                            res.status(400)
                            res.send(JSON.stringify({error: "Can not create list"}))
                        })
                }, error => {
                    res.status(400)
                    res.send(JSON.stringify({error: "Can not find user"}))
                })
            } else {
                res.status(401)
                res.send(JSON.stringify({error: "Sign in to create lists"}))
            }
        }
    )

    app.post('/add-place',
        (req, res) => {
            if (req.session.user) {
                placesDb.getUserByLogin(req.session.user, _ => {
                    placesDb.addPlace(req.body.listId, req.body,
                        _ => {
                            res.status(200)
                            res.end()
                        }, error => {
                            res.status(400)
                            res.send(JSON.stringify({error: "Can not add place"}))
                        })
                }, error => {
                    res.status(400)
                    res.send(JSON.stringify({error: "Can not find user"}))
                })
            } else {
                res.status(401)
                res.send(JSON.stringify({error: "Sign in to create lists"}))
            }
        }
    )

    app.listen(port, () => {
        console.log(`App listening at http://localhost:${port}`)
    })
}

exports.start = start