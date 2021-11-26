import {render, screen} from "@testing-library/react";
import PlaceItem from "../../../components/Places/PlaceItem";

describe("<PlaceItem/>", () => {
    it('render place item', () => {
        reporter.story("Render place item")

        const place = {
            name: 'BB',
            description: 'best bar',
            type: 'Bar',
            city: 'Gorod',
            street: 'Ulica',
            number: '21A',
        }
        render(<PlaceItem place={place}/>);
        expect(screen.getByText("BB")).toBeInTheDocument()
        expect(screen.getByText("Bar")).toBeInTheDocument()
        expect(screen.getByText("best bar")).toBeInTheDocument()
        expect(screen.getByText("Gorod, Ulica, 21A")).toBeInTheDocument()
    });
})