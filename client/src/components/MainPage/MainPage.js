import React from "react";
import Unauthorized from "../Unauthorized/Unauthorized";
import BaseComponent from "../Base/BaseComponent";
import Preview from "../Preview/Preview";
import PlacesListItem from "../PlacesLists/PlacesListItem";
import PlaceItem from "../Places/PlaceItem";

export default class MainPage extends BaseComponent {
    componentDidMount() {
        super.reloadUser()
    }

    render() {
        if (this.state.user) {
            return (
                <div>
                    <Preview placesService={this.placesService}/>
                    <center>
                        <div className="list-group m-lg-3 w-50">
                            <PlacesListItem eg={true} list={{
                                id: -1,
                                name: "Example List",
                                owner: "John Doe",
                                description:
                                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                                    "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                                    "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                            }}/>
                        </div>
                        <div className="list-group w-50">
                            <PlaceItem place={{
                                name: 'Example Place',
                                description: 'Best place in this city',
                                type: 'Cafe',
                                city: 'Moscow',
                                street: 'Tverskaya',
                                number: '1A'
                            }} />
                        </div>
                    </center>
                </div>
            )
        } else {
            return (
                <Unauthorized placesService={this.placesService}/>
            )
        }
    }
}