import PlacesService from "../../../services/PlacesService";
import {waitFor} from "@testing-library/react";

describe("<PlacesService/>", () => {
    it('currentUser', async () => {
        let placesService = new PlacesService('', 0);
        global.fetch = jest.fn(async (args) => {
                if (args.includes("/current-user")) {
                    return {
                        json: async () => ({user: 'user'}),
                        status: 200
                    }
                }
            }
        );

        await waitFor(async () => expect((await placesService.currentUser())[0])
            .toBe('user'))
    });

    it('getPlacesLists', async () => {
        let placesService = new PlacesService('', 0);
        global.fetch = jest.fn(async (args) => {
                if (args.includes("/places-lists")) {
                    return {
                        json: async () => ({lists: 'a'}),
                        status: 200
                    }
                }
            }
        );

        await waitFor(async () => expect((await placesService.getPlacesLists())[0])
            .toStrictEqual({lists: 'a'}))
    });

    it('getPlacesList', async () => {
        let placesService = new PlacesService('', 0);
        global.fetch = jest.fn(async (args) => {
                if (args.includes("/places-list?id=1")) {
                    return {
                        json: async () => ({list: 'a'}),
                        status: 200
                    }
                }
            }
        );

        await waitFor(async () => expect((await placesService.getPlacesList(1))[0])
            .toStrictEqual({list: 'a'}))
    });
})