import React from "react";
import BaseComponent from "../Base/BaseComponent";
import Unauthorized from "../Unauthorized/Unauthorized";

export default class AddPlacesList extends BaseComponent {
    constructor(props) {
        super(props);

        this.state = {
            name: '',
            description: '',
            error: null
        }

        this.submitAdd = this.submitAdd.bind(this)
        this.changeName = this.changeName.bind(this)
        this.changeDescription = this.changeDescription.bind(this)
    }

    componentDidMount() {
        super.reloadUser()
    }

    async submitAdd() {
        let [_, err] = await this.placesService.addPlaceList(this.state.name, this.state.description)
        if (err) {
            this.setState({
                error: err
            })
        } else {
            window.location.href = '/my-places-lists'
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

    render() {
        if (this.state.user) {
            return (
                <center>
                    <div className="w-50 mt-5">
                        <h3>Add List</h3>
                        <div>{this.state.error}</div>
                        <div className="w-100">
                            <div className={"label"}>Name</div>
                            <input type="text" name="name"  className="form-control" placeholder="Name" onChange={this.changeName}/>
                        </div>
                        <div className="w-100">
                            <div className={"label"}>Description</div>
                            <textarea name="description" className="form-control" placeholder="Description" onChange={this.changeDescription}/>
                        </div>
                        <div className="login-button-holder w-50 mt-3">
                            <button className={"login-button"} onClick={this.submitAdd}>Add List</button>
                        </div>
                    </div>
                </center>
            )
        } else {
            return (
                <Unauthorized placesService={this.placesService}/>
            )
        }
    }
}