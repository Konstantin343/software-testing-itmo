import BaseComponent from "../Base/BaseComponent";
import PlacesListItem from "./PlacesListItem";
import Unauthorized from "../Unauthorized/Unauthorized";
import React from "react";

export default class MyPlacesLists extends BaseComponent {
    constructor(props) {
        super(props);

        this.state = {
            myLists: [],
            addedLists: []
        }
    }

    componentDidMount() {
        this.placesService.currentUser().then(u => {
            this.setState({
                user: u[0]
            })
            this.placesService.getPlacesLists(u[0]).then(pl => {
                this.setState({
                    myLists: pl[0].lists ?? [],
                    addedLists: pl[0].addedLists ?? []
                })
            })
        })
    }

    render() {
        if (this.state.user) {
            return (
                <div className="w-100">
                    <div className="list-group m-lg-3" style={{width: '45%', float:'left'}}>
                        <h3>My lists</h3>
                        {this.state.myLists.map(l => {
                            return (
                                <PlacesListItem key={l.name + "-" + l.owner} list={l}/>
                            )
                        })}
                    </div>
                    <div className="list-group m-lg-3" style={{width: '45%', float:'right'}}>
                        <h3>Added lists</h3>
                        {this.state.addedLists.map(l => {
                            return (
                                <PlacesListItem key={l.name + "-" + l.owner} list={l}/>
                            )
                        })}
                    </div>
                </div>
            )
        } else {
            return (
                <Unauthorized placesService={this.placesService}/>
            )
        }
    }
}