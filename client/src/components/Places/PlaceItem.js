import React from "react";

export default class PlacesItem extends React.Component {
    render() {
        return (
            <a href='#'
               className="w-100 list-group-item list-group-item-action flex-column align-items-start">
                <div className="d-flex w-100 justify-content-between">
                    <h4 className="mb-1">{this.props.place.name}</h4>
                    <small>{this.props.place.type}</small>
                </div>
                <div className="d-flex w-100 justify-content-between">
                    <p className="mb-1 text-start w-75">{this.props.place.description}</p>
                    <small>{this.props.place.city}, {this.props.place.street}, {this.props.place.number}</small>
                </div>
            </a>
        )
    }
}