# Tests to implement

## Component tests

### Tests for PlacesController

#### 1. Add new place

_Pre:_ `PlacesService.addPlace(user, place)` do nothing

_Steps:_
1. Send `place` and session with `user=user` to `/add-place`

_Expected:_
1. Status: `200 OK`; Content: `""`
2. `PlacesService.addPlace(user, place)` was called

---

#### 2. Don't add new place when error

_Pre:_ `PlacesService.addPlace(user, place)` throw error `errorText`

_Steps:_
1. Send `place` and session with `user=user` to `/add-place`

_Expected:_
1. Status: `400 BAD REQUEST`; Content: `"{"error":"errorText"}"`
2. `PlacesService.addPlace(user, place)` was called

---

#### 3. Don't add new place when error

_Pre:_  -

_Steps:_
1. Send `place` to `/add-place` withou session

_Expected:_
1. Status: `401 UNAUTHORIZED`; Content: `"{"error":"Sign in to add places"}"`