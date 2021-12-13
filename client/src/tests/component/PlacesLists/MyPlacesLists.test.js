import {render, screen, waitFor} from "@testing-library/react";
import PlacesService from "../../../services/PlacesService";
import MyPlacesLists from "../../../components/PlacesLists/MyPlacesLists";

describe("<MyPlacesLists/>", () => {
    it('render my places lists', async () => {
        reporter.feature("Render")
        reporter.story("Render my places lists")

        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser')
            .mockImplementation(async () => ['user1', null]);
        jest.spyOn(placesService, 'getPlacesLists')
            .mockImplementation(async () => [{
                lists: [{
                    name: 'list1',
                    description: 'desc1',
                    owner: 'user1'
                }],
                addedLists: [{
                    name: 'list2',
                    description: 'desc2',
                    owner: 'user2'
                }]
            }, null]);

        render(<MyPlacesLists placesService={placesService}/>);

        await waitFor(() => expect(screen.getByText("My lists")).toBeInTheDocument())
        expect(screen.getByText("list1")).toBeInTheDocument()
        expect(screen.getByText("desc1")).toBeInTheDocument()
        expect(screen.getByText("user1")).toBeInTheDocument()

        await waitFor(() => expect(screen.getByText("Added lists")).toBeInTheDocument())
        expect(screen.getByText("list2")).toBeInTheDocument()
        expect(screen.getByText("desc2")).toBeInTheDocument()
        expect(screen.getByText("user2")).toBeInTheDocument()
    });
})