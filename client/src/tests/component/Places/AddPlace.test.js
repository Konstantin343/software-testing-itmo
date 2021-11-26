import {render, screen} from "@testing-library/react";
import AddPlace from "../../../components/Places/AddPlace";
import {BrowserRouter} from "react-router-dom";

describe("<AddPlace/>", () => {
    it('render add place item form', () => {
        reporter.feature("Render")
        reporter.story("Render add place form")

        render(<BrowserRouter><AddPlace/></BrowserRouter>);

        expect(screen.getByText("Add Place")).toBeInTheDocument()
        expect(screen.getByText("Name")).toBeInTheDocument()
        expect(screen.getByText("Description")).toBeInTheDocument()
        expect(screen.getByText("Type")).toBeInTheDocument()
        expect(screen.getByText("City")).toBeInTheDocument()
        expect(screen.getByText("Street")).toBeInTheDocument()
        expect(screen.getByText("Number")).toBeInTheDocument()
        expect(screen.getByText("Add")).toBeInTheDocument()
    });
})