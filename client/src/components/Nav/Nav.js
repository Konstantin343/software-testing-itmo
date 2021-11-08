import {Link} from "react-router-dom";
import React from "react";
import BaseComponent from "../Base/BaseComponent";

export default class Nav extends BaseComponent {
    constructor(props) {
        super(props);

        this.signOut = this.signOut.bind(this)
    }

    componentDidMount() {
        super.reloadUser()
    }

    async signOut() {
        await this.placesService.signOut()
        super.reloadUser()
        window.location.href = '/'
    }

    render() {
        if (this.state.user) {
            return (
                <nav>
                    <ul>
                        <li>
                            <Link to="/">Home</Link>
                        </li>
                        <li>
                            <Link to="/places-lists">All Lists</Link>
                        </li>
                        <li>
                            <div>|</div>
                        </li>
                        <li>
                            <Link to="/my-places-lists">My Lists</Link>
                        </li>
                        <li>
                            <Link to="/add-places-list">Add New List</Link>
                        </li>
                        <li>
                            <Link to="#" onClick={this.signOut}>Sign out (<i>{this.state.user}</i>)</Link>
                        </li>
                    </ul>
                </nav>
            )
        } else {
            return (
                <nav>
                    <ul>
                        <li>
                            <Link to="/">Home</Link>
                        </li>
                        <li>
                            <Link to="/places-lists">All Lists</Link>
                        </li>
                    </ul>
                </nav>
            )
        }
    }
}