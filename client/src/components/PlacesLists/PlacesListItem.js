import React from "react";

export default class PlacesListItem extends React.Component {
    render() {
        return (
            <a href={this.props.eg ? '#' : `/places-list/${this.props.list?.id}`}
               className="w-100 list-group-item list-group-item-action flex-column align-items-start">
                <div className="d-flex w-100 justify-content-between">
                    <h4 className="mb-1">{this.props.list?.name}</h4>
                    <small>{this.props.list?.owner}</small>
                </div>
                <p className="mb-1 text-start">{this.props.list?.description}</p>
            </a>
        )
    }
}