import {render, screen, waitFor} from "@testing-library/react";
import PlacesService from "../../../services/PlacesService";
import AllPlacesLists from "../../../components/PlacesLists/AllPlacesLists";

describe("<AllPlacesLists/>", () => {
    it('render all places lists', async () => {
        reporter.story("Render all places lists")

        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser')
            .mockImplementation(async () => ['user1', null]);
        jest.spyOn(placesService, 'getPlacesLists')
            .mockImplementation(async () => [{
                lists: [{
                    name: 'list1',
                    description: 'desc1',
                    owner: 'user1'
                }, {
                    name: 'list2',
                    description: 'desc2',
                    owner: 'user2'
                }],
            }, null]);

        render(<AllPlacesLists placesService={placesService}/>);

        await waitFor(() => expect(screen.getByText("list1")).toBeInTheDocument())
        expect(screen.getByText("desc1")).toBeInTheDocument()
        expect(screen.getByText("user1")).toBeInTheDocument()

        await waitFor(() => expect(screen.getByText("list2")).toBeInTheDocument())
        expect(screen.getByText("desc2")).toBeInTheDocument()
        expect(screen.getByText("user2")).toBeInTheDocument()
    });

    it('render empty places lists', async () => {
        reporter.story("Render all places lists")

        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser')
            .mockImplementation(async () => ['user1', null]);
        jest.spyOn(placesService, 'getPlacesLists')
            .mockImplementation(async () => [{
                lists: [],
            }, null]);

        render(<AllPlacesLists placesService={placesService}/>);

        await waitFor(() => expect(screen.getByText("No lists")).toBeInTheDocument())
    });
})