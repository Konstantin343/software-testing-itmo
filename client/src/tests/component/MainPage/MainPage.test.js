import {render, screen, waitFor} from "@testing-library/react";
import PlacesService from "../../../services/PlacesService";
import MainPage from "../../../components/MainPage/MainPage";

describe("<MainPage/>", () => {
    it('render authorized main page', async () => {
        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser')
            .mockImplementation(async () => ['user', null]);

        render(<MainPage placesService={placesService}/>);

        await waitFor(() => expect(screen.getByText("Example List")).toBeInTheDocument())
        expect(screen.getByText("Example Place")).toBeInTheDocument()
    });

    it('render unauthorized main page', () => {
        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser').mockImplementation(
            async () => [null, null]);

        render(<MainPage placesService={placesService}/>);
        expect(screen.getByText("Sign in")).toBeInTheDocument()
        expect(screen.getByText("Sign up")).toBeInTheDocument()
    });
})