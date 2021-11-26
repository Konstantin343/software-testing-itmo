import {render, screen, waitFor} from "@testing-library/react";
import PlacesService from "../../../services/PlacesService";
import App from "../../../components/App/App";

describe("<App/>", () => {
    it('render app', async () => {
        reporter.story("Render app")

        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser').mockImplementation(
            async () => ['user', null]);

        render(<App placesService={placesService}/>);
        expect(screen.getByText("Home")).toBeInTheDocument()
        await waitFor(() => expect(screen.getByText("Hello, user!")).toBeInTheDocument())
    });
})