import {render, screen, waitFor} from "@testing-library/react";
import PlacesService from "../../../services/PlacesService";
import Preview from "../../../components/Preview/Preview";

describe("<Preview/>", () => {
    it('render unauthorized preview', () => {
        reporter.story("Render preview")

        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser')
            .mockImplementation(async () => [null, null]);

        render(<Preview placesService={placesService}/>);

        expect(screen.getByText("Hello!")).toBeInTheDocument()
        expect(screen.getByText("Places App")).toBeInTheDocument()
    });

    it('render authorized preview', async () => {
        reporter.story("Render preview")

        let placesService = new PlacesService('', 0);
        jest.spyOn(placesService, 'currentUser')
            .mockImplementation(async () => ['user', null]);

        render(<Preview placesService={placesService}/>);

        await waitFor(() => expect(screen.getByText("Hello, user!")).toBeInTheDocument())
        expect(screen.getByText("Places App")).toBeInTheDocument()
    });
})