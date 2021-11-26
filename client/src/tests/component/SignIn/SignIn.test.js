import {render, screen} from "@testing-library/react";
import PlacesService from "../../../services/PlacesService";
import SignIn from "../../../components/SignIn/SignIn";

describe("<SignIn/>", () => {
    it('render sign in page', () => {
        reporter.story("Render sign in page")

        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser').mockImplementation(
            async () => [null, null]);

        render(<SignIn placesService={placesService}/>);
        expect(screen.getByText("Login")).toBeInTheDocument()
        expect(screen.getByText("Password")).toBeInTheDocument()
        expect(screen.getByText("Sign in")).toBeInTheDocument()
    });
})