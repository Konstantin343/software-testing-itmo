import React from "react";
import BaseComponent from "../Base/BaseComponent";
import {withRouter} from "react-router-dom";
import PlacesItem from "../Places/PlaceItem";
import AddPlace from "../Places/AddPlace";
import PlacesListItem from "./PlacesListItem";

class PlacesList extends BaseComponent {
    constructor(props) {
        super(props);

        this.state = {
            list: null,
            places: [],
            error: null,
            added: false
        }

        this.addOrRemoveList = this.addOrRemoveList.bind(this)
    }

    async addOrRemoveList() {
        let res
        if (this.state.added)
            res = await this.placesService.removeListFromAdded(this.props.match.params.id)
        else
            res = await this.placesService.addListToAdded(this.props.match.params.id)
        if (res[1]) {
            this.setState({
                error: res[1].error
            })
        } else {
            this.setState({
                added: !this.state.added
            })
            if (!this.state.added) {
                window.location.href = '/my-places-lists'
            }
        }
    }

    componentDidMount() {
        this.placesService.getPlacesList(this.props.match.params.id).then(pl => {
            if (pl[0]) {
                this.setState({
                    list: pl[0],
                    places: pl[0].places ?? []
                })
                this.placesService.currentUser().then(u => {
                    this.setState({
                        user: u[0]
                    })
                    this.placesService.getPlacesLists(u[0]).then(al => {
                        this.setState({
                            added: (al[0].addedLists ?? []).map(l => l.id.toString()).includes(this.props.match.params.id.toString())
                        })
                    })
                })
            } else {
                this.setState({
                    error: pl[1].error
                })
            }
        })
    }

    render() {
        if (this.state.list?.owner === this.state.user) {
            return (
                <div className="m-lg-3">
                    <AddPlace placesService={this.placesService}/>
                    <div style={{float: 'right', 'margin-right':'15px', width: '60%'}}>
                        <div className="w-100 m-lg-3">
                            <PlacesListItem eg={true} list={this.state.list}/>
                        </div>
                        <div className="list-group m-lg-3">
                            {this.state.places.map(p => {
                                return (
                                    <PlacesItem key={p.name} place={p}/>
                                )
                            })}
                        </div>
                    </div>
                </div>
            )
        } else if (this.state.user) {
            return (
                <center>
                    <div>{this.state.error}</div>
                    <div className="login-button-holder w-50 mt-3">
                        <button className={"login-button"} onClick={this.addOrRemoveList}>{this.state.added ? "Remove List" : "Add List"}</button>
                    </div>
                    <div className="w-50 m-lg-3">
                        <PlacesListItem eg={true} list={this.state.list}/>
                    </div>
                    <div className="list-group w-50 m-lg-3">
                        {this.state.places.map(p => {
                            return (
                                <PlacesItem key={p.name} place={p}/>
                            )
                        })}
                    </div>
                </center>
            )
        } else {
            return (
                <center>
                    <div className="w-50 m-lg-3">
                        <PlacesListItem eg={true} list={this.state.list}/>
                    </div>
                    <div className="list-group w-50 m-lg-3">
                        {this.state.places.map(p => {
                            return (
                                <PlacesItem key={p.name} place={p}/>
                            )
                        })}
                    </div>
                </center>
            )
        }
    }
}

export default withRouter(PlacesList)