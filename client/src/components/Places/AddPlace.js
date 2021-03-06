import React from "react";
import {withRouter} from "react-router-dom";
import BaseComponent from "../Base/BaseComponent";

class AddPlace extends BaseComponent {
    constructor(props) {
        super(props);

        this.state = {
            name: '',
            description: '',
            type: '',
            city: '',
            street: '',
            number: ''
        }

        this.submitAdd = this.submitAdd.bind(this)
        this.changeName = this.changeName.bind(this)
        this.changeDescription = this.changeDescription.bind(this)
        this.changeType = this.changeType.bind(this)
        this.changeCity = this.changeCity.bind(this)
        this.changeStreet = this.changeStreet.bind(this)
        this.changeNumber = this.changeNumber.bind(this)
    }

    async submitAdd() {
        let [_, err] = await this.placesService.addPlace(
            this.props.match.params.id,
            this.state.name,
            this.state.description,
            this.state.type,
            this.state.city,
            this.state.street,
            this.state.number,
        )
        if (err) {
            this.setState({
                error: err
            })
        } else {
            window.location.href = '/places-list/' + this.props.match.params.id
        }
    }

    changeName(event) {
        this.setState({
            name: event.target.value
        });
    }

    changeDescription(event) {
        this.setState({
            description: event.target.value
        });
    }

    changeType(event) {
        this.setState({
            type: event.target.value
        });
    }

    changeCity(event) {
        this.setState({
            city: event.target.value
        });
    }

    changeStreet(event) {
        this.setState({
            street: event.target.value
        });
    }

    changeNumber(event) {
        this.setState({
            number: event.target.value
        });
    }

    render() {
        return (
            <div style={{float: 'left', width: '35%'}}>
                <h3>Add Place</h3>
                <div className="w-100">
                    <div className={"label"}><label htmlFor="name">Name</label></div>
                    <input type="text" id="name" name="name" className="form-control" placeholder="Name"
                           onChange={this.changeName}/>
                </div>
                <div className="w-100">
                    <div className={"label"}><label htmlFor="description">Description</label></div>
                    <textarea id="description" name="description" className="form-control" placeholder="Description"
                              onChange={this.changeDescription}/>
                </div>
                <div className="w-100">
                    <div className={"label"}><label htmlFor="type">Type</label></div>
                    <input type="text" id="type" name="type" className="form-control" placeholder="Type"
                           onChange={this.changeType}/>
                </div>
                <div className="w-100">
                    <div className={"label"}><label htmlFor="city">City</label></div>
                    <input type="text" id="city" name="city" className="form-control" placeholder="City"
                           onChange={this.changeCity}/>
                </div>
                <div className="w-100">
                    <div className={"label"}><label htmlFor="street">Street</label></div>
                    <input type="text" id="street" name="street" className="form-control" placeholder="Street"
                           onChange={this.changeStreet}/>
                </div>
                <div className="w-100">
                    <div className={"label"}><label htmlFor="number">Number</label></div>
                    <input type="text" id="number" name="number" className="form-control" placeholder="Number"
                           onChange={this.changeNumber}/>
                </div>
                <div className="login-button-holder w-50 mt-3">
                    <button className={"login-button"} onClick={this.submitAdd}>Add</button>
                </div>
            </div>
        )
    }
}

export default withRouter(AddPlace)