import {render, screen} from "@testing-library/react";
import PlacesListItem from "../../../components/PlacesLists/PlacesListItem";

describe("<PlacesListItem/>", () => {
    it('render places list item', () => {
        const list = {
            name: 'Bars',
            description: 'List of bars',
            owner: 'user'
        }

        render(<PlacesListItem list={list}/>);

        expect(screen.getByText("Bars")).toBeInTheDocument()
        expect(screen.getByText("List of bars")).toBeInTheDocument()
        expect(screen.getByText("user")).toBeInTheDocument()
    });
})