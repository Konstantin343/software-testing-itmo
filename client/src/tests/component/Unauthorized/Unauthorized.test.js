import {render, screen} from "@testing-library/react";
import PlacesService from "../../../services/PlacesService";
import Unauthorized from "../../../components/Unauthorized/Unauthorized";

describe("<Unauthorized/>", () => {
    it('render unauthorized', () => {
        reporter.feature("Render")
        reporter.story("Render unauthorized page")

        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser').mockImplementation(
            async () => [null, null]);

        render(<Unauthorized placesService={placesService}/>);
        expect(screen.getByText("Sign in")).toBeInTheDocument()
        expect(screen.getByText("Sign up")).toBeInTheDocument()
    });
})