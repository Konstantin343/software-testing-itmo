import {render, screen, waitFor} from "@testing-library/react";
import AddPlacesList from "../../../components/PlacesLists/AddPlacesList";
import PlacesService from "../../../services/PlacesService";

describe("<AddPlacesList/>", () => {
    it('render add places list item form', async () => {
        reporter.feature("Render")
        reporter.story("Render add places list")

        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser').mockImplementation(
            async () => ['user', null]);

        render(<AddPlacesList placesService={placesService}/>);

        await waitFor(() => expect(screen.getByText("Add List")).toBeInTheDocument())
        expect(screen.getByText("Name")).toBeInTheDocument()
        expect(screen.getByText("Description")).toBeInTheDocument()
        expect(screen.getByText("Add")).toBeInTheDocument()
    });

    it('not render add places list item form for unauthorized', () => {
        reporter.feature("Render")
        reporter.story("Render add places list")

        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser').mockImplementation(
            async () => [null, null]);

        render(<AddPlacesList placesService={placesService}/>);

        expect(screen.getByText("Hello!")).toBeInTheDocument()
        expect(() => screen.getByText("Add List")).toThrow()
        expect(() => screen.getByText("Name")).toThrow()
        expect(() => screen.getByText("Description")).toThrow()
        expect(() => screen.getByText("Add")).toThrow()
    });
})