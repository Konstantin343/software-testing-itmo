import {render, screen} from "@testing-library/react";
import PlacesService from "../../../services/PlacesService";
import SignUp from "../../../components/SignUp/SignUp";

describe("<SignUp/>", () => {
    it('render sign up page', () => {
        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser').mockImplementation(
            async () => [null, null]);

        render(<SignUp placesService={placesService}/>);
        expect(screen.getByText("Login")).toBeInTheDocument()
        expect(screen.getByText("Password")).toBeInTheDocument()
        expect(screen.getByText("Sign up")).toBeInTheDocument()
    });
})