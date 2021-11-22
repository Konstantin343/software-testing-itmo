import {render, screen, waitFor} from "@testing-library/react";
import Nav from "../../../components/Nav/Nav";
import PlacesService from "../../../services/PlacesService";

describe("<Nav/>", () => {
    it('render unauthorized nav items', () => {
        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser').mockImplementation(
            async () => [null, null]);

        render(<Nav placesService={placesService}/>);
        expect(screen.getByText("Home")).toBeInTheDocument()
        expect(screen.getByText("All Lists")).toBeInTheDocument()
    });

    it('render authorized nav items', async () => {
        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser')
            .mockImplementation(async () => ['user', null]);

        render(<Nav placesService={placesService}/>);

        expect(screen.getByText("Home")).toBeInTheDocument()
        expect(screen.getByText("All Lists")).toBeInTheDocument()
        await waitFor(() => expect(screen.getByText("My Lists")).toBeInTheDocument())
        expect(screen.getByText("Add New List")).toBeInTheDocument()
        expect(screen.getByText("Sign out (user)")).toBeInTheDocument()
    });
})