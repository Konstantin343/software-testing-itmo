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
1. Send `place` to `/add-place` without session

_Expected:_
1. Status: `401 UNAUTHORIZED`; Content: `"{"error":"Sign in to add places"}"`

## System tests

### Tests with places lists

#### 1. Manipulations with places list

_Pre:_ User `user1` with list `list1` and `user2` exists in database

_Steps:_
1. Get all places lists
2. Sign in for `user2`
3. Get all places lists
4. Get my places lists
5. Create list `list2` for `user2`
6. Create place `place` in `list2`
7. Get all places lists
8. Get my places lists
9. Add `list1` to added
10. Get my places lists
11. Remove `list1` from added
12. Get my places lists

_Expected:_
1. Only `list1` in `lists`
2. Successful sing in, return session
3. Only `list1` in `lists`
4. Empty `lists`, empty `addedLists`
5. List `list2` created successful
6. Place `place` created successful
7. Both `list1`, `list2` in `lists`
8. Only `list2` in lists, empty `addedLists`
9. List `list1` added successful
10. Only `list2` in lists, only `list1` in `addedLists`
11. List `list1` removed successful
12. Only `list2` in lists, empty `addedLists`
