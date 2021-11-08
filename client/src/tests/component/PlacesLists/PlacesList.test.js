import {render, screen, waitFor} from "@testing-library/react";
import PlacesService from "../../../services/PlacesService";
import PlacesList from "../../../components/PlacesLists/PlacesList";
import {BrowserRouter} from "react-router-dom";

describe("<PlacesList/>", () => {
    it('render owner places list', async () => {
        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser')
            .mockImplementation(async () => ['user', null]);
        jest.spyOn(placesService, 'getPlacesList')
            .mockImplementation(async () => [{
                name: 'list',
                description: 'desc',
                owner: 'user',
                places: [
                    {
                        name: 'BB',
                        description: 'best bar',
                        type: 'Bar',
                        city: 'Gorod',
                        street: 'Ulica',
                        number: '21A',
                    }
                ]
            }, null]);
        jest.spyOn(placesService, 'getPlacesLists')
            .mockImplementation(async () => [{
                addedLists: []
            }, null]);

        render(<BrowserRouter><PlacesList placesService={placesService}/></BrowserRouter>);

        await waitFor(() => expect(screen.getByText("Add Place")).toBeInTheDocument())
        expect(screen.getByText("list")).toBeInTheDocument()
        expect(screen.getByText("desc")).toBeInTheDocument()
        expect(screen.getByText("user")).toBeInTheDocument()
        expect(screen.getByText("BB")).toBeInTheDocument()
        expect(screen.getByText("best bar")).toBeInTheDocument()
        expect(screen.getByText("Bar")).toBeInTheDocument()
        expect(screen.getByText("Gorod, Ulica, 21A")).toBeInTheDocument()
    });

    it('render other places list when not added', async () => {
        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser')
            .mockImplementation(async () => ['user1', null]);
        jest.spyOn(placesService, 'getPlacesList')
            .mockImplementation(async () => [{
                name: 'list',
                description: 'desc',
                owner: 'user2',
                places: [
                    {
                        name: 'BB',
                        description: 'best bar',
                        type: 'Bar',
                        city: 'Gorod',
                        street: 'Ulica',
                        number: '21A',
                    }
                ]
            }, null]);
        jest.spyOn(placesService, 'getPlacesLists')
            .mockImplementation(async () => [{
                addedLists: [{id:'1'}]
            }, null]);

        render(<BrowserRouter><PlacesList placesService={placesService}/></BrowserRouter>);

        await waitFor(() => expect(screen.getByText("Add List")).toBeInTheDocument())
        expect(screen.getByText("list")).toBeInTheDocument()
        expect(screen.getByText("desc")).toBeInTheDocument()
        expect(screen.getByText("user2")).toBeInTheDocument()
        expect(screen.getByText("BB")).toBeInTheDocument()
        expect(screen.getByText("best bar")).toBeInTheDocument()
        expect(screen.getByText("Bar")).toBeInTheDocument()
        expect(screen.getByText("Gorod, Ulica, 21A")).toBeInTheDocument()
    });

    it('render other places list when added', async () => {
        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser')
            .mockImplementation(async () => ['user1', null]);
        jest.spyOn(placesService, 'getPlacesList')
            .mockImplementation(async () => [{
                name: 'list',
                description: 'desc',
                owner: 'user2',
                places: [
                    {
                        name: 'BB',
                        description: 'best bar',
                        type: 'Bar',
                        city: 'Gorod',
                        street: 'Ulica',
                        number: '21A',
                    }
                ]
            }, null]);
        jest.spyOn(placesService, 'getPlacesLists')
            .mockImplementation(async () => [{
                addedLists: [{id:null}]
            }, null]);

        render(<BrowserRouter><PlacesList placesService={placesService}/></BrowserRouter>);

        await waitFor(() => expect(screen.getByText("Remove List")).toBeInTheDocument())
        expect(screen.getByText("list")).toBeInTheDocument()
        expect(screen.getByText("desc")).toBeInTheDocument()
        expect(screen.getByText("user2")).toBeInTheDocument()
        expect(screen.getByText("BB")).toBeInTheDocument()
        expect(screen.getByText("best bar")).toBeInTheDocument()
        expect(screen.getByText("Bar")).toBeInTheDocument()
        expect(screen.getByText("Gorod, Ulica, 21A")).toBeInTheDocument()
    });

    it('render other places list when unauthorized', async () => {
        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser')
            .mockImplementation(async () => [null, null]);
        jest.spyOn(placesService, 'getPlacesList')
            .mockImplementation(async () => [{
                name: 'list',
                description: 'desc',
                owner: 'user',
                places: [
                    {
                        name: 'BB',
                        description: 'best bar',
                        type: 'Bar',
                        city: 'Gorod',
                        street: 'Ulica',
                        number: '21A',
                    }
                ]
            }, null]);
        jest.spyOn(placesService, 'getPlacesLists')
            .mockImplementation(async () => [{
                addedLists: []
            }, null]);

        render(<BrowserRouter><PlacesList placesService={placesService}/></BrowserRouter>);

        await waitFor(() => expect(screen.getByText("list")).toBeInTheDocument())
        expect(screen.getByText("desc")).toBeInTheDocument()
        expect(screen.getByText("user")).toBeInTheDocument()
        expect(screen.getByText("BB")).toBeInTheDocument()
        expect(screen.getByText("best bar")).toBeInTheDocument()
        expect(screen.getByText("Bar")).toBeInTheDocument()
        expect(screen.getByText("Gorod, Ulica, 21A")).toBeInTheDocument()
    });
})