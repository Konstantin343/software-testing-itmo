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
                            <a href="/">Home</a>
                        </li>
                        <li>
                            <a href="/places-lists">All Lists</a>
                        </li>
                        <li>
                            <div>|</div>
                        </li>
                        <li>
                            <a href="/my-places-lists">My Lists</a>
                        </li>
                        <li>
                            <a href="/add-places-list">Add New List</a>
                        </li>
                        <li>
                            <a href="#" onClick={this.signOut}>Sign out ({this.state.user})</a>
                        </li>
                    </ul>
                </nav>
            )
        } else {
            return (
                <nav>
                    <ul>
                        <li>
                            <a href="/">Home</a>
                        </li>
                        <li>
                            <a href="/places-lists">All Lists</a>
                        </li>
                    </ul>
                </nav>
            )
        }
    }
}