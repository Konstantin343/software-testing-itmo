import React from "react";
import BaseComponent from "../Base/BaseComponent";

export default class Preview extends BaseComponent {
    componentDidMount() {
        super.reloadUser()
    }

    render() {
        const hello = this.state.user ? (', ' + this.state.user) : ''
        return (
            <center className={"pt-5"}>
                <h1>Hello{hello}! This is <span className={"app-name"}>Places App</span>.</h1>
                <div className={"app-info"}>
                    <p>
                        Create your own lists with places<br/>
                        Share them with your friends<br/>
                        Add yours friend's lists
                    </p>
                </div>
            </center>
        )
    }
}