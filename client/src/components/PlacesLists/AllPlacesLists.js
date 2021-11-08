import React from "react";
import BaseComponent from "../Base/BaseComponent";
import PlacesListItem from "./PlacesListItem";

export default class AllPlacesLists extends BaseComponent {
    constructor(props) {
        super(props);

        this.state = {
            lists: []
        }
    }

    componentDidMount() {
        super.reloadUser()
        this.placesService.getPlacesLists(null).then(pl => {
            this.setState({
                lists: pl[0].lists ?? []
            })
        })
    }

    render() {
        return (
            <center>
                <div className="list-group m-lg-3 w-50">
                    {this.state.lists.length === 0 ? "No lists" :
                        this.state.lists.map(l => {
                            return (
                                <PlacesListItem key={l.name + "-" + l.owner} list={l}/>
                            )
                        })}
                </div>
            </center>
        )
    }
}